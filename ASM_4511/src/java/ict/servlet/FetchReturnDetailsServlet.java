/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kenneth
 */
@WebServlet(name = "FetchReturnDetailsServlet", urlPatterns = "/fetchReturnDetails")
public class FetchReturnDetailsServlet extends HttpServlet {

    private static final String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
            return;
        }
        String returnType = request.getParameter("returnType");
        String htmlResponse = "";
        int userId = user.getUserID();
        if ("equipment".equals(returnType)) {
            htmlResponse = fetchEquipmentReturnDetails(userId); // Pass the user ID to the method
        } else if ("venue".equals(returnType)) {
            htmlResponse = fetchVenueReturnDetails(userId); // Assuming this method doesn't need the user ID
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(htmlResponse);
    }

    private String fetchEquipmentReturnDetails(int userId) {
        String sql = "SELECT er.requestID, eq.name, er.requestDateTime, er.startDate, er.returnDate, er.status "
                + "FROM equipmentrequest er "
                + "JOIN equipmentrequest_equipment ere ON er.requestID = ere.EquipmentRequestrequestID "
                + "JOIN equipment eq ON ere.EquipmentequipmentID = eq.equipmentID "
                + "WHERE er.requesterID = ? AND er.status = 'assigned' "
                + "ORDER BY er.requestID, er.requestDateTime";

        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<div class='row'>")
                .append("<div class='col-md-12 col-lg-12'>")
                .append("<div class='card'>")
                .append("<div class='card-header'>Personal Borrowing Records</div>")
                .append("<div class='card-body'>");

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            int currentRequestID = -1;
            boolean hasResults = false;

            while (rs.next()) {
                int requestId = rs.getInt("requestID");
                if (requestId != currentRequestID) {
                    if (currentRequestID != -1) {
                        htmlResponse.append("</tbody>")
                                .append("</table>")
                                .append("<button onclick='returnEquipment(").append(currentRequestID).append(")' class='btn btn-primary'>Return All</button>")
                                .append("<hr>");
                    }
                    htmlResponse.append("<table class='table table-striped'>")
                            .append("<thead>")
                            .append("<tr>")
                            .append("<th>Request ID</th>")
                            .append("<th>Equipment Name</th>")
                            .append("<th>Request DateTime</th>")
                            .append("<th>Start Date</th>")
                            .append("<th>Return Date</th>")
                            .append("<th>Status</th>")
                            .append("</tr>")
                            .append("</thead>")
                            .append("<tbody>");
                    currentRequestID = requestId;
                }
                htmlResponse.append("<tr>")
                        .append("<td>").append(requestId).append("</td>")
                        .append("<td>").append(rs.getString("name")).append("</td>")
                        .append("<td>").append(rs.getTimestamp("requestDateTime")).append("</td>")
                        .append("<td>").append(rs.getDate("startDate") != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("startDate")) : "N/A").append("</td>")
                        .append("<td>").append(rs.getDate("returnDate") != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("returnDate")) : "N/A").append("</td>")
                        .append("<td>").append(rs.getString("status")).append("</td>")
                        .append("</tr>");
                hasResults = true;
            }

            if (currentRequestID != -1) {
                htmlResponse.append("</tbody>")
                        .append("</table>")
                        .append("<button onclick='returnEquipment(").append(currentRequestID).append(")' class='btn btn-primary'>Return All</button>");
            }

            if (!hasResults) {
                htmlResponse.append("<p>You have no borrowing records.</p>");
            }

            htmlResponse.append("</div>") // Close card-body
                    .append("</div>") // Close card
                    .append("</div>") // Close col
                    .append("</div>"); // Close row
        } catch (SQLException e) {
            e.printStackTrace();
            return "<div class='alert alert-danger' role='alert'>Error fetching equipment return details: " + e.getMessage() + "</div>";
        }

        return htmlResponse.toString();
    }

    private String fetchVenueReturnDetails(int userId) {
        StringBuilder html = new StringBuilder("<div class='row'><div class='col-md-12 col-lg-12'><div class='card'><div class='card-header'>Venue Return Details</div><div class='card-body'>");
        String sql = "SELECT bookingID, VenuevenueID, bookingDate, checkinTime, checkoutTime, status "
                + "FROM venuebooking WHERE status = 'approved' AND requesterID = ?"; // Ensure you only show venues booked by the logged-in user

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);  // Assuming the user ID is passed to the method
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                html.append("<p>No approved venue bookings to return.</p>");
            } else {
                html.append("<table class='table table-striped'>")
                        .append("<thead>")
                        .append("<tr>")
                        .append("<th>Booking ID</th>")
                        .append("<th>Venue ID</th>")
                        .append("<th>Date</th>")
                        .append("<th>Time Slot</th>")
                        .append("<th>Status</th>")
                        .append("<th>Action</th>")
                        .append("</tr>")
                        .append("</thead>")
                        .append("<tbody>");

                while (rs.next()) {
                    int bookingID = rs.getInt("bookingID");
                    int venueID = rs.getInt("VenuevenueID");
                    String bookingDate = rs.getString("bookingDate");
                    String checkinTime = rs.getString("checkinTime");
                    String checkoutTime = rs.getString("checkoutTime");
                    String status = rs.getString("status");

                    html.append("<tr>")
                            .append("<td>").append(bookingID).append("</td>")
                            .append("<td>").append(venueID).append("</td>")
                            .append("<td>").append(bookingDate).append("</td>")
                            .append("<td>").append(checkinTime).append(" - ").append(checkoutTime).append("</td>")
                            .append("<td>").append(status).append("</td>")
                            .append("<td><button class='btn btn-primary' onclick='initiateReturn(").append(bookingID).append(")'>Return</button></td>")
                            .append("</tr>");
                }
                html.append("</tbody></table>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            html.append("<p>Error fetching venue return details: ").append(e.getMessage()).append("</p>");
        }

        html.append("</div></div></div></div></div>");
        return html.toString();
    }
}
