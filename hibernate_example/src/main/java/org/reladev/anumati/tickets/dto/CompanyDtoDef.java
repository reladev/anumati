package org.reladev.anumati.tickets.dto;

import org.reladev.anumati.tickets.entity.Company;
import org.reladev.quickdto.shared.CopyFromOnly;
import org.reladev.quickdto.shared.QuickDto;

@QuickDto(source = Company.class)
public class CompanyDtoDef {
    @CopyFromOnly
    Long id;

    String name;
}
