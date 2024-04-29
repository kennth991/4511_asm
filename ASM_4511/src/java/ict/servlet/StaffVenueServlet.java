package ict.servlet;

import ict.bean.User;
import ict.bean.Venue;
import ict.db.VenueDB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StaffVenueServlet", urlPatterns = "/s_view_venue")
public class StaffVenueServlet extends HttpServlet {

    private VenueDB venueDAO;

    public void init() {
        venueDAO = new VenueDB();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        listVenues(request, response);
    }

    private void listVenues(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("staff");
        List<Venue> listVenue = venueDAO.listAllVenues();
        request.setAttribute("listVenue", listVenue);
        String test = "/staff/view_venue.jsp";
        System.out.println(test);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/" + user.getRole() + "/view_venue.jsp");
        dispatcher.forward(request, response);
    }
}
