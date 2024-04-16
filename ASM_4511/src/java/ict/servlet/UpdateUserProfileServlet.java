/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kenneth
 */
@WebServlet("/UpdateUserProfileServlet")
public class UpdateUserProfileServlet extends HttpServlet {

    private String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in");
            return;
        }

        String username = request.getParameter("userName");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        if (!newPassword.isEmpty() && !newPassword.equals(confirmNewPassword)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("New passwords do not match");
            return;
        }

        if (!updateUserProfile(user.getUserID(), username, oldPassword, newPassword)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Update failed or incorrect old password");
        } else {
            response.getWriter().write("Update successful");
        }
    }

    private boolean updateUserProfile(int userID, String username, String oldPassword, String newPassword) {
        Connection connection = null;
        PreparedStatement checkPasswordStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet resultSet = null;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            //Verify the old password
            String checkPasswordSQL = "SELECT password FROM user WHERE userID = ?";
            checkPasswordStmt = connection.prepareStatement(checkPasswordSQL);
            checkPasswordStmt.setInt(1, userID);
            resultSet = checkPasswordStmt.executeQuery();

            if (resultSet.next() && resultSet.getString("password").equals(oldPassword)) {
                //Update the user information
                String updateSQL = "UPDATE user SET userName = ?, password = ? WHERE userID = ?";
                updateStmt = connection.prepareStatement(updateSQL);
                updateStmt.setString(1, username);
                // Use the new password if provided, otherwise use the old password to update (effectively making no change to it)
                updateStmt.setString(2, !newPassword.isEmpty() ? newPassword : oldPassword);
                updateStmt.setInt(3, userID);

                int rowsAffected = updateStmt.executeUpdate();
                updateSuccess = rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up JDBC objects
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (checkPasswordStmt != null) {
                    checkPasswordStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return updateSuccess;
    }
}
