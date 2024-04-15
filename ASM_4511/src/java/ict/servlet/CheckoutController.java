/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author kenneth
 */
@WebServlet(name = "CheckoutController", urlPatterns = {"/checkoutController"})
public class CheckoutController extends HttpServlet {

    private String dbURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String dbUsername = "root";
    private String dbPassword = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] equipmentIds = request.getParameterValues("equipmentId");
        String location = request.getParameter("location");
        String startDate = request.getParameter("startDate");
        String returnDate = request.getParameter("returnDate");
        int userId = Integer.parseInt(request.getParameter("userId"));

        // Logging the received parameters to console
        System.out.println("Received parameters:");
        System.out.println("User ID: " + userId);
        System.out.println("Location: " + location);
        System.out.println("Start Date: " + startDate);
        System.out.println("Return Date: " + returnDate);
        if (equipmentIds != null) {
            System.out.println("Equipment IDs: " + String.join(", ", equipmentIds));
        } else {
            System.out.println("No equipment IDs received.");
        }

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            conn.setAutoCommit(false); // Start transaction

            try {
                updateEquipmentStatus(conn, equipmentIds);
                int requestId = insertEquipmentRequest(conn, userId, startDate, returnDate);
                insertEquipmentRequestDetails(conn, requestId, equipmentIds, location);

                conn.commit(); // Commit transaction
                response.sendRedirect("success.jsp"); // Redirect to a success page
            } catch (SQLException ex) {
                conn.rollback(); // Rollback on error
                throw new ServletException("Database error: " + ex.getMessage(), ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to an error page
        }
    }

    private void updateEquipmentStatus(Connection conn, String[] equipmentIds) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE equipment SET status = 'unavailable' WHERE equipmentID = ?")) {
            for (String id : equipmentIds) {
                ps.setInt(1, Integer.parseInt(id));
                ps.executeUpdate();
            }
        }
    }

    private int insertEquipmentRequest(Connection conn, int userId, String startDate, String returnDate) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO equipmentrequest (requesterID, responderID, requestDateTime, startDate, returnDate, status) VALUES (?, NULL, NOW(), ?, ?, 'requested')",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(returnDate));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    private void insertEquipmentRequestDetails(Connection conn, int requestId, String[] equipmentIds, String formLocation) throws SQLException {
        String query = "INSERT INTO equipmentrequest_equipment (EquipmentRequestrequestID, EquipmentequipmentID, DeliverydeliveryID, requestLocation, respondLocation, status) VALUES (?, ?, NULL, ?, ?, 'requested')";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (String equipmentId : equipmentIds) {
                String respondLocation = getEquipmentLocation(conn, Integer.parseInt(equipmentId));

                ps.setInt(1, requestId);
                ps.setInt(2, Integer.parseInt(equipmentId));
                ps.setString(3, formLocation);
                ps.setString(4, respondLocation);
                ps.executeUpdate();
            }
        }
    }

    private String getEquipmentLocation(Connection conn, int equipmentId) throws SQLException {
        String query = "SELECT location FROM equipment WHERE equipmentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, equipmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("location");
                }
            }
        }
        return null;
    }

}
