/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

/**
 *
 * @author kenneth
 */
import ict.bean.VenueBooking;
import java.sql.*;

public class VenueBookingDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    public boolean saveBooking(VenueBooking booking) {
        String INSERT_BOOKING_SQL = "INSERT INTO venuebooking (requesterID, responderID, VenuevenueID, requestDatetime, bookingDate, checkinTime, checkoutTime, status) VALUES (?, NULL, ?, NOW(), ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKING_SQL)) {

            preparedStatement.setInt(1, booking.getRequesterID());
            preparedStatement.setInt(2, booking.getVenueVenueID());
            preparedStatement.setDate(3, booking.getBookingDate());
            preparedStatement.setString(4, booking.getCheckinTime());
            preparedStatement.setString(5, booking.getCheckoutTime());
            preparedStatement.setString(6, booking.getStatus());

            int result = preparedStatement.executeUpdate();
            System.out.println("Booking inserted successfully: " + result); // Log the result of insertion
            return result > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage()); // Log SQL error message
            e.printStackTrace();
        }
        return false;
    }

}
