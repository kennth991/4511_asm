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

@WebServlet(name = "StaffAddWishListServlet", urlPatterns = "/StaffAddWishList")
public class StaffAddWishListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        WishListEquipmentDB wEquipDb = new WishListEquipmentDB(url, username, password); // Assuming the EquipmentDB manages its own connections
        HttpSession session = request.getSession();
        ArrayList<WishListEquipmentBean> allWishListEquipment = wEquipDb.sQueryAddWishList(session);
        // Set the equipments attribute in the request
        request.setAttribute("wishListAdd", allWishListEquipment);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/staff/addWishlist.jsp");
        rd.forward(request, response);
    }

    protected void addWishList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        HttpSession session = request.getSession();
        int wishListId = (int) session.getAttribute("userId");
        int equipmentId = Integer.parseInt(request.getParameter("equipmentID"));

        WishListEquipmentDB wEquipDb = new WishListEquipmentDB(url, username, password);
        wEquipDb.addToWishList(wishListId, equipmentId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/StaffAddWishList");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("AddWishList".equals(action)) {
            addWishList(request, response);
        } else {
            processRequest(request, response);
        }
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
