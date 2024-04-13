package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ict.bean.EquipmentBean;
import java.util.ArrayList;
import ict.db.EquipmentDB;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "BookingServlet", urlPatterns = "/Booking")
public class BookingServlet extends HttpServlet {

    private EquipmentDB equipDb = new EquipmentDB(); // This should use the no-arg constructor as intended.

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ArrayList<Equipment> allEquipment = equipDb.queryEquip(); // Assume this method exists and returns all equipment.
        request.setAttribute("equipments", allEquipment);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/technician/booking.jsp");
        rd.forward(request, response);
    }

    protected void editEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int equipmentId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        EquipmentBean editBean = new EquipmentBean(equipmentId, name, location, description, status);

        equipDb.editRecord(editBean); // Assumes editRecord updates the equipment in the database
        response.sendRedirect(request.getContextPath() + "/Equipment");
    }

    protected void deleteEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int equipmentId = Integer.parseInt(request.getParameter("id"));
        equipDb.deleteRecord(equipmentId); // Assumes deleteRecord removes the equipment from the database
        response.sendRedirect(request.getContextPath() + "/Equipment");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            editEquipment(request, response);
        } else if ("delete".equals(action)) {
            deleteEquipment(request, response);
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
