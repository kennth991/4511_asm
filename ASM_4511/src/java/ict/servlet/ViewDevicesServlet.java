package ict.servlet;

import ict.bean.EquipmentBean;
import ict.db.EquipmentDB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/view_devices")
public class ViewDevicesServlet extends HttpServlet {

    private EquipmentDB equipmentDB;

    @Override
    public void init() {
        // Initialize EquipmentDB assuming it's set up for database connections
        equipmentDB = new EquipmentDB();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Use false to not create a new session
        if (session == null || session.getAttribute("user") == null) {
            // If no session or user is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            List<EquipmentBean> availableEquipment = equipmentDB.getAllAvailableEquipment();
            request.setAttribute("availableEquipment", availableEquipment);
            request.getRequestDispatcher("/WEB-INF/user/view_devices.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving data from the database.");
            request.getRequestDispatcher("/WEB-INF/user/error.jsp").forward(request, response);
        }
    }
}
