package org.acme.service;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.exception.BusinessException;
import org.acme.mapper.UserMapper;
import org.acme.model.User;
import org.acme.pojo.UserCreatedEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {
    @Inject
    UserMapper userMapper;
    @Inject
    Event<UserCreatedEvent> userCreatedEvent;

    public List<UserDTO> listAll() {
        return userMapper.toUserDTOList(User.listAll());
    }

    public UserDTO findById(Long id) {
        User user = (User) User.findByIdOptional(id).orElseThrow(NotFoundException::new);
        return userMapper.toUserDTO(user);
    }

    @Transactional
    public void update(Long id, UserDTO userDTO) {
        User user = (User) User.findByIdOptional(id).orElseThrow(NotFoundException::new);
        boolean authenticateUser = authenticateUser(userDTO, user);
        if (!authenticateUser) {
            throw new BusinessException("User authentication failed", 401);
        }
        boolean isEmailAlreadyUsed = isEmailAlreadyUsed(userDTO.email());
        if (isEmailAlreadyUsed) {
            throw new BusinessException("Email already used from the same or another user", 409);
        }
        userMapper.updateUserFromDTO(userDTO, user);
    }

    @Transactional
    public void create(UserDTO userDTO) {
        boolean isUserAlreadyPresent = isUserAlreadyPresent(userDTO);
        if (isUserAlreadyPresent) {
            throw new BusinessException("User already exists", 409);
        }
        boolean isEmailAlreadyUsed = isEmailAlreadyUsed(userDTO.email());
        if (isEmailAlreadyUsed) {
            throw new BusinessException("Email already used from another user", 409);
        }
        User user = userMapper.toUser(userDTO);
        user.persist();
        userCreatedEvent.fire(new UserCreatedEvent(userDTO, "User created successfully!"));
    }

    @Transactional
    public void delete(Long id) {
        User user = (User) User.findByIdOptional(id).orElseThrow(NotFoundException::new);
        user.delete();
    }

    private boolean isUserAlreadyPresent(UserDTO userDTO) {
        return User.find("firstName = ?1 AND lastName = ?2", userDTO.firstName(), userDTO.lastName()).firstResultOptional().isPresent();
    }

    private boolean authenticateUser(UserDTO userDTO, User user) {
        boolean hasSameFistName = userDTO.firstName().equalsIgnoreCase(user.firstName) ? true : false;
        boolean hasSameLastName = userDTO.lastName().equalsIgnoreCase(user.lastName) ? true : false;
        return hasSameFistName && hasSameLastName;
    }

    private boolean isEmailAlreadyUsed(String email) {
        return User.find("email = ?1", email).firstResultOptional().isPresent();
    }
}