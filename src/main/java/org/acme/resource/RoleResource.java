package org.acme.resource;

import org.acme.dto.RoleDTO;
import org.acme.service.RoleService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/roles")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {
    @Inject
    RoleService roleService;

    @POST
    public void create(@Valid RoleDTO roleDTO) {
        roleService.create(roleDTO);
    }
}