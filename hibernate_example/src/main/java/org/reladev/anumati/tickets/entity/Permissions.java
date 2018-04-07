package org.reladev.anumati.tickets.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.reladev.anumati.AuthPermissions;
import org.reladev.anumati.AuthReferenceType;
import org.reladev.anumati.ReferenceKey;
import org.reladev.anumati.tickets.auth.ReferenceType;


@Entity
public class Permissions implements AuthPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long refId;
    private ReferenceType refType;
    private transient ReferenceKey referenceKey;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "Permission_Role", joinColumns = {@JoinColumn(name = "permission_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @Column(columnDefinition = "TEXT")
    private Set<String> privileges;

    protected Permissions() {
    }

    public Permissions(User user, Long refId, AuthReferenceType refType) {
        this.user = user;
        this.refId = refId;
        this.refType = (ReferenceType) refType;
    }

    public Permissions(User user, ReferenceKey refKey) {
        this.user = user;
        this.refId = (Long) refKey.getId();
        this.refType = (ReferenceType) refKey.getType();
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getReferenceId() {
        return refId;
    }

    public ReferenceType getReferenceType() {
        return refType;
    }

    public ReferenceKey getReferenceKey() {
        if (referenceKey == null) {
            referenceKey = new ReferenceKey(refId, refType);
        }

        return referenceKey;
    }

    public Set<String> getPrivileges() {
        return privileges;
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
