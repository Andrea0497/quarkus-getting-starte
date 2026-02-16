package org.acme.mapper;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<User> users);
}