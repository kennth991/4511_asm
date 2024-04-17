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
import java.util.ArrayList;
import java.util.List;

public class VenueBookingDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC driver not found.", e);
        }
    }
    
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

    public List<VenueBooking> listAllBookings() {
        List<VenueBooking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM venuebooking ORDER BY requestDatetime DESC"; // Fetching all bookings

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VenueBooking booking = new VenueBooking();
                booking.setBookingID(rs.getInt("bookingID"));
                booking.setRequesterID(rs.getInt("requesterID"));
                booking.setVenueVenueID(rs.getInt("VenuevenueID"));
                booking.setRequestDatetime(rs.getTimestamp("requestDatetime"));
                booking.setBookingDate(rs.getDate("bookingDate"));
                booking.setCheckinTime(rs.getString("checkinTime"));
                booking.setCheckoutTime(rs.getString("checkoutTime"));
                booking.setStatus(rs.getString("status"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean updateResponder(int bookingId, int responderId) {
        String sql = "UPDATE venuebooking SET responderID = ? WHERE bookingID = ?";
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, responderId);
            stmt.setInt(2, bookingId);
            int count = stmt.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBookingStatus(int bookingId, String status, Integer responderId) {
        String sql = "UPDATE venuebooking SET status = ?, responderID = ? WHERE bookingID = ?";
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            if (responderId != null) {
                stmt.setInt(2, responderId);
            } else {
                stmt.setNull(2, Types.INTEGER); // Set to NULL if responderId is not provided
            }
            stmt.setInt(3, bookingId);
            int count = stmt.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
