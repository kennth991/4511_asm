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
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        EquipmentDB equipDb = new EquipmentDB(url, username, password); // Assuming the EquipmentDB manages its own connections
        ArrayList<EquipmentBean> allEquipment = equipDb.queryEquip();

        // Set the equipments attribute in the request
        request.setAttribute("equipments", allEquipment);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/technician/equipment.jsp");
        rd.forward(request, response);
    }

    protected void editEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        int equipmentID = Integer.parseInt(request.getParameter("id"));  // Change "editid" to "id"
        String name = request.getParameter("name");  // Change "editname" to "name"
        String location = request.getParameter("location");  // Change "editlocation" to "location"
        String description = request.getParameter("description");  // Change "editdescription" to "description"
        String status = request.getParameter("status");  // Change "editstatus" to "status"
        String category = request.getParameter("category");  // Change "editcategory" to "category"
        String imgSrc = "42";

        EquipmentBean editbean = new EquipmentBean(equipmentID, name, location, description, status, category, imgSrc);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/Equipment");
    }

    protected void deleteEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int equipmentId = Integer.parseInt(request.getParameter("id"));
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.delRecord(equipmentId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/Equipment");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("editEquipment".equals(action)) {
            editEquipment(request, response);
        } else if ("deleteEquipment".equals(action)) {
            processRequest(request, response);
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
