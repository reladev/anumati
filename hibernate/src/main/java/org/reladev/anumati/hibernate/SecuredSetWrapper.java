//package org.reladev.anumati.hibernate;
//
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.Spliterator;
//import java.util.function.Consumer;
//import java.util.function.Predicate;
//import java.util.stream.Stream;
//
//import org.reladev.anumati.SecuredReference;
//
//public class SecuredSetWrapper<S, C extends S> implements Set<S> {
//	Set<C> set;
//
//	public SecuredSetWrapper(Set<C> securedSet) {
//		this.securedSet = securedSet;
//	}
//
//	@Override
//	public int size() {
//		//Todo implement
//		return 0;
//	}
//
//	@Override
//	public boolean isEmpty() {
//		//Todo implement
//		return false;
//	}
//
//	@Override
//	public boolean contains(Object o) {
//		//Todo implement
//		return false;
//	}
//
//	@Override
//	public Iterator<S> iterator() {
//		//Todo implement
//		return set.iterator();
//	}
//
//	@Override
//	public Object[] toArray() {
//		return set.toArray();
//	}
//
//	@Override
//	public <T> T[] toArray(T[] a) {
//		return set.toArray(a);
//	}
//
//	@Override
//	public boolean add(S s) {
//		return set.add((C) s);
//	}
//
//	@Override
//	public boolean remove(Object o) {
//		return set.remove(o);
//	}
//
//	@Override
//	public boolean containsAll(Collection<?> c) {
//		//Todo implement
//		return set.containsAll(c);
//	}
//
//	@Override
//	public boolean addAll(Collection<? extends S> c) {
//		return set.addAll(c);
//	}
//
//	@Override
//	public boolean retainAll(Collection<?> c) {
//		return set.removeAll(c);
//	}
//
//	@Override
//	public boolean removeAll(Collection<?> c) {
//		return set.removeAll(c);
//	}
//
//	@Override
//	public void clear() {
//		set.clear();
//	}
//
//	@Override
//	public Spliterator<S> spliterator() {
//		return (Spliterator<S>) set.spliterator();
//	}
//
//	@Override
//	public boolean removeIf(Predicate<? super S> filter) {
//		//Todo implement
//		return set.removeIf(filter);
//	}
//
//	@Override
//	public Stream<S> stream() {
//		//Todo implement
//		return (Stream<S>) set.stream();
//	}
//
//	@Override
//	public Stream<S> parallelStream() {
//		//Todo implement
//		return (Stream<S>) set.parallelStream();
//	}
//
//	@Override
//	public void forEach(Consumer<? super S> action) {
//		set.forEach(action);
//	}
//}
