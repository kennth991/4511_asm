/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kenneth
 */
@WebServlet(name = "ManageReturnServlet", urlPatterns = {"/manageReturn"})
public class ManageReturnServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 解析 JSON 数据
        StringBuilder jb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jb.append(line);
        }
        JSONObject jsonObject = new JSONObject(jb.toString());
        int requestID = jsonObject.getInt("requestID");
        JSONArray damages = jsonObject.getJSONArray("damages");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            boolean updateSuccess = updateRequestStatus(conn, requestID, "completed");
            processDamages(conn, damages);

            if (updateSuccess) {
                response.getWriter().write("{\"status\":\"success\", \"message\":\"Return managed successfully.\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to manage return.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Database error occurred.\"}");
        }
    }

    private boolean updateRequestStatus(Connection conn, int requestID, String newStatus) throws SQLException {
        String query = "UPDATE equipmentrequest SET status = ? WHERE requestID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, requestID);
            return stmt.executeUpdate() > 0;
        }
    }

    private void processDamages(Connection conn, JSONArray damages) throws SQLException {
        for (int i = 0; i < damages.length(); i++) {
            JSONObject damage = damages.getJSONObject(i);
            int equipmentID = damage.getInt("equipmentID");
            String description = damage.getString("description");
            createDamageReport(conn, equipmentID, description);
        }
    }

    private void createDamageReport(Connection conn, int equipmentID, String description) throws SQLException {
        String query = "INSERT INTO damagereport (description, equipmentID, status) VALUES (?, ?, 'damaged')";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, description);
            stmt.setInt(2, equipmentID);
            stmt.executeUpdate();
        }
    }
}
