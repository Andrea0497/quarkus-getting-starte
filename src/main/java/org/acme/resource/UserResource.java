package org.acme.resource;

import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.dto.UserWoRDTO;
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
    public List<UserWoRDTO> listAll() {
        return userService.listAll();
    }

    @GET
    @Path("{id}")
    public UserDTO findById(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @PUT
    @Path("{id}")
    public void update(@PathParam("id") Long id, @Valid UserWoRDTO userWoRDTO) {
        userService.update(id, userWoRDTO);
    }

    @PUT
    @Path("{userId}/roles/{roleId}")
    public void linkRoleToUser(@PathParam("userId") Long userId, @PathParam("roleId") Long roleId) {
        userService.linkRoleToUser(userId, roleId);
    }

    @POST
    public void create(@Valid UserWoRDTO userWoRDTO) {
        userService.create(userWoRDTO);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        userService.delete(id);
    }
}