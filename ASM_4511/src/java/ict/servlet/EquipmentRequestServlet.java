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

    /*protected void editEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        int equipmentID = Integer.parseInt(request.getParameter("equipmentID"));
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String category = request.getParameter("category");
        String imgSrc = request.getParameter("imgSrc");

        EquipmentBean editbean = new EquipmentBean(equipmentID,name,location,description,status,category,imgSrc);
        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.editRecord(editbean);
        response.sendRedirect(request.getContextPath() + "/Equipment");
    }
*/
    /* protected void deleteEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int equipmentId = Integer.parseInt(request.getParameter("id"));
        EquipmentDB equipDb = new EquipmentDB();
        equipDb.deleteRecord(equipmentId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/Equipment");
    }*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            //editEquipment(request, response);
        } else if ("delete".equals(action)) {
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
