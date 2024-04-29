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

@WebServlet(name = "AdminEquipmentServlet", urlPatterns = "/AdminEquipment")
public class AdminEquipmentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        EquipmentDB equipDb = new EquipmentDB(url, username, password); // Assuming the EquipmentDB manages its own connections
        ArrayList<EquipmentBean> allEquipment = equipDb.queryEquip();
        // Set the equipments attribute in the request
        request.setAttribute("equipments", allEquipment);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/admin/equipment.jsp");
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
        String isStaff = request.getParameter("editIsStaffDropdown");

        EquipmentBean editbean = new EquipmentBean(equipmentID, name, location, description, status, category, imgSrc, isStaff);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/AdminEquipment");
    }

    protected void createEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        int equipmentID = 999;
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String category = request.getParameter("category");
        String imgSrc = "42"; // Set the image source accordingly

        String isStaff = request.getParameter("createIsStaffDropdown");

        EquipmentBean newEquipment = new EquipmentBean(equipmentID, name, location, description, status, category, imgSrc, isStaff);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.createRecord(newEquipment);
        response.sendRedirect(request.getContextPath() + "/AdminEquipment");
    }

    protected void deleteEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int equipmentId = Integer.parseInt(request.getParameter("id"));
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.delRecord(equipmentId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/AdminEquipment");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("editEquipment".equals(action)) {
            editEquipment(request, response);
        } else if ("deleteEquipment".equals(action)) {
            deleteEquipment(request, response);
        } else if ("createEquipment".equals(action)) {
            createEquipment(request, response);
        } else {
            processRequest(request, response);
        }
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
