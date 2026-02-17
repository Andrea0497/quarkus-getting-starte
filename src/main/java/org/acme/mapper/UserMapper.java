package org.acme.mapper;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {
    UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<User> users);
    
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    void updateUserFromDTO(UserDTO userDTO, @MappingTarget User user);
}