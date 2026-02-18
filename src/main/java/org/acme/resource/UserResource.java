package org.acme.resource;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.dto.UserWRDTO;
import org.acme.service.UserService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;

    @GET
    public List<UserWRDTO> listAll() {
        return userService.listAll();
    }

    @GET
    @Path("{ID}")
    public UserDTO findById(@PathParam("ID") Long id) {
        return userService.findById(id);
    }

    @PUT
    @Path("{ID}")
    public void update(@PathParam("ID") Long id, @Valid UserDTO userDTO) {
        userService.update(id, userDTO);
    }

    @PUT
    @Path("{userID}/roles/{roleID}")
    public void linkRoleToUser(@PathParam("userID") Long userId, @PathParam("roleID") Long roleId) {
        userService.linkRoleToUser(userId, roleId);
    }

    @POST
    public void create(@Valid UserDTO userDTO) {
        userService.create(userDTO);
    }

    @DELETE
    @Path("{ID}")
    public void delete(@PathParam("ID") Long id) {
        userService.delete(id);
    }
}