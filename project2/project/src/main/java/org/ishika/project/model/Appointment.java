package org.ishika.project.model;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private Long patientId;
    private LocalDateTime dateTime;
    private String status;

    public Appointment() {}

    public Appointment(Long id, Long patientId, LocalDateTime dateTime, String status) {
        this.id = id;
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
