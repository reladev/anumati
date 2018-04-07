package org.reladev.anumati.tickets;

import org.reladev.anumati.AuthAction;

public enum TicketsAction implements AuthAction {
    CREATE(0, 'C'),
    READ(1, 'R'),
    UPDATE(2, 'U'),
    DELETE(3, 'D'),
    PERMISSION(4, 'P');

    private int flagPosition;
    private char abbreviation;

    TicketsAction(int flagPosition, char abbreviation) {
        this.flagPosition = flagPosition;
        this.abbreviation = abbreviation;
    }

    @Override
    public int getFlagPosition() {
        return flagPosition;
    }

    @Override
    public char getAbbreviation() {
        return abbreviation;
    }
}
