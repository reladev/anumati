package org.reladev.anumati.tickets.repository;

import org.reladev.anumati.AuthReferenceType;
import org.reladev.anumati.tickets.entity.Permissions;
import org.springframework.stereotype.Component;

@Component
public class PermissionsRepository extends BaseRepository<Permissions> {
    public PermissionsRepository() {
    }

    public Permissions find(Long userId, Long refId, AuthReferenceType refType) {
        return entityManager.createQuery("SELECT p " + "FROM Permissions p " + "WHERE p.user.id=:userId " + "AND p.refId=:refId " + "AND p.refType=:refType", Permissions.class).setParameter("userId", userId).setParameter("refId", refId).setParameter(
              "refType", refType).getSingleResult();
    }


}