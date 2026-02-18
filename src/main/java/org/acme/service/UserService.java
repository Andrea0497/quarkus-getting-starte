package org.acme.service;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.dto.UserWRDTO;
import org.acme.exception.BusinessException;
import org.acme.mapper.UserMapper;
import org.acme.model.User;
import org.acme.pojo.UserCreatedEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {
    @Inject
    UserMapper userMapper;
    @Inject
    RoleService roleService;
    @Inject
    Event<UserCreatedEvent> userCreatedEvent;

    public List<UserWRDTO> listAll() {
        return userMapper.toUserWRDTOList(User.listAll());
    }

    public UserDTO findById(Long id) {
        return userMapper.toUserDTO(
                (User) User.findByIdOptional(id).orElseThrow(() -> new BusinessException("User not found", 404)));
    }

    @Transactional
    public void update(Long id, UserWRDTO userWRDTO) {
        User user = (User) User.findByIdOptional(id).orElseThrow(() -> new BusinessException("User not found", 404));
        if (!authenticateUser(userWRDTO, user))
            throw new BusinessException("User authentication failed", 401);
        if (isEmailAlreadyUsed(userWRDTO.email()))
            throw new BusinessException("Email already used from the same or another user", 409);
        userMapper.updateUserFromUserWRDTO(userWRDTO, user);
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
    public void create(UserWRDTO userWRDTO) {
        if (isUserAlreadyPresent(userWRDTO))
            throw new BusinessException("User already exists", 409);
        if (isEmailAlreadyUsed(userWRDTO.email()))
            throw new BusinessException("Email already used from another user", 409);
        userMapper.toUser(userWRDTO).persist();
        userCreatedEvent.fire(new UserCreatedEvent(userWRDTO, "User created successfully!"));
    }

    @Transactional
    public void delete(Long id) {
        User.findByIdOptional(id).orElseThrow(() -> new BusinessException("User not found", 404)).delete();
    }

    private boolean authenticateUser(UserWRDTO userWRDTO, User user) {
        boolean hasSameFistName = userWRDTO.firstName().equalsIgnoreCase(user.firstName) ? true : false;
        boolean hasSameLastName = userWRDTO.lastName().equalsIgnoreCase(user.lastName) ? true : false;
        return hasSameFistName && hasSameLastName;
    }

    private boolean isEmailAlreadyUsed(String email) {
        return User.find("email = ?1", email).firstResultOptional().isPresent();
    }

    private boolean isRoleAlreadyLinked(User user, Long roleId) {
        return user.roles.stream().anyMatch(role -> role.id.equals(roleId));
    }

    private boolean isUserAlreadyPresent(UserWRDTO userWRDTO) {
        return User.find("firstName = ?1 AND lastName = ?2", userWRDTO.firstName(), userWRDTO.lastName())
                .firstResultOptional().isPresent();
    }
}