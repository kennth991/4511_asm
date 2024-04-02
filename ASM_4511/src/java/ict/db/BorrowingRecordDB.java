/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

/**
 *
 * @author kenneth
 */

import ict.bean.BorrowingRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowingRecordDB {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/4511_asm";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    public List<BorrowingRecord> getBorrowingRecordsByUserId(int userId) {
    List<BorrowingRecord> records = new ArrayList<>();
    // Adjusted query to join with the Equipment table and select the equipment name
    String query = "SELECT br.*, eq.name AS equipmentName FROM BorrowingRecord br JOIN Equipment eq ON br.equipment_id = eq.equipment_id WHERE br.user_id = ?";
    
    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            BorrowingRecord record = new BorrowingRecord();
            record.setRecordId(rs.getInt("record_id"));
            record.setUserId(rs.getInt("user_id"));
            record.setEquipmentId(rs.getString("equipment_id"));
            record.setCheckoutDate(rs.getDate("checkout_date"));
            record.setExpectedReturnDate(rs.getDate("expected_return_date"));
            record.setActualReturnDate(rs.getDate("actual_return_date"));
            record.setStatus(rs.getString("status"));
            record.setComments(rs.getString("comments"));
            record.setEquipmentName(rs.getString("equipmentName")); // Assuming you add this property to your BorrowingRecord bean
            records.add(record);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return records;
}

}
