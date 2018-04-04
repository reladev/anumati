package org.reladev.anumati.tickets;

import org.reladev.anumati.AuthAction;

public enum TicketsAction implements AuthAction {
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
