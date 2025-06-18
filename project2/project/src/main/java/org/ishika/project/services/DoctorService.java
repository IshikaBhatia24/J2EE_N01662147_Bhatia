package org.ishika.project.services;

import org.ishika.project.model.Doctor;
import org.ishika.project.util.DBUtil;

import java.sql.*;
import java.util.*;

public class DoctorService {

    public Doctor createDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (id, specialization) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, doctor.getId()); // must match user.id
            stmt.setString(2, doctor.getSpecialization());

            stmt.executeUpdate();
            return doctor;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getLong("id"),
                        rs.getString("specialization")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Doctor getDoctorById(Long id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Doctor(rs.getLong("id"), rs.getString("specialization"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
