package org.acme.mapper;

import org.acme.dto.RoleDTO;
import org.acme.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role toRole(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);
}