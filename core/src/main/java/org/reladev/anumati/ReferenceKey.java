package org.reladev.anumati;

import java.util.Objects;

public class ReferenceKey {
    Object id;
    AuthReferenceType type;

    public ReferenceKey(Object id, AuthReferenceType type) {
        this.id = id;
        this.type = type;
    }

    public Object getId() {
        return id;
    }

    public AuthReferenceType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReferenceKey that = (ReferenceKey) o;
        return type == that.type && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }
}
