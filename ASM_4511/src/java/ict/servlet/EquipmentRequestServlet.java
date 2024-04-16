package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ict.bean.EquipmentRequestBean;

import java.util.ArrayList;
import ict.db.EquipmentRequestDB;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "EquipmentRequestServlet", urlPatterns = "/EquipmentRequestServlet")
public class EquipmentRequestServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root"; 

        String password = "";
        EquipmentRequestDB equipDb = new EquipmentRequestDB(url, username, password); // Assuming the EquipmentDB manages its own connections
        ArrayList<EquipmentRequestBean> allEquipmentRequest = equipDb.queryEquip();

        // Set the equipments attribute in the request
        request.setAttribute("equipmentsRequest", allEquipmentRequest);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/technician/approveRequest.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            //editEquipment(request, response);
        } else if ("approve".equals(action)) {
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
