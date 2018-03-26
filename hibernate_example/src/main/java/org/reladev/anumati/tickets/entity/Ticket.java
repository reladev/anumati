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
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.tickets.auth.SecurityObjectType;


@Entity
public class Ticket extends SecuredEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "object_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Where(clause = "object_type=" + SecurityObjectType.TICKET_ORDINAL)
    private Set<SecurityReference> securityReferences = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @Where(clause = "parent_type=" + SecurityObjectType.TICKET_ORDINAL)
    private Set<ParentChildReference> childReferences = new HashSet<>();

    private Long companyId;

    private Long projectId;

    private String title;

    private String description;

    public Ticket() {
        super(SecurityObjectType.TICKET);
    }

    @Override
    protected Set<? extends SecuredReference> getSecuredReferencesForEdit() {
        return securityReferences;
    }

    @Override
    protected Set<? extends SecuredParentChild> getChildReferencesForEdit() {
        return childReferences;
    }

    @Override
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
