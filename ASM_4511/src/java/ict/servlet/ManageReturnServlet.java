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
import java.io.StringReader; // 确保导入此类
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "ManageReturnServlet", urlPatterns = {"/manageReturn"})
public class ManageReturnServlet extends HttpServlet {

    private String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder jb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jb.append(line);
        }
        try (JsonReader reader = Json.createReader(new StringReader(jb.toString()))) {
            JsonObject jsonObject = reader.readObject();

            if (!jsonObject.containsKey("requestID") || !jsonObject.containsKey("damages")) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Missing 'requestID' or 'damages' in request.\"}");
                return;
            }

            int requestID = jsonObject.getInt("requestID");
            JsonArray damages = jsonObject.getJsonArray("damages");

            try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
                conn.setAutoCommit(false);
                boolean success = true;

                for (JsonObject damage : damages.getValuesAs(JsonObject.class)) {
                    int equipmentID = damage.getInt("equipmentID");
                    String status = damage.getString("status", "available");
                    String description = damage.getString("description", "");

                    success &= updateEquipmentStatus(conn, equipmentID, status, request.getSession().getAttribute("userID"), description);
                }

                if (success) {
                    updateRequestStatus(conn, requestID, "completed");
                    conn.commit();
                    response.getWriter().write("{\"status\":\"success\", \"message\":\"Return managed successfully.\"}");
                } else {
                    conn.rollback();
                    response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to manage return.\"}");
                }
            } catch (SQLException e) {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Database error: " + e.getMessage() + "\"}");
                e.printStackTrace();
            }
        } catch (Exception e) {
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Error processing request: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

    private boolean updateEquipmentStatus(Connection conn, int equipmentID, String status, Object userID, String description) throws SQLException {
        String updateSql = "UPDATE equipmentrequest_equipment SET status = ? WHERE equipmentID = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            updateStmt.setString(1, status.equals("damaged") ? "damaged" : "completed");
            updateStmt.setInt(2, equipmentID);

            if (updateStmt.executeUpdate() > 0) {
                if ("damaged".equals(status)) {
                    return createDamageReport(conn, equipmentID, (Integer) userID, description);
                }
                return true;
            }
            return false;
        }
    }

    private boolean createDamageReport(Connection conn, int equipmentID, int proposerID, String description) throws SQLException {
        String insertSql = "INSERT INTO damagereport (EquipmentequipmentID, proposerID, description, status, reportDateTime) VALUES (?, ?, ?, 'damaged', NOW())";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            insertStmt.setInt(1, equipmentID);
            insertStmt.setInt(2, proposerID);
            insertStmt.setString(3, description);
            return insertStmt.executeUpdate() > 0;
        }
    }

    private void updateRequestStatus(Connection conn, int requestID, String newStatus) throws SQLException {
        String query = "UPDATE equipmentrequest SET status = ? WHERE requestID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, requestID);
            stmt.executeUpdate();
        }
    }
}
