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

@WebServlet(name = "EquipmentServlet", urlPatterns = "/Equipment")
public class EquipmentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EquipmentDB equipDb = new EquipmentDB(); // Assuming the EquipmentDB manages its own connections
        ArrayList<EquipmentBean> allEquipment = equipDb.queryEquip();

        // Set the equipments attribute in the request
        request.setAttribute("equipments", allEquipment);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/technician/equipment.jsp");
        rd.forward(request, response);
    }

    protected void editEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int equipmentId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String status = request.getParameter("status");

        EquipmentBean editbean = new EquipmentBean();
        EquipmentDB equipDb = new EquipmentDB();
        equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/Equipment");
    }

    protected void deleteEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int equipmentId = Integer.parseInt(request.getParameter("id"));
        EquipmentDB equipDb = new EquipmentDB();
        equipDb.deleteRecord(equipmentId); // Assuming you have a method to delete records
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
