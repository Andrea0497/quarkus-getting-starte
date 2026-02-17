package org.acme.service;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.mapper.UserMapper;
import org.acme.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {
    @Inject
    UserMapper userMapper;

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
        boolean isEmailAlreadyUsed = isEmailAlreadyUsed(userDTO.email());
        if (isEmailAlreadyUsed) {
            //TODO -> CHANGE EXCEPTION
            throw new RuntimeException("Email already used from the same or another user");
        }
        userMapper.updateUserFromDTO(userDTO, user);
    }

    @Transactional
    public void create(UserDTO userDTO) {
        boolean isUserAlreadyPresent = isUserAlreadyPresent(userDTO);
        if (isUserAlreadyPresent) {
            //TODO -> CHANGE EXCEPTION
            throw new RuntimeException("User already exists");
        }
        boolean isEmailAlreadyUsed = isEmailAlreadyUsed(userDTO.email());
        if (isEmailAlreadyUsed) {
            //TODO -> CHANGE EXCEPTION
            throw new RuntimeException("Email already used from another user");
        }
        User user = userMapper.toUser(userDTO);
        user.persist();
    }

    @Transactional
    public void delete(Long id) {
        User user = (User) User.findByIdOptional(id).orElseThrow(NotFoundException::new);
        user.delete();
    }

    private boolean isUserAlreadyPresent(UserDTO userDTO) {
        return User.find("first_name = ?1 AND last_name = ?2", userDTO.firstName(), userDTO.lastName()).firstResultOptional().isPresent();
    }

    private boolean isEmailAlreadyUsed(String email) {
        return User.find("email = ?1", email).firstResultOptional().isPresent();
    }
}