package org.reladev.anumati.tickets.dto;

import org.reladev.anumati.tickets.entity.Project;
import org.reladev.quickdto.shared.CopyFromOnly;
import org.reladev.quickdto.shared.QuickDto;

@QuickDto(source = Project.class)
public class ProjectDtoDef {
    @CopyFromOnly
    Long id;

    String name;
}
