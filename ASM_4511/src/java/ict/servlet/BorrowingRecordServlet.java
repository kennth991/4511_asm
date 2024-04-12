 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

/**
 *
 * @author kenneth
 */
import ict.bean.BorrowingRecord;
import ict.bean.User;
import ict.db.BorrowingRecordDB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BorrowingRecordServlet", urlPatterns = "/BorrowingRecordServlet")

public class BorrowingRecordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            BorrowingRecordDB db = new BorrowingRecordDB();
            List<BorrowingRecord> records = db.getBorrowingRecordsByUserId(user.getUserID());
            for (BorrowingRecord record : records) {
                System.out.println("Record ID: " + record.getRecordId() + ", Equipment Name: " + record.getEquipmentName());
            }
            request.setAttribute("borrowingRecords", records);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user/index.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

}
