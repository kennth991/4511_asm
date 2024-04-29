package ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ict.bean.DamageReportBean;
import ict.db.ApproveDamageReportDB;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ApproveDamageReport", urlPatterns = "/ApproveDamageReport")
public class ApproveDamageReportServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        ApproveDamageReportDB equipDb = new ApproveDamageReportDB(url, username, password);
        ArrayList<DamageReportBean> allEquipmentRequestEquipment = equipDb.queryDamageReportList();

        request.setAttribute("damageReport", allEquipmentRequestEquipment);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/admin/ApproveDamageReport.jsp");
        rd.forward(request, response);
    }

    protected void approveReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";
        int reportId = Integer.parseInt(request.getParameter("reportID"));

        ApproveDamageReportDB wEquipDb = new ApproveDamageReportDB(url, username, password);
        wEquipDb.approveReport(reportId); // Assuming you have a method to delete records
        response.sendRedirect(request.getContextPath() + "/ApproveDamageReport");
    }

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("approveDamageReport".equals(action)) {

            approveReport(request, response);
        } else if ("placeDelivery".equals(action)) {

        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
