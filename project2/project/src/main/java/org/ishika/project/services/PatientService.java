package org.ishika.project.services;

import org.ishika.project.model.Patient;
import org.ishika.project.util.DBUtil;

import java.sql.*;
import java.util.*;

public class PatientService {

    public List<Patient> getAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Patient(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("password"),
                        rs.getString("feedback")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Patient getById(Long id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("password"),
                        rs.getString("feedback")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Patient create(Patient patient) {
        String sql = "INSERT INTO patients (name, email, age, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getEmail());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getPassword());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                patient.setId(rs.getLong(1));
            }
            return patient;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Patient updateFeedback(Long id, String feedback) {
        String sql = "UPDATE patients SET feedback = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, feedback);
            stmt.setLong(2, id);
            stmt.executeUpdate();
            return getById(id);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
