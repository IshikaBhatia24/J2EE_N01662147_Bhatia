package org.ishika.project.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ishika.project.model.Appointment;
import org.ishika.project.services.AppointmentService;

import java.time.LocalDateTime;
import java.util.List;

@Path("/appointments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    private final AppointmentService appointmentService = new AppointmentService();

    @GET
    public List<Appointment> getAll() {
        return appointmentService.getAll();
    }

    @GET
    @Path("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathParam("patientId") Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @POST
    public Response book(Appointment appointment) {
        Appointment created = appointmentService.bookAppointment(appointment);
        if (created == null) {
            return Response.status(Response.Status.CONFLICT).entity("Time slot not available").build();
        }
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/reschedule")
    public Response reschedule(@PathParam("id") Long id, @QueryParam("dateTime") String dateTimeStr) {
        LocalDateTime newTime = LocalDateTime.parse(dateTimeStr);
        Appointment updated = appointmentService.rescheduleAppointment(id, newTime);
        if (updated == null) {
            return Response.status(Response.Status.CONFLICT).entity("Time slot taken or appointment not found").build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response cancel(@PathParam("id") Long id) {
        boolean deleted = appointmentService.cancelAppointment(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Appointment not found").build();
        }
        return Response.ok("Cancelled appointment successfully").build();
    }
}
