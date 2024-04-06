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

        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        ArrayList<EquipmentBean> allEquipment = equipDb.queryEquip();

        // Set the equipments attribute in the request
        request.setAttribute("equipments", allEquipment);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/technician/index.jsp");
        rd.forward(request, response);

    }

    protected void editEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        int equipmentId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int qty = Integer.parseInt(request.getParameter("qty"));
        EquipmentBean editbean = new EquipmentBean(equipmentId, name, description, qty);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        Boolean message = equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/Equipment");

    }

    protected void deleteEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        int equipmentId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int qty = Integer.parseInt(request.getParameter("qty"));
        EquipmentBean editbean = new EquipmentBean(equipmentId, name, description, qty);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        Boolean message = equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/Equipment");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String editEquipment = request.getParameter("editEquipment");
        String deleteEquipment = request.getParameter("deleteEquipment");
        if (editEquipment != null && !editEquipment.isEmpty()) {

            editEquipment(request, response);

        }else if (){
            
        }else {

            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String editEquipment = request.getParameter("editEquipment");

        if (editEquipment != null && !editEquipment.isEmpty()) {

            editEquipment(request, response);

        } else {

            processRequest(request, response);
        }
    }

}
