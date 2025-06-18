package org.ishika.project.model;

public class Doctor {
    private Long id;
    private String specialization;

    public Doctor() {}

    public Doctor(Long id, String specialization) {
        this.id = id;
        this.specialization = specialization;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
}
