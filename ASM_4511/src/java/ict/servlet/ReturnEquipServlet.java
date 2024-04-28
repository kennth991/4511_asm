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



  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("editEquipment".equals(action)) {
          
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
