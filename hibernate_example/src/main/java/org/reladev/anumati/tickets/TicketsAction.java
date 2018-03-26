package org.reladev.anumati.tickets;

import org.reladev.anumati.SecuredAction;

public enum TicketsAction implements SecuredAction {
    CREATE(0),
    READ(1),
    UPDATE(2),
    DELETE(3),
    PERMISSION(4);

    private int flagPosition;

    TicketsAction(int flagPosition) {
        this.flagPosition = flagPosition;
    }

    @Override
    public int getFlagPosition() {
        return flagPosition;
    }
}
