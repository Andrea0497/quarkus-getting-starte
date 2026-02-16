package org.acme.service;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.mapper.UserMapper;
import org.acme.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {
    @Inject
    UserMapper userMapper;

    public List<UserDTO> list() {
        return userMapper.toUserDTOList(User.listAll());
    }

    @Transactional
    public void create(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.persist();
    }
}