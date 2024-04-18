/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.db.EquipmentRequestDB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author kenneth
 */
@WebServlet(name = "ReturnEquipmentServlet", urlPatterns = {"/returnEquipment"})
public class ReturnEquipmentServlet extends HttpServlet {

    private String url = "jdbc:mysql://localhost:3306/4511_asm";
    private String username = "root";
    private String password = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        EquipmentRequestDB equipmentRequestDB = new EquipmentRequestDB(url, username, password);
        boolean updateSuccess = equipmentRequestDB.setEquipmentReturnPending(requestId);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if (updateSuccess) {
            out.println("{\"status\":\"success\", \"message\":\"Return process initiated successfully.\"}");
        } else {
            out.println("{\"status\":\"failure\", \"message\":\"Failed to initiate return process.\"}");
        }
    }

}
