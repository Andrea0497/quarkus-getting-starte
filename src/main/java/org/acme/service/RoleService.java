package org.acme.service;

import java.util.List;

import org.acme.dto.RoleDTO;
import org.acme.dto.WebSocketRecord;
import org.acme.exception.BusinessException;
import org.acme.mapper.RoleMapper;
import org.acme.model.Role;
import org.acme.websocket.NotificationWebSocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RoleService {
    @Inject
    RoleMapper roleMapper;
    @Inject
    NotificationWebSocket notificationWebSocket;

    public List<RoleDTO> listAll() {
        return roleMapper.toRoleDTOList(Role.listAll());
    }

    public Role findById(Long id) {
        return (Role) Role.findByIdOptional(id).orElseThrow(() -> new BusinessException("Role not found", 404));
    }

    @Transactional
    public void create(RoleDTO roleDTO) {
        if (isRoleAlreadyPresent(roleDTO))
            throw new BusinessException("Role already exists", 409);
        roleMapper.toRole(roleDTO).persist();
        notificationWebSocket.sendNotification(new WebSocketRecord<RoleDTO>(roleDTO, "Role created successfully!"));
    }

    @Transactional
    public void delete(Long id) {
        findById(id).delete();
    }

    private boolean isRoleAlreadyPresent(RoleDTO roleDTO) {
        return Role.find("description", roleDTO.description()).firstResultOptional().isPresent();
    }
}