/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author kenneth
 */

/**
 * Servlet to fetch details about equipment requests marked as "return pending"
 */
@WebServlet(name = "fetchEquipmentDetailsServlet", urlPatterns = "/fetchEquipmentDetails")
public class FetchEquipmentDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = fetchEquipmentDetails();
        response.getWriter().write(jsonResponse);
    }

    /**
     * Fetches equipment details from the database.
     */
    private String fetchEquipmentDetails() {
        String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        String sql = "SELECT er.requestID, er.requestDateTime, er.status, u.name as requesterName, " +
                     "eq.equipmentID, eq.name as equipmentName, eq.location " +
                     "FROM equipmentrequest er " +
                     "JOIN equipmentrequest_equipment ere ON er.requestID = ere.EquipmentRequestrequestID " +
                     "JOIN equipment eq ON eq.equipmentID = ere.EquipmentequipmentID " +
                     "JOIN user u ON u.userID = er.requesterID " +  // Ensuring to fetch requester's name
                     "WHERE er.status = 'return pending' " +
                     "ORDER BY er.requestID, er.requestDateTime";

        StringBuilder responseJson = new StringBuilder("{\"requests\": [");
        boolean firstRequest = true;
        int lastRequestId = -1;

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int requestId = rs.getInt("requestID");
                if (lastRequestId != requestId) {
                    if (lastRequestId != -1) {
                        responseJson.append("]}"); // Close previous equipments array and request object
                    }
                    if (!firstRequest) {
                        responseJson.append(","); // Comma before starting a new request object, except for the first
                    }
                    responseJson.append(String.format("{\"requestID\": \"%d\", \"requestDateTime\": \"%s\", \"status\": \"%s\", \"requesterName\": \"%s\", \"equipments\": [",
                            requestId, rs.getString("requestDateTime"), rs.getString("status"), rs.getString("requesterName")));
                    lastRequestId = requestId;
                    firstRequest = false;
                } else {
                    responseJson.append(","); // Comma before starting a new equipment object
                }
                responseJson.append(String.format("{\"equipmentID\": \"%d\", \"name\": \"%s\", \"location\": \"%s\"}",
                        rs.getInt("equipmentID"), rs.getString("equipmentName"), rs.getString("location")));
            }
            if (lastRequestId != -1) {
                responseJson.append("]}"); // Close the last equipments array and request object
            }
            responseJson.append("]}"); // Close the requests array and the root object

        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\":\"Failed to fetch equipment details.\"}";
        }
        System.out.println("Generated JSON: " + responseJson.toString()); // Debug output
        return responseJson.toString();
    }
}

