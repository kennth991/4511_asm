package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ict.bean.ReturnEquipmentBean;
import java.util.ArrayList;
import ict.db.ReturnEquipmentDB;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ReturnEquipServlet", urlPatterns = "/ReturnEquipServlet")
public class ReturnEquipServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        ReturnEquipmentDB equipDb = new ReturnEquipmentDB(url, username, password); // Assuming the EquipmentDB manages its own connections
        ArrayList<ArrayList<ReturnEquipmentBean>> allEquipment = equipDb.queryEquip();

        // Set the equipments attribute in the request
        request.setAttribute("returnEquipments", allEquipment);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/technician/returnEquipment.jsp");
        rd.forward(request, response);
    }

protected void returnEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String url = "jdbc:mysql://localhost:3306/4511_asm";
    String username = "root";
    String password = "";

    // Retrieve the selected equipment IDs and their corresponding values
    String requestID = request.getParameter("requestID");
    String[] equipmentIds = request.getParameterValues("equipmentId");
    String[] returnStatuses = request.getParameterValues("returnStatus");
    HttpSession session = request.getSession();
    ReturnEquipmentDB equipDb = new ReturnEquipmentDB(url, username, password);

    for (int i = 0; i < equipmentIds.length; i++) {
        String equipmentId = equipmentIds[i];
        String returnStatus = returnStatuses[i];
       
        // Check if the return status is "damage" and retrieve the damage report value
        if (returnStatus.equals("damage")) {
            String damageReport = request.getParameter("damageReportText" + equipmentId);
 
          
          equipDb.damageRecord(damageReport,equipmentId,session);
        }

        // Perform other operations as necessary

        equipDb.editRecord(requestID, equipmentIds, returnStatuses, session); // Pass the equipment IDs and return statuses to the editRecord method
    }

    response.sendRedirect(request.getContextPath() + "/ReturnEquipServlet");
}

  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("returnEquipment".equals(action)) {
          
        } else if ("deleteEquipment".equals(action)) {
           
        } else {
            processRequest(request, response);
        }
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
