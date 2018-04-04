package org.reladev.anumati.tickets.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.tickets.auth.SecurityObjectType;
import org.reladev.anumati.tickets.auth.SecurityReferenceType;

@Entity
public class Company extends SecuredEntity implements SecuredReferenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "object_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Where(clause = "object_type=" + SecurityObjectType.COMPANY_ORDINAL)
    private Set<SecurityReference> securityReferences = new HashSet<>();

    private String name;

    public Company() {
        super(SecurityObjectType.COMPANY);
    }

    public Company(String name) {
        this();
        this.name = name;
    }

    /*test*/ Company(Long id) {
        this();
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public SecuredReferenceType getSecuredReferenceType() {
        return SecurityReferenceType.COMPANY;
    }

    @Override
    protected Set<? extends SecuredReference> getSecuredReferencesForEdit() {
        return securityReferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
