package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.List;

import org.acme.dto.UserDTO;
import org.acme.service.UserService;

@Path("/users")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;

    @GET
    public List<UserDTO> listAll() {
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