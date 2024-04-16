package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ict.bean.EquipmentRequestBean;
import ict.bean.User;
import ict.bean.EquipmentRequestEquipmentBean;
import java.util.ArrayList;
import ict.db.EquipmentRequestDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EquipmentRequestEquipmentServlet", urlPatterns = "/EquipmentRequestEquipmentServlet")
public class EquipmentRequestEquipmentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        EquipmentRequestDB equipDb = new EquipmentRequestDB(url, username, password);
        ArrayList<EquipmentRequestEquipmentBean> allEquipmentRequestEquipment = equipDb.queryEquipList(requestID);
        ArrayList<User> couriers = equipDb.queryCourList();

        request.setAttribute("equipmentsRequestEquipment", allEquipmentRequestEquipment);
        request.setAttribute("availableCourier", couriers);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/technician/approveRequestEquipment.jsp");
        rd.forward(request, response);
    }

protected void assignEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String url = "jdbc:mysql://localhost:3306/4511_asm";
    String username = "root";
    String password = "";

    EquipmentRequestDB equipDb = new EquipmentRequestDB(url, username, password);
    HttpSession session = request.getSession();
    int userId = (int) session.getAttribute("userId");

    int requestCount = Integer.parseInt(request.getParameter("requestCount"));

    for (int i = 0; i < requestCount; i++) {
        int requestID = Integer.parseInt(request.getParameter("requestID_" + i));
        int equipmentID = Integer.parseInt(request.getParameter("equipmentID_" + i));
        int assignedCourier = Integer.parseInt(request.getParameter("assignedCourier_" + i));

        if (assignedCourier == -1) {
            equipDb.rejectRequest(requestID, equipmentID); // Assuming you have a method to reject the request
        } else {
            equipDb.assignRequest(assignedCourier, userId); // Assuming you have a method to assign the request
            equipDb.assignRequestEquipment(requestID, equipmentID, userId);
        }
    }

    response.sendRedirect(request.getContextPath() + "/EquipmentRequestServlet");
}

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("assignCourier".equals(action)) {
            assignEquipment(request, response);

        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
