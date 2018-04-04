package org.reladev.anumati.tickets.entity;

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
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.tickets.auth.SecurityObjectType;
import org.reladev.anumati.tickets.auth.SecurityReferenceType;

@Entity
public class Project extends SecuredEntity implements SecuredReferenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "object_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Where(clause = "object_type=" + SecurityObjectType.PROJECT_ORDINAL)
    private Set<SecurityReference> securityReferences = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    String name;


    public Project() {
        super(SecurityObjectType.PROJECT);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public SecuredReferenceType getSecuredReferenceType() {
        return SecurityReferenceType.PROJECT;
    }

    @Override
    public Set<SecuredReferenceObject> getIncludedReferenceObjects() {
        return Collections.singleton(company);
    }

    @Override
    protected Set<? extends SecuredReference> getSecuredReferencesForEdit() {
        return securityReferences;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
