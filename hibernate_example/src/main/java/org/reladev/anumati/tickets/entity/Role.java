package org.reladev.anumati.tickets.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.reladev.anumati.tickets.TicketsRole;
import org.reladev.anumati.tickets.auth.SecurityObjectType;


@Entity
public class Role extends SecuredEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "object_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Where(clause = "object_type=" + SecurityObjectType.ROLE_ORDINAL)
    private Set<SecurityReference> securityReferences = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    private TicketsRole role;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @Column(columnDefinition = "TEXT")
    private List<String> privileges;

    public Role() {
        super(SecurityObjectType.ROLE);
    }

    public Role(Company company, TicketsRole role, List<String> privileges) {
        this();
        this.company = company;
        this.role = role;
        this.privileges = privileges;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
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

    public TicketsRole getRole() {
        return role;
    }

    public void setRole(TicketsRole role) {
        this.role = role;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}
