package org.reladev.anumati.article;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.AuthReference;
import org.reladev.anumati.AuthReferenceType;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.hibernate.SecuredByRefEntity;
import org.reladev.anumati.hibernate_test.entity.SecurityReference;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

@Entity
public class Ticket extends SecuredByRefEntity {
    @Id private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) @JoinColumn(name = "object_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) @Where(clause = "object_type=" + SecurityObjectType.TICKET_ORDINAL)
    private Set<SecurityReference> securityReferences = new HashSet<>();

    private String name;

    public Ticket() {
        super(null);
    }


    @Override
    protected AuthReference createSecuredReference(Object objectId, SecuredObjectType objectType, Object referenceId, AuthReferenceType referenceType) {
        //Todo implement
        return null;
    }

    @Override
    protected Set<? extends AuthReference> getSecuredReferencesForEdit() {
        //Todo implement
        return null;
    }

    @Override
    public Object getId() {
        //Todo implement
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



