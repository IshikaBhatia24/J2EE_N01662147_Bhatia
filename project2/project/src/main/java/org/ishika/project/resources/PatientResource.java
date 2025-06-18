package org.ishika.project.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ishika.project.model.Patient;
import org.ishika.project.services.PatientService;

import java.util.List;

@Path("/patients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatientResource {

    private final PatientService patientService = new PatientService();

    @GET
    public List<Patient> getAllPatients() {
        return patientService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Patient patient = patientService.getById(id);
        if (patient == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(patient).build();
    }

    @POST
    public Response register(Patient patient) {
        Patient created = patientService.create(patient);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/feedback")
    public Response giveFeedback(@PathParam("id") Long id, @QueryParam("message") String feedback) {
        Patient updated = patientService.updateFeedback(id, feedback);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }
}
