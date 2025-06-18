package org.ishika.project.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ishika.project.model.User;
import org.ishika.project.services.UserService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService = new UserService();

    @POST
    @Path("/register")
    public Response register(User user) {
        User created = userService.register(user);
        if (created == null) {
            return Response.status(Response.Status.CONFLICT).entity("Email already exists").build();
        }
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @POST
    @Path("/login")
    public Response login(User user) {
        User found = userService.login(user.getEmail(), user.getPassword());
        if (found != null) {
            return Response.ok(found).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid email or password").build();
    }
}
