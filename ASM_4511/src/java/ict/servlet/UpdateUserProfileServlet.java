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
@WebServlet(name = "updateUserProfileServlet", urlPatterns = {"/UserProfile"})
public class UpdateUserProfileServlet extends HttpServlet {

    private String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login.jsp");
            return;
        }

        String username = request.getParameter("userName");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        if (!newPassword.equals(confirmNewPassword)) {
            session.setAttribute("message", "Passwords do not match");
            response.sendRedirect("/ASM_4511/user/UserProfile.jsp");
            return;
        }

        if (validateOldPassword(user.getUserID(), oldPassword)) {
            if (updateUserProfile(user.getUserID(), username, newPassword)) {
                session.setAttribute("message", "Update successful");
            } else {
                session.setAttribute("message", "Update failed");
            }
        } else {
            session.setAttribute("message", "Incorrect old password");
        }

        response.sendRedirect("/ASM_4511/user/UserProfile.jsp");
    }

    private boolean validateOldPassword(int userID, String oldPassword) {
        String query = "SELECT password FROM user WHERE userID = ?";
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(oldPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateUserProfile(int userID, String username, String newPassword) {
        String updateSQL = "UPDATE user SET userName = ?, password = ? WHERE userID = ?";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, newPassword);
            preparedStatement.setInt(3, userID);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
