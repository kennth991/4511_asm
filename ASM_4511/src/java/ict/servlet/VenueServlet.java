/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.EquipmentBean;
import ict.db.EquipmentDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lau Ka Ming Benjmain
 */
public class VenueServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        ArrayList<EquipmentBean> allEquipment = equipDb.queryEquip();

        // Set the equipments attribute in the request
        request.setAttribute("equipments", allEquipment);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/technician/equipment.jsp");
        rd.forward(request, response);

    }
    protected void bookVenue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        int equipmentId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        EquipmentBean editbean = new EquipmentBean(equipmentId, name, location, description, status);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/Equipment");

    }
    protected void editVenue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        int equipmentId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        EquipmentBean editbean = new EquipmentBean(equipmentId, name, location, description, status);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/Equipment");

    }

    protected void deleteVenue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        int equipmentId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String status = request.getParameter("status");;
        EquipmentBean editbean = new EquipmentBean(equipmentId, name, location, description, status);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        Boolean message = equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/Equipment");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String editEquipment = request.getParameter("editEquipment");
        String deleteEquipment = request.getParameter("deleteEquipment");
        String bookVenue = request.getParameter("bookVenue");
        if (editEquipment != null && !editEquipment.isEmpty()) {

            editEquipment(request, response);

        } else if (bookVenue != null && !bookVenue.isEmpty()) {

            processRequest(request, response);
        } else {
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
