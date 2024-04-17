/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *
 * @author kenneth
 */
@WebServlet("/AvailableSlotsServlet")
public class AvailableSlotsServlet extends HttpServlet {

    private String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int venueId = Integer.parseInt(request.getParameter("venueId"));
        String bookingDate = request.getParameter("bookingDate");

        System.out.println("Fetching available slots for venue ID: " + venueId + " on date: " + bookingDate);
        List<String> availableSlots = getAvailableTimeSlots(venueId, bookingDate);
        response.setContentType("application/json");
        response.getWriter().write(convertListToJson(availableSlots));
    }

    private List<String> getAvailableTimeSlots(int venueId, String bookingDate) {
    List<String> bookedSlots = new ArrayList<>();
    String query = "SELECT checkinTime FROM venuebooking WHERE VenuevenueID = ? AND bookingDate = ? AND (status = 'approved' OR status = 'reserved')";
    try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, venueId);
        preparedStatement.setDate(2, java.sql.Date.valueOf(bookingDate));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            bookedSlots.add(rs.getString("checkinTime"));
            System.out.println("Booked time slot fetched: " + rs.getString("checkinTime"));
        }
    } catch (SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
        e.printStackTrace();
    }
    List<String> availableSlots = filterAvailableSlots(bookedSlots);
    System.out.println("Available slots after filtering: " + availableSlots);
    return availableSlots;
}

    private List<String> filterAvailableSlots(List<String> bookedSlots) {
        List<String> allSlots = Arrays.asList("09:00", "11:00", "13:00", "15:00");
        List<String> formattedBookedSlots = bookedSlots.stream()
                .map(slot -> slot.substring(0, 5))
                .collect(Collectors.toList());
        return allSlots.stream()
                .filter(slot -> !formattedBookedSlots.contains(slot))
                .collect(Collectors.toList());
    }

    private String convertListToJson(List<String> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append("\"").append(list.get(i)).append("\"");
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
