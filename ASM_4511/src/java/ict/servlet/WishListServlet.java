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
import ict.bean.User;
@WebServlet(name = "WishListServlet", urlPatterns = "/WishListServlet")
public class WishListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        WishListEquipmentDB wEquipDb = new WishListEquipmentDB(url, username, password); // Assuming the EquipmentDB manages its own connections
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<WishListEquipmentBean> allWishListEquipmentApproved = wEquipDb.queryWishListApproved(session);
        ArrayList<WishListEquipmentBean> allWishListEquipment = wEquipDb.queryWishList();

        // Set the equipments attribute in the request
        request.setAttribute("wishList", allWishListEquipment);
        request.setAttribute("wishListApproved", allWishListEquipmentApproved);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + user.getRole() + "/wishList.jsp");
        rd.forward(request, response);
    }

    protected void confirmWishEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int wishListId = Integer.parseInt(request.getParameter("wishListwishID"));
        int equipmentId = Integer.parseInt(request.getParameter("equipmentID"));
        WishListEquipmentDB wEquipDb = new WishListEquipmentDB(url, username, password);
        wEquipDb.deleteWishListEquipment(wishListId,equipmentId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/WishListServlet");
    }
     protected void removeWishEquipment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int wishListId = Integer.parseInt(request.getParameter("removeWishListwishID"));
        int equipmentId = Integer.parseInt(request.getParameter("removeEquipmentID"));
        WishListEquipmentDB wEquipDb = new WishListEquipmentDB(url, username, password);
        wEquipDb.deleteWishListEquipment(wishListId,equipmentId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/WishListServlet");
    }

    @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    if ("confirm".equals(action)) {
        System.out.println("confirmWishEquipment method is running.");
        confirmWishEquipment(request, response);
    } else if ("remove".equals(action)) {
       removeWishEquipment(request, response);
        System.out.println("confirmWishEquipment method is running.");
    } else {
        processRequest(request, response);
    }
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
