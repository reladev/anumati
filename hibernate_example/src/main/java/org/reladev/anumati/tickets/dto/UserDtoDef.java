package org.reladev.anumati.tickets.dto;

import org.reladev.anumati.tickets.entity.User;
import org.reladev.quickdto.shared.CopyFromOnly;
import org.reladev.quickdto.shared.QuickDto;

@QuickDto(source = User.class)
public class UserDtoDef {
    @CopyFromOnly
    Long id;

    String username;

    String password;

}
