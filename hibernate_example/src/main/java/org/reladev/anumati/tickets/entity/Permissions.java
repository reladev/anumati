package org.reladev.anumati.tickets.entity;

import java.util.List;
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

import org.reladev.anumati.tickets.auth.SecurityObjectType;


@Entity
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long object_id;
    private SecurityObjectType objectType;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "Permission_Role", joinColumns = {@JoinColumn(name = "permission_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @Column(columnDefinition = "TEXT")
    private List<String> privileges;

    public Permissions() {
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getObject_id() {
        return object_id;
    }

    public void setObject_id(Long object_id) {
        this.object_id = object_id;
    }

    public SecurityObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(SecurityObjectType objectType) {
        this.objectType = objectType;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}
