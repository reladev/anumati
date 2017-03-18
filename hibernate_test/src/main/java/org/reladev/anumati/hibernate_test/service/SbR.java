//package org.reladev.anumati.hibernate_test.service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.ConstraintMode;
//import javax.persistence.Entity;
//import javax.persistence.ForeignKey;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//
//import org.hibernate.annotations.Where;
//import org.reladev.anumati.SecuredByRef;
//import org.reladev.anumati.SecurityContext;
//import org.reladev.anumati.hibernate_test.dto.ProjectDto;
//import org.reladev.anumati.hibernate_test.entity.Department;
//import org.reladev.anumati.hibernate_test.entity.Project;
//import org.reladev.anumati.hibernate_test.entity.SecurityReference;
//import org.reladev.anumati.hibernate_test.repository.ProjectRepository;
//import org.reladev.anumati.hibernate_test.security.SecurityAction;
//import org.reladev.anumati.hibernate_test.security.SecurityObjectType;
//import org.reladev.anumati.hibernate_test.security.UserContext;
//
//public class ProjectService {
//	protected Project save(ProjectDto dto) {
//		Long id = dto.getId();
//
//		Project project;
//		if (id != null) {
//			project = projectRepository.find(id);
//			SecurityContext.assertPermissions(project, SecurityAction.EDIT);
//
//		} else {
//			project = new Project();
//			Long departmentId = dto.getOwnerId();
//			Department department;
//			if (departmentId != null) {
//				department = departmentRepository.find(departmentId);
//				SecurityContext.assertPermissions(department, SecurityAction.VIEW);
//
//			} else {
//				department = UserContext.getUser().getDefaultDepartment();
//			}
//			project.setOwner(department);
//			SecurityContext.assertPermissions(project, SecurityAction.CREATE);
//		}
//
//		project.setName(dto.getName());
//
//		return project;
//	}
//}
//
//@Entity
//public class Project extends SecuredByRef {
//	@Id
//	private Long id;
//
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name = "object_id",
//		  referencedColumnName = "id",
//		  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//	@Where(clause = "object_type=" + SecurityObjectType.PROJECT_ORDINAL)
//	private Set<SecurityReference> securityReferences = new HashSet<>();
//
//	private String name;
//}
//
//public class SecurityContext {
//	public static void assertPermissions(SecuredByRef entity, SecuredAction action) {...}
//
//	public static boolean checkPermissions(SecuredByRef entity, SecuredAction action) {...}
//}
//
