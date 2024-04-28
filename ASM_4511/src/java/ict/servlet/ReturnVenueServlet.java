/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.db.VenueBookingDB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author kenneth
 */
@WebServlet(name = "ReturnVenueServlet", urlPatterns = {"/returnVenue"})
public class ReturnVenueServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        System.out.println("Booking ID received: " + bookingId);

        VenueBookingDB bookingDAO = new VenueBookingDB();

        // Call the method to update the status of the booking
        boolean isUpdated = bookingDAO.updateBookingStatus(bookingId, "return pending", null);
        System.out.println("Update status: " + isUpdated);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (isUpdated) {
            System.out.println("Return initiated successfully.");
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Return initiated successfully.\"}");
        } else {
            System.out.println("Failed to initiate return.");
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to initiate return.\"}");
        }
    }

}
