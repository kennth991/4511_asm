/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.db.EquipmentDB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author kenneth
 */
// File: CheckoutServlet.java
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/4511_asm";
    String username = "root";
    String password = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String[] equipmentIds = request.getParameterValues("equipmentIds");
    if (equipmentIds != null) {
        System.out.println("Received equipmentIds: " + Arrays.toString(equipmentIds));
        EquipmentDB equipmentDB = new EquipmentDB(url, username, password);
        equipmentDB.updateEquipmentStatus(Arrays.asList(equipmentIds), "waiting for approval");
    }
    response.sendRedirect("/user/view_devices.jsp"); // Redirect to a confirmation page
}

}
