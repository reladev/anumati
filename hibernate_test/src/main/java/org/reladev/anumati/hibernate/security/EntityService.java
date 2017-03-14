package org.reladev.anumati.hibernate.security;

import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.hibernate.BaseService;
import org.reladev.anumati.hibernate.IdDto;
import org.reladev.anumati.hibernate.entity.Company;
import org.reladev.anumati.hibernate.entity.Department;
import org.reladev.anumati.hibernate.repository.EntityRepository;

abstract public class EntityService<T extends SecuredByRef<Long>, D extends IdDto<Long>> extends BaseService<Long, T, D> {

	private EntityRepository<T> repository;

	public EntityService(EntityRepository<T> repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public T getInstance(D dto) {
		//Todo implement
		return null;
	}

	@Override
	public T createNewInstance(D dto, Class<T> entityClass) {
		try {
			T entity = entityClass.newInstance();
			if (entity instanceof DepartmentOwned) {
				Long departmentId = ((DepartmentOwnedDto) dto).getOwnerId();
				Department department;
				if (departmentId != null) {
					department = repository.find(Department.class, departmentId);
				} else {
					department = UserContext.getUser().getDepartment();
				}
				//todo security
				//UserContext.assertPermissions(department, SecurityAction.VIEW);
				((DepartmentOwned) entity).setOwner(department);

			} else if (entity instanceof CompanyOwned) {
				Long companyId = ((CompanyOwnedDto) dto).getOwnerId();
				Company company;
				if (companyId != null) {
					company = repository.find(Company.class, companyId);
				} else {
					company = UserContext.getUser().getCompany();
				}
				//todo security
				//UserContext.assertPermissions(department, SecurityAction.VIEW);
				((CompanyOwned) entity).setOwner(company);
			}
			return entity;
		} catch (IllegalAccessException | InstantiationException e) {
			throw new IllegalStateException(entityClass + " must have default constructor");
		}
	}

	@Override
	protected T _save(D dto, T entity) {
		//Todo implement
		return null;
	}
}
