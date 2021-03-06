package org.reladev.anumati.hibernate_test.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.AuthReference;
import org.reladev.anumati.AuthReferenceObject;
import org.reladev.anumati.AuthReferenceType;
import org.reladev.anumati.hibernate_test.security.CompanyOwned;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;
import org.reladev.anumati.hibernate_test.security.SecurityReferenceType;

@Entity
public class Project extends SecuredEntity implements AuthReferenceObject, CompanyOwned {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "object_id",
		  referencedColumnName = "id",
		  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) @Where(clause = "object_type=" + SecurityObjectType.PROJECT_ORDINAL) private Set<SecurityReference> securityReferences = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;


    public Project() {
        super(SecurityObjectType.PROJECT);
    }

	@Override
	public Long getId() {
		return id;
	}

	@Override
    public AuthReferenceType getSecuredReferenceType() {
        return SecurityReferenceType.PROJECT;
    }

	@Override
    public Set<AuthReferenceObject> getIncludedReferenceObjects() {
        return Collections.singleton(company);
    }

    protected Project(Long id) {
        super(SecurityObjectType.PROJECT);

        this.id = id;
    }

	@Override
    protected Set<? extends AuthReference> getSecuredReferencesForEdit() {
        return securityReferences;
    }

	@Override
    public void setOwner(AuthReferenceObject refObj) {
        super.setOwner(refObj);

		company = (Company) refObj;
	}
}
