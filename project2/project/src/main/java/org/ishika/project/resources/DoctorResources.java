package org.ishika.project.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ishika.project.model.Patient;
import org.ishika.project.services.DoctorService;
import org.ishika.project.services.PatientService;

import java.util.List;

@Path("/doctor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DoctorResources {

    private final PatientService patientService = new PatientService();
    private final DoctorService doctorService = new DoctorService();

    @GET
    @Path("/patients")
    public Response getAllPatients() {
        List<Patient> patients = patientService.getAll();
        return Response.ok(patients).build();
    }

    @PUT
    @Path("/patients/{id}/feedback")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateFeedback(@PathParam("id") Long id, String feedback) {
        Patient updated = patientService.updateFeedback(id, feedback);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Patient not found").build();
        }
        return Response.ok(updated).build();
    }
}
