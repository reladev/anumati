package org.reladev.anumati.tickets.entity;

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
import org.reladev.anumati.AuthUser;
import org.reladev.anumati.UserPermissions;
import org.reladev.anumati.tickets.TicketsRole;
import org.reladev.anumati.tickets.auth.ReferenceType;
import org.reladev.anumati.tickets.auth.SecuredType;

@Entity
public class User extends SecuredEntity implements AuthUser, AuthReferenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "object_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Where(clause = "object_type=" + SecuredType.USER_ORDINAL)
    private Set<SecurityReference> securityReferences = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Permissions> allPermissions;

    private transient UserPermissions userPermissions;


    public User() {
        super(SecuredType.USER);
    }

    public User(Company company, String username, String password, TicketsRole role) {
        this();
        this.company = company;
        this.username = username;
        this.password = password;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public UserPermissions getUserPermissions() {
        if (userPermissions == null) {
            userPermissions = new UserPermissions(allPermissions);
        }
        return userPermissions;
    }

    @Override
    public AuthReferenceType getSecuredReferenceType() {
        return ReferenceType.USER;
    }

    @Override
    protected Set<? extends AuthReference> getSecuredReferencesForEdit() {
        return securityReferences;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
