package ict.servlet;

import ict.bean.Venue;
import ict.db.VenueDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "VenueServlet", urlPatterns = "/viewVenue")
public class VenueServlet extends HttpServlet {

    private VenueDAO venueDAO;

    public void init() {
        venueDAO = new VenueDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        listVenues(request, response);
    }

    private void listVenues(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Venue> listVenue = venueDAO.listAllVenues();
        request.setAttribute("listVenue", listVenue);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/view_venue.jsp");
        dispatcher.forward(request, response);
    }
}
