package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ict.bean.DeliveryBean;
import ict.db.DeliveryDB;
import java.util.ArrayList;
import ict.db.EquipmentRequestDB;
import ict.db.WishListEquipmentDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeliveryServlet", urlPatterns = "/DeliveryServlet")
public class DeliveryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        DeliveryDB equipDb = new DeliveryDB(url, username, password);
        ArrayList<DeliveryBean> allEquipmentRequestEquipment = equipDb.queryDeliveryList(userId);

        ArrayList<DeliveryBean> allEquipmentRequestEquipmentPickuped = equipDb.queryDeliveryListPlace(userId);

        request.setAttribute("deliveryEquipment", allEquipmentRequestEquipment);

        request.setAttribute("deliveryEquipmentPlace", allEquipmentRequestEquipmentPickuped);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/courier/delivery.jsp");
        rd.forward(request, response);
    }

    protected void pickupDelivery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int deliveryId = Integer.parseInt(request.getParameter("pickupDeliveryID"));

        DeliveryDB wEquipDb = new DeliveryDB(url, username, password);
        wEquipDb.pickupDelivery(deliveryId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/DeliveryServlet");
    }

    protected void placeDelivery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int deliveryId = Integer.parseInt(request.getParameter("placeDeliveryID"));

        DeliveryDB wEquipDb = new DeliveryDB(url, username, password);
        wEquipDb.placeDelivery(deliveryId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/DeliveryServlet");
    }

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("pickupDelivery".equals(action)) {
            pickupDelivery(request, response);

        } else if ("placeDelivery".equals(action)) {
            placeDelivery(request, response);
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
