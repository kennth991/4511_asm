/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.EquipmentBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */




public class EquipmentDB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/4511_asm";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    // Ensure the driver is loaded once (optional in newer Java versions)
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // This should be logged properly in a real-world application
        }
    }

    private Connection getConnection() throws SQLException {
        // Handling only SQLException because the driver is loaded statically
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    public ArrayList<EquipmentBean> queryEquip() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        EquipmentBean cb = null;
        ArrayList<EquipmentBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT * FROM Equipment";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {
                cb = new EquipmentBean();
                cb.setEquipmentId(rs.getInt("equipmentID"));
                cb.setName(rs.getString("name"));
                cb.setLocation(rs.getString("location"));
                cb.setDescription(rs.getString("description"));
                cb.setStatus(rs.getString("status"));
                
                equipments.add(cb);
            }

            rs.close();
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return equipments;
    }
    public void createEquipTable() {
        Statement stmnt = null;
        Connection cnnct = null;

        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Equipment ("
                    + "equipmentID INT AUTO_INCREMENT,"
                    + "name varchar(50) NOT NULL,"
                    + "description varchar(50) NOT NULL,"
                    + "qty int(4) NOT NULL,"
                    + "PRIMARY KEY (equipmentID)"
                    + ")";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addRecord(String CustId, String Name, String Tel, int Age) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO Equipment VALUES (?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, CustId);
            pStmnt.setString(2, Name);
            pStmnt.setString(3, Tel);
            pStmnt.setInt(4, Age);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }

    public boolean editRecord(EquipmentBean eb) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean updateSuccessful = false;

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "UPDATE equipment SET name = ?, location = ?, description = ?, status = ? WHERE equipmentID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, eb.getName());
            pStmnt.setString(2, eb.getLocation());
            pStmnt.setString(3, eb.getDescription());
            pStmnt.setString(4, eb.getStatus());
            pStmnt.setInt(5, eb.getEquipmentId());

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                updateSuccessful = true;
            }

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return updateSuccessful;
    }

    public List<EquipmentBean> getAllAvailableEquipment() throws SQLException {
        List<EquipmentBean> equipments = new ArrayList<>();
        String sql = "SELECT * FROM equipment WHERE status = 'available'";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentID(rs.getInt("equipmentID"));
                equipment.setName(rs.getString("name"));
                equipment.setDescription(rs.getString("description"));
                equipment.setLocation(rs.getString("location"));
                equipment.setStatus(rs.getString("status"));
                equipment.setCategory(rs.getString("category"));
                equipment.setImgSrc(rs.getString("imgSrc"));
                equipments.add(equipment);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage()); // This should also be logged using a logging framework
            throw e; // Rethrow the exception to handle it in a higher layer (e.g., in the servlet)
        }
        return equipments;
    }
}
