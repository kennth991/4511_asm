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

@WebServlet(name = "WishListServlet", urlPatterns = "/WishListServlet")
public class WishListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        WishListEquipmentDB wEquipDb = new WishListEquipmentDB(url, username, password); // Assuming the EquipmentDB manages its own connections
       HttpSession session = request.getSession();
        ArrayList<WishListEquipmentBean> allWishListEquipment = wEquipDb.queryWishList(session);

        // Set the equipments attribute in the request
        request.setAttribute("wishList", allWishListEquipment);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/user/wishList.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
