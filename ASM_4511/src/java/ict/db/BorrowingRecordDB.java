/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.BorrowingRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author kenneth
 */

public class BorrowingRecordDB {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/4511_asm";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    public List<BorrowingRecord> getBorrowingRecordsByUserId(int userId) {
        List<BorrowingRecord> records = new ArrayList<>();
        String query = "SELECT er.requestID, eq.name AS equipmentName, er.requestDateTime, er.startDate, er.returnDate, er.status " +
                       "FROM equipmentrequest er " +
                       "JOIN equipmentrequest_equipment ere ON er.requestID = ere.EquipmentRequestrequestID " +
                       "JOIN equipment eq ON ere.EquipmentequipmentID = eq.equipmentID " +
                       "WHERE er.requesterID = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BorrowingRecord record = new BorrowingRecord();
                record.setRecordId(rs.getInt("requestID"));
                record.setUserId(userId);
                record.setEquipmentName(rs.getString("equipmentName"));
                record.setRequestDateTime(rs.getTimestamp("requestDateTime"));
                record.setStartDate(rs.getDate("startDate"));
                record.setReturnDate(rs.getDate("returnDate"));
                record.setStatus(rs.getString("status"));
                records.add(record);
                System.out.println("Loaded record: " + record.getEquipmentName());  // Debugging line
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}

