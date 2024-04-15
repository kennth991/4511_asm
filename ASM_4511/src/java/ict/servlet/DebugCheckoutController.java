/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author kenneth
 */


@WebServlet(name = "DebugCheckoutController", urlPatterns = {"/debugCheckoutController"})
public class DebugCheckoutController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        Map<String, String[]> params = request.getParameterMap();
        request.setAttribute("params", params);

        for (Entry<String, String[]> entry : params.entrySet()) {
            System.out.println(entry.getKey() + ": ");
            for (String value : entry.getValue()) {
                System.out.println("\t" + value);
            }
        }

        // Forward to JSP for display
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/debugOutput.jsp");
        dispatcher.forward(request, response);
    }
}
