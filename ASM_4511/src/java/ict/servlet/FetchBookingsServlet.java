/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import ict.db.VenueBookingDB;
import ict.bean.VenueBooking;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author kenneth
 */
@WebServlet(name = "fetchBookingsServlet", urlPatterns = "/view_booking")
public class FetchBookingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            VenueBookingDB bookingDAO = new VenueBookingDB();
            List<VenueBooking> bookings = bookingDAO.listAllBookings();
            request.setAttribute("bookings", bookings);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/technician/view_booking.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

}
