/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

/**
 *
 * @author kenneth
 */
import ict.db.UserDB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/DeleteUserServlet"})
public class DeleteUserServlet extends HttpServlet {

    private UserDB userDB;

    public void init() {
        userDB = new UserDB("jdbc:mysql://localhost:3306/4511_asm", "root", "");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        try {
            userDB.deleteUser(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("ManageAccounts.jsp");
    }
}
