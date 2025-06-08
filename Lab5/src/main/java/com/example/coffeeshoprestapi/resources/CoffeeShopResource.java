package com.example.coffeeshoprestapi.resources;
import com.example.coffeeshoprestapi.models.Coffee;

import com.example.coffeeshoprestapi.services.CoffeeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.*;

@Path("/coffees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeeShopResource {

    @Inject
    private CoffeeService service;

    @GET
    public Response getAll() {
        return Response.ok(service.getAllCoffees()).build();
    }

    @GET
    @Path("/{id}")
    public Response getCoffeeById(@PathParam("id") int id) {
        Coffee coffee = service.getById(id);
        if (coffee == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(coffee).build();
    }

    @POST
    public Response addCoffee(@Context SecurityContext sc, Coffee coffee) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admins only").build();
        }
        return Response.status(Response.Status.CREATED).entity(service.addCoffee(coffee)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCoffee(@Context SecurityContext sc, @PathParam("id") int id, Coffee coffee) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admins only").build();
        }
        Coffee updated = service.updateCoffee(id, coffee);
        if (updated == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoffee(@Context SecurityContext sc, @PathParam("id") int id) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admins only").build();
        }
        boolean deleted = service.deleteCoffee(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}