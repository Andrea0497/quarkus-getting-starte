package org.acme.mapper;

import java.util.List;

import org.acme.dto.RoleDTO;
import org.acme.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    Role toRole(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);

    List<RoleDTO> toRoleDTOList(List<Role> roles);
}