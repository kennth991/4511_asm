/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;
        
import ict.bean.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;


/**
 *
 * @author kenneth
 */


@WebServlet(name = "CheckoutController", urlPatterns = {"/checkoutController"})

public class CheckoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = getDBConnection()) {  // Assume getDBConnection() is your method to get a DB connection
            String location = request.getParameter("location");
            java.sql.Date startDate = java.sql.Date.valueOf(request.getParameter("startDate"));
            java.sql.Date returnDate = java.sql.Date.valueOf(request.getParameter("returnDate"));
            String[] equipmentIds = request.getParameterValues("equipmentId");

            conn.setAutoCommit(false);

            PreparedStatement psEquipment = conn.prepareStatement("UPDATE equipment SET status = 'requested' WHERE equipmentID = ?");
            for (String equipmentId : equipmentIds) {
                psEquipment.setString(1, equipmentId);
                psEquipment.executeUpdate();
            }

            PreparedStatement psRequest = conn.prepareStatement("INSERT INTO equipmentrequest (requesterID, requestDateTime, startDate, returnDate, status, requestLocation) VALUES (?, NOW(), ?, ?, 'requested', ?)", Statement.RETURN_GENERATED_KEYS);
            psRequest.setInt(1, user.getUserID());
            psRequest.setDate(2, startDate);
            psRequest.setDate(3, returnDate);
            psRequest.setString(4, location);
            psRequest.executeUpdate();

            ResultSet rs = psRequest.getGeneratedKeys();
            int requestId = rs.next() ? rs.getInt(1) : 0;

            PreparedStatement psRequestEquipment = conn.prepareStatement("INSERT INTO equipmentrequest_equipment (EquipmentRequestrequestID, EquipmentequipmentID) VALUES (?, ?)");
            for (String equipmentId : equipmentIds) {
                psRequestEquipment.setInt(1, requestId);
                psRequestEquipment.setString(2, equipmentId);
                psRequestEquipment.executeUpdate();
            }

            conn.commit();

            session.removeAttribute("cart");
            request.setAttribute("message", "Checkout successful!");
            request.getRequestDispatcher("/confirmation.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error during checkout: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private Connection getDBConnection() throws SQLException {
        // This should return your database connection; replace it with your actual database connection method
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/4511_asm", "root", "");
    }
}
