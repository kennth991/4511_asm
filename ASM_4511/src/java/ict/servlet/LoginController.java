/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ict.db.UserDB;
import ict.bean.User;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kenneth
 */
@WebServlet(name = "LoginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // redirect to login.jsp
        request.getRequestDispatcher("login.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                System.out.println("Inside doPost of LoginController");
        // get the username and password from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // compare the username and password from database
        UserDB db = new UserDB();
        User user = db.authenticate(username, password);
        HttpSession session = request.getSession(true);
        
        if (user != null) {
            String role = user.getRole();
            switch (role) {
                case "user":
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/user");
                    break;
                case "staff":
                    request.getSession().setAttribute("staff", user);
                    response.sendRedirect(request.getContextPath() + "/staff");
                    break;
                case "technician":
                    session.setAttribute("technician", user);
                    response.sendRedirect(request.getContextPath() + "/EquipmentServlet");
                    break;
                case "courier":
                    request.getSession().setAttribute("courier", user);
                    response.sendRedirect(request.getContextPath() + "/courier");
                    break;
                case "administrator":
                    request.getSession().setAttribute("admin", user);
                    response.sendRedirect(request.getContextPath() + "/admin");
                    break;
                default:
                    // User is not authorized, redirect to login page and clear session
                    request.getSession().invalidate();
                    response.sendRedirect(request.getContextPath() + "/login");
                    break;
            }
        } else {
            // if the user is not found, redirect to login.jsp
            response.sendRedirect(request.getContextPath() + "/login?error=Invalid username or password");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}