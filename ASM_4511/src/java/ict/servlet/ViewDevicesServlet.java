package ict.servlet;

import ict.db.EquipmentDB;
import ict.bean.EquipmentBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewDevicesServlet", urlPatterns = {"/view_devices"})
public class ViewDevicesServlet extends HttpServlet {

    private EquipmentDB equipmentDB;
    String url = "jdbc:mysql://localhost:3306/4511_asm";
    String username = "root";
    String password = "";

    public void init() {
        // Assuming EquipmentDB is already configured to use a DataSource or Connection pool
        equipmentDB = new EquipmentDB(url, username, password);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<EquipmentBean> availableEquipment = equipmentDB.getAllAvailableEquipment();
            request.setAttribute("availableEquipment", availableEquipment);
            request.getRequestDispatcher("/user/view_devices.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
