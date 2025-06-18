package org.ishika.project.services;

import org.ishika.project.model.Appointment;
import org.ishika.project.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class AppointmentService {

    public List<Appointment> getAll() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Appointment(
                        rs.getLong("id"),
                        rs.getLong("patient_id"),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Appointment(
                        rs.getLong("id"),
                        rs.getLong("patient_id"),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Appointment bookAppointment(Appointment appointment) {
        String checkSql = "SELECT COUNT(*) FROM appointments WHERE date_time = ?";
        String insertSql = "INSERT INTO appointments (patient_id, date_time, status) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setTimestamp(1, Timestamp.valueOf(appointment.getDateTime()));
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) return null;

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setLong(1, appointment.getPatientId());
                insertStmt.setTimestamp(2, Timestamp.valueOf(appointment.getDateTime()));
                insertStmt.setString(3, appointment.getStatus());

                insertStmt.executeUpdate();
                ResultSet keys = insertStmt.getGeneratedKeys();
                if (keys.next()) appointment.setId(keys.getLong(1));
                return appointment;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Appointment rescheduleAppointment(Long id, LocalDateTime newTime) {
        String checkSql = "SELECT COUNT(*) FROM appointments WHERE date_time = ? AND id != ?";
        String updateSql = "UPDATE appointments SET date_time = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setTimestamp(1, Timestamp.valueOf(newTime));
            checkStmt.setLong(2, id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) return null;

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setTimestamp(1, Timestamp.valueOf(newTime));
                updateStmt.setLong(2, id);
                updateStmt.executeUpdate();
                return getById(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Appointment getById(Long id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Appointment(
                        rs.getLong("id"),
                        rs.getLong("patient_id"),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cancelAppointment(Long id) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
