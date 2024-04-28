/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.db.VenueBookingDB;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author kenneth
 */

@WebServlet("/ManageBookingServlet")
public class ManageBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            String action = request.getParameter("action");

            // Check if parameters are not null and action is valid
            if (action == null || action.isEmpty()) {
                response.getWriter().write("Action is required");
                return;
            }

            VenueBookingDB bookingDAO = new VenueBookingDB();
            boolean success;

            if ("approved".equals(action) || "declined".equals(action)) {
                // Assuming updateBookingStatus also sets the responderID when status changes
                success = bookingDAO.updateBookingStatus(bookingId, action, (Integer) request.getSession().getAttribute("userId"));
            } else {
                response.getWriter().write("Invalid action");
                return;
            }

            if (success) {
                response.getWriter().write("Success");
            } else {
                response.getWriter().write("Failed to update booking status");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid booking ID");
        } catch (NullPointerException e) {
            response.getWriter().write("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
