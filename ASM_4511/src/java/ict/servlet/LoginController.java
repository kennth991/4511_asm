package ict.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.db.UserDB;
import ict.bean.User;

/**
 * Servlet implementation for User Login
 */
@WebServlet(name = "LoginController", urlPatterns = {"/loginController"})
public class LoginController extends HttpServlet {

    private UserDB userDB;

    public void init() {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        userDB = new UserDB(url, username, password);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to login page on GET request
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDB.authenticate(username, password);
        HttpSession session = request.getSession();

        if (user != null) {
            session.setAttribute("userName", user.getName()); 
            session.setAttribute("userId", user.getUserID()); 
            session.setAttribute("location", user.getLocation());
            session.setAttribute(user.getRole().toLowerCase(), user);  // Use role-based session attributes
            redirectBasedOnRole(user.getRole(), request, response);
        } else {
            session.setAttribute("error", "Invalid username or password");
            response.sendRedirect(request.getContextPath() + "/login.jsp");  // Include error message on redirect
        }
    }

    private void redirectBasedOnRole(String role, HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (role) {
            case "user":
                response.sendRedirect(request.getContextPath() + "/BorrowingRecordServlet");
                break;
            case "staff":
                response.sendRedirect(request.getContextPath() + "/StaffBorrowingRecordServlet");
                break;
            case "technician":
                response.sendRedirect(request.getContextPath() + "/Equipment");
                break;
            case "courier":
                response.sendRedirect(request.getContextPath() + "/DeliveryServlet");
                break;
            case "admin":
                response.sendRedirect(request.getContextPath() + "/AdminEquipment");
                break;
            default:
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "LoginController handles user authentication";
    }
}
