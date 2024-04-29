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

@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    private UserDB userDB;

    public void init() {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        userDB = new UserDB(url, username, password);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String location = request.getParameter("location");
        String role = request.getParameter("role");

        User newUser = new User();
        newUser.setName(name);
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setLocation(location);
        newUser.setRole(role);

        boolean isCreated = userDB.createUser(newUser);
        if (isCreated) {
            response.sendRedirect(request.getContextPath() + "/admin/CreateAccount.jsp");
        } else {
            request.setAttribute("error", "Failed to create user account.");
            request.getRequestDispatcher("/admin/CreateAccount.jsp").forward(request, response);
        }
    }
}
