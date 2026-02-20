package org.acme.service;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.dto.UserWoRDTO;
import org.acme.dto.WebSocketDTO;
import org.acme.exception.BusinessException;
import org.acme.mapper.UserMapper;
import org.acme.model.User;
import org.acme.websocket.NotificationWebSocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {
    @Inject
    UserMapper userMapper;
    @Inject
    RoleService roleService;
    @Inject
    NotificationWebSocket notificationWebSocket;

    public List<UserWoRDTO> listAll() {
        return userMapper.toUserWoRDTOList(User.listAll());
    }

    public UserDTO findById(Long id) {
        return userMapper.toUserDTO(
                (User) User.findByIdOptional(id).orElseThrow(() -> new BusinessException("User not found", 404)));
    }

    @Transactional
    public void update(Long id, UserWoRDTO userWoRDTO) {
        User user = (User) User.findByIdOptional(id).orElseThrow(() -> new BusinessException("User not found", 404));
        if (!authenticateUser(userWoRDTO, user))
            throw new BusinessException("User authentication failed", 401);
        if (isEmailAlreadyUsed(userWoRDTO.email()))
            throw new BusinessException("Email already used from the same or another user", 409);
        userMapper.updateUserFromUserWoRDTO(userWoRDTO, user);
    }

    @Transactional
    public void linkRoleToUser(Long userId, Long roleId) {
        User user = (User) User.findByIdOptional(userId)
                .orElseThrow(() -> new BusinessException("User not found", 404));
        if (isRoleAlreadyLinked(user, roleId))
            throw new BusinessException("Role already linked to the user", 409);
        user.roles.add(roleService.findById(roleId));
    }

    @Transactional
    public void create(UserWoRDTO userWoRDTO) {
        if (isUserAlreadyPresent(userWoRDTO))
            throw new BusinessException("User already exists", 409);
        if (isEmailAlreadyUsed(userWoRDTO.email()))
            throw new BusinessException("Email already used from another user", 409);
        userMapper.toUser(userWoRDTO).persist();
        notificationWebSocket
                .sendNotification(new WebSocketDTO<UserWoRDTO>(userWoRDTO, "User created successfully!"));
    }

    @Transactional
    public void delete(Long id) {
        User.findByIdOptional(id).orElseThrow(() -> new BusinessException("User not found", 404)).delete();
    }

    private boolean authenticateUser(UserWoRDTO userWoRDTO, User user) {
        boolean hasSameFistName = userWoRDTO.firstName().equalsIgnoreCase(user.firstName) ? true : false;
        boolean hasSameLastName = userWoRDTO.lastName().equalsIgnoreCase(user.lastName) ? true : false;
        return hasSameFistName && hasSameLastName;
    }

    private boolean isEmailAlreadyUsed(String email) {
        return User.find("email = ?1", email).firstResultOptional().isPresent();
    }

    private boolean isRoleAlreadyLinked(User user, Long roleId) {
        return user.roles.stream().anyMatch(role -> role.id.equals(roleId));
    }

    private boolean isUserAlreadyPresent(UserWoRDTO userWoRDTO) {
        return User.find("firstName = ?1 AND lastName = ?2", userWoRDTO.firstName(), userWoRDTO.lastName())
                .firstResultOptional().isPresent();
    }
}