/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.Venue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kenneth
 */

public class VenueDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/4511_asm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String SELECT_ALL_VENUES = "SELECT * FROM venue WHERE status = 'open'";

    public VenueDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Venue> listAllVenues() {
        List<Venue> venues = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VENUES)) {
            System.out.println("Executing query: " + SELECT_ALL_VENUES);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("venueID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String status = rs.getString("status");
                String location = rs.getString("location");
                String imgSrc = rs.getString("imgSrc");

                venues.add(new Venue(id, name, description, status, location, imgSrc));
                System.out.println("Found venue: " + name);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        if (venues.isEmpty()) {
            System.out.println("No venues found.");
        }
        return venues;
    }

    public List<String> getBookedSlots(int venueId, String bookingDate) {
        List<String> bookedSlots = new ArrayList<>();
        String sql = "SELECT checkinTime FROM venuebooking WHERE VenuevenueID = ? AND bookingDate = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, venueId);
            stmt.setDate(2, Date.valueOf(bookingDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookedSlots.add(rs.getString("checkinTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookedSlots;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
