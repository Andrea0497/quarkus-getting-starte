package org.acme.mapper;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.dto.UserWoRDTO;
import org.acme.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { RoleMapper.class })
public interface UserMapper {
    UserDTO toUserDTO(User user);

    UserWoRDTO toUserWoRDTO(User user);

    @Mapping(target = "roles", ignore = true)
    User toUser(UserWoRDTO userWoRDTO);

    List<UserWoRDTO> toUserWoRDTOList(List<User> users);

    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUserFromUserWoRDTO(UserWoRDTO userWoRDTO, @MappingTarget User user);
}