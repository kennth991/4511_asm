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
import ict.bean.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EditUserServlet", urlPatterns = {"/EditUserServlet"})
public class EditUserServlet extends HttpServlet {

    private UserDB userDB;

    public void init() {
        userDB = new UserDB("jdbc:mysql://localhost:3306/4511_asm", "root", "");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        try {
            User user = userDB.getUserByID(userID);
            request.setAttribute("user", user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/edit_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the post data from edit form submission
        int userID = Integer.parseInt(request.getParameter("userID"));
        String name = request.getParameter("name");
        String userName = request.getParameter("userName");
        String location = request.getParameter("location");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        // Update the user
        User user = new User(userID, name, userName, location, role, password);

        try {
            userDB.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("ManageAccounts.jsp");
    }
}
