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
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author kenneth
 */
@WebServlet(name = "ManageReturnServlet", urlPatterns = {"/manageReturn"})
public class ManageReturnServlet extends HttpServlet {

    private String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder jb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jb.append(line);
        }
        JsonReader reader = Json.createReader(new StringReader(jb.toString()));
        JsonObject jsonObject = reader.readObject();
        reader.close();

        int requestID = jsonObject.getInt("requestID");
        JsonArray damages = jsonObject.getJsonArray("damages");

        boolean updateSuccess = updateRequestStatus(requestID, "completed");
        processDamages(damages);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (updateSuccess) {
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Return managed successfully.\"}");
        } else {
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to manage return.\"}");
        }
    }

    private boolean updateRequestStatus(int requestID, String newStatus) {
        String query = "UPDATE equipmentrequest SET status = ? WHERE requestID = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, requestID);
            int count = stmt.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void processDamages(JsonArray damages) {
        for (JsonObject damage : damages.getValuesAs(JsonObject.class)) {
            int equipmentID = damage.getInt("equipmentID");
            String description = damage.getString("description");
            createDamageReport(equipmentID, description);
        }
    }

    private void createDamageReport(int equipmentID, String description) {
        String query = "INSERT INTO damagereport (equipmentID, description) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, equipmentID);
            stmt.setString(2, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
