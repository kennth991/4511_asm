package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ict.bean.WishListEquipmentBean;
import java.util.ArrayList;
import ict.db.WishListEquipmentDB;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "WishListEquipmentServlet", urlPatterns = "/WishListEquipmentServlet")
public class WishListEquipmentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        WishListEquipmentDB wEquipDb = new WishListEquipmentDB(url, username, password); // Assuming the EquipmentDB manages its own connections
        ArrayList<WishListEquipmentBean> allWishListEquipment = wEquipDb.queryEquip();
        ArrayList<WishListEquipmentBean> allWishListEquipmentAvailable = wEquipDb.queryEquipAvailable();

        // Set the equipments attribute in the request
        request.setAttribute("wishListEquipment", allWishListEquipment);
        request.setAttribute("wishListEquipmentAvailable", allWishListEquipmentAvailable);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/technician/wishListEquipment.jsp");
        rd.forward(request, response);
    }

    protected void confirmWishList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int wishListId = Integer.parseInt(request.getParameter("wishListwishID"));
        int equipmentId = Integer.parseInt(request.getParameter("equipmentID"));
        WishListEquipmentDB wEquipDb = new WishListEquipmentDB(url, username, password);
        wEquipDb.confirmWishList(wishListId, equipmentId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "//WishListEquipmentServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("confirmWishList".equals(action)) {
            confirmWishList(request, response);
        } else {
            processRequest(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
