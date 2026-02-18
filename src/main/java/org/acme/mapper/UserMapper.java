package org.acme.mapper;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.model.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(uses = { RoleMapper.class })
public interface UserMapper {
    UserDTO toUserDTO(User user);

    @Named("toUserDTOWithoutRoles")
    @Mapping(target = "roles", ignore = true)
    UserDTO toUserDTOWithoutRoles(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserDTO userDTO);

    @IterableMapping(qualifiedByName = "toUserDTOWithoutRoles")
    List<UserDTO> toUserDTOList(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUserFromDTO(UserDTO userDTO, @MappingTarget User user);
}