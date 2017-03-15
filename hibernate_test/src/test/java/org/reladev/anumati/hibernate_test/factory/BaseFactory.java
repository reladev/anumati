package org.reladev.anumati.hibernate_test.factory;

import java.util.HashMap;
import java.util.function.Consumer;

import javax.persistence.EntityManager;

public abstract class BaseFactory<B extends BaseFactory, T> {
	static ThreadLocal<HashMap<Class, Object>> cache = new ThreadLocal<>();
	static {
		cache.set(new HashMap<>());
	}

	protected static EntityManager entityManager;

	public static void setEntityManager(EntityManager entityManager) {
		BaseFactory.entityManager = entityManager;
	}

	public static <E> E getFromCache(Class<E> type) {
		HashMap<Class, Object> map = cache.get();
		return (E) map.get(type);
	}

	public static void putInCache(Object entity) {
		HashMap<Class, Object> map = cache.get();
		map.put(entity.getClass(), entity);
	}

	public static void clearCache() {
		cache.get().clear();
	}

	protected T entity;

	public BaseFactory(T entity) {
		this.entity = entity;
	}

	abstract protected void ensureRequired();

	public B set(Consumer<T> consumer) {
		consumer.accept(entity);
		return (B) this;
	}

	public T create() {
		ensureRequired();
		T value = entity;
		entity = null;

		return value;
	}

	public T createPersist() {
		T value = create();

		entityManager.persist(value);
		entityManager.flush();
		putInCache(value);
		return value;
	}

	public T getOrCreatePersist() {
		@SuppressWarnings("unchecked")
		T entity = (T) getFromCache(this.entity.getClass());
		if (entity == null) {
			entity = createPersist();
		}
		return entity;
	}

}
