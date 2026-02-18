package org.acme.service;

import org.acme.dto.RoleDTO;
import org.acme.exception.BusinessException;
import org.acme.mapper.RoleMapper;
import org.acme.model.Role;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class RoleService {
    @Inject
    RoleMapper roleMapper;

    public Role findById(Long id) {
        return (Role) Role.findByIdOptional(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void create(RoleDTO roleDTO) {
        if (isRoleAlreadyPresent(roleDTO)) {
            throw new BusinessException("Role already exists", 409);
        }
        Role role = roleMapper.toRole(roleDTO);
        role.persist();
    }

    private boolean isRoleAlreadyPresent(RoleDTO roleDTO) {
        return Role.find("description", roleDTO.description()).firstResultOptional().isPresent();
    }
}