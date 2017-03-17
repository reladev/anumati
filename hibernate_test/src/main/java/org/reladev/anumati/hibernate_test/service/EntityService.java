package org.reladev.anumati.hibernate_test.service;

import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.hibernate.BaseService;
import org.reladev.anumati.hibernate.IdDto;
import org.reladev.anumati.hibernate_test.entity.Company;
import org.reladev.anumati.hibernate_test.entity.Department;
import org.reladev.anumati.hibernate_test.entity.ParentChildReference;
import org.reladev.anumati.hibernate_test.entity.SecurityReference;
import org.reladev.anumati.hibernate_test.repository.EntityRepository;
import org.reladev.anumati.hibernate_test.security.CompanyOwned;
import org.reladev.anumati.hibernate_test.security.CompanyOwnedDto;
import org.reladev.anumati.hibernate_test.security.DepartmentOwned;
import org.reladev.anumati.hibernate_test.security.DepartmentOwnedDto;
import org.reladev.anumati.hibernate_test.security.SecurityAction;
import org.reladev.anumati.hibernate_test.security.UserContext;

abstract public class EntityService<T extends SecuredByRef, D extends IdDto> extends BaseService<T, D> {

	private EntityRepository<T> repository;

	public EntityService(Class<T> entityClass, EntityRepository<T> repository) {
		super(entityClass, repository);
		this.repository = repository;
	}

	 @Override
	 public Class<? extends SecuredParentChild> getSecuredParentChildClass() {
		 return ParentChildReference.class;
	 }

	 @Override
	 public SecuredReference createSecuredReference(Object objectId, SecuredObjectType objectType, Object referenceId, SecuredReferenceType referenceType) {
		 return new SecurityReference(objectId, objectType, referenceId, referenceType);
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
					department = UserContext.getUser().getDefaultDepartment();
				}
				SecurityContext.assertPermissions(department, SecurityAction.VIEW);
				((DepartmentOwned) entity).setOwner(department);

			} else if (entity instanceof CompanyOwned) {
				Long companyId = ((CompanyOwnedDto) dto).getOwnerId();
				Company company;
				if (companyId != null) {
					company = repository.find(Company.class, companyId);
				} else {
					company = UserContext.getUser().getCompany();
				}
				SecurityContext.assertPermissions(company, SecurityAction.VIEW);
				((CompanyOwned) entity).setOwner(company);
			}
			return entity;
		} catch (IllegalAccessException | InstantiationException e) {
			throw new IllegalStateException(entityClass + " must have default constructor");
		}
	}
}
