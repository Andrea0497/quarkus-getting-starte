package org.acme.resource;

import java.util.List;

import org.acme.dto.RoleDTO;
import org.acme.service.RoleService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/roles")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {
    @Inject
    RoleService roleService;

    @GET
    public List<RoleDTO> listAll() {
        return roleService.listAll();
    }

    @POST
    public void create(@Valid RoleDTO roleDTO) {
        roleService.create(roleDTO);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        roleService.delete(id);
    }
}