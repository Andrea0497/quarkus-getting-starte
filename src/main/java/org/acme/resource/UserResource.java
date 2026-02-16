package org.acme.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.List;
import org.acme.model.User;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @GET
    public List<User> list() {
        return User.listAll();
    }

    @POST
    @Transactional
    public void create(User user) {
        user.persist();
    }
}