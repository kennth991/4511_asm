package ict.servlet;

import ict.bean.User;
import ict.bean.VenueBooking;
import ict.db.VenueBookingDAO;
import java.io.IOException;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BookVenueServlet")
public class BookVenueServlet extends HttpServlet {

    private VenueBookingDAO bookingDAO;

    public void init() {
        bookingDAO = new VenueBookingDAO(); // Assume VenueBookingDAO is your data access object for bookings
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Venue ID: " + request.getParameter("venueId"));
        System.out.println("Booking Date: " + request.getParameter("bookingDate"));
        System.out.println("Start Time: " + request.getParameter("startTime"));
        System.out.println("End Time: " + calculateEndTime(request.getParameter("startTime")));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int venueId = Integer.parseInt(request.getParameter("venueId"));
        String bookingDate = request.getParameter("bookingDate");
        String startTime = request.getParameter("startTime");
        String endTime = calculateEndTime(startTime);

        VenueBooking booking = new VenueBooking();
        booking.setRequesterID(user.getUserID());
        booking.setVenueVenueID(venueId);
        booking.setBookingDate(java.sql.Date.valueOf(bookingDate));
        booking.setCheckinTime(startTime);
        booking.setCheckoutTime(endTime);
        booking.setStatus("reserved");

        try {
            boolean isBooked = bookingDAO.saveBooking(booking);
            if (isBooked) {
                response.getWriter().write("Booking successful!");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to book venue due to a scheduling conflict.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error occurred.");
        }
    }

    private String calculateEndTime(String startTime) {
        LocalTime start = LocalTime.parse(startTime);
        return start.plusHours(2).toString(); // Assumes each booking lasts for 2 hours
    }
}
