/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.io.IOException;
import java.sql.*;

import ict.bean.EquipmentBean;
import java.util.ArrayList;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */
public class EquipmentDB {

    private String url = "";
    private String username = "";
    private String password = "";

    public EquipmentDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }

    public boolean addRecord(String CustId, String Name, String Tel, int Age) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO Equipment VALUES (?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
//            pStmnt.setString(1, CustId);
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

            String preQueryStatement = "UPDATE equipment SET name = ?, location = ?, description = ?, status = ?,category = ?,imgSrc = ?,isStaff = ? WHERE equipmentID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, eb.getName());
            pStmnt.setString(2, eb.getLocation());
            pStmnt.setString(3, eb.getDescription());
            pStmnt.setString(4, eb.getStatus());
            pStmnt.setString(5, eb.getCategory());
            pStmnt.setString(6, eb.getImgSrc());
            pStmnt.setString(7, eb.getIsStaff());
            pStmnt.setInt(8, eb.getEquipmentID());

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

  public boolean createRecord(EquipmentBean equipment) {
    String url = "jdbc:mysql://localhost:3306/4511_asm";
    String username = "root";
    String password = "";

    try (Connection conn = DriverManager.getConnection(url, username, password)) {
        // Find the maximum equipmentID in the table
        String maxIdQuery = "SELECT MAX(equipmentID) AS maxId FROM equipment";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(maxIdQuery);
        int maxId = 0;
        if (rs.next()) {
            maxId = rs.getInt("maxId");
        }

        // Increment the maxId to generate the next equipmentID
        int nextId = maxId + 1;

        // Set the equipmentID of the equipment object
        equipment.setEquipmentID(nextId);

        // Insert the new equipment record into the table
        String insertQuery = "INSERT INTO equipment (equipmentID, name, location, description, status, category, imgSrc, isStaff) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
        pstmt.setInt(1, maxId+1);
        pstmt.setString(2, equipment.getName());
        pstmt.setString(3, equipment.getLocation());
        pstmt.setString(4, equipment.getDescription());
        pstmt.setString(5, equipment.getStatus());
        pstmt.setString(6, equipment.getCategory());
        pstmt.setString(7, equipment.getImgSrc());
        pstmt.setString(8, equipment.getIsStaff());
        pstmt.executeUpdate();

        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean delRecord(int equipID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean deletionSuccessful = false;

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "DELETE FROM equipment WHERE equipmentID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement); // get the prepare Statement
            pStmnt.setInt(1, equipID); // update the placehoder with id

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                deletionSuccessful = true;
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
        return deletionSuccessful;
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
                cb.setEquipmentID(rs.getInt("equipmentID"));
                cb.setName(rs.getString("name"));
                cb.setLocation(rs.getString("location"));
                cb.setDescription(rs.getString("description"));
                cb.setStatus(rs.getString("status"));
                cb.setCategory(rs.getString("category"));
                cb.setImgSrc(rs.getString("imgSrc"));
               
                cb.setIsStaff(rs.getString("isStaff"));
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

    public ArrayList<EquipmentBean> getAllAvailableEquipment() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        EquipmentBean cb = null;
        ArrayList<EquipmentBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT * FROM Equipment WHERE status = 'available' AND isStaff IS NULL";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {
                cb = new EquipmentBean();
                cb.setEquipmentID(rs.getInt("equipmentID"));
                cb.setName(rs.getString("name"));
                cb.setLocation(rs.getString("location"));
                cb.setDescription(rs.getString("description"));
                cb.setStatus(rs.getString("status"));
                cb.setCategory(rs.getString("category"));
                cb.setImgSrc(rs.getString("imgSrc"));

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

    public ArrayList<EquipmentBean> getAllAvailableEquipmentS() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        EquipmentBean cb = null;
        ArrayList<EquipmentBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT * FROM Equipment WHERE status = 'available'";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {
                cb = new EquipmentBean();
                cb.setEquipmentID(rs.getInt("equipmentID"));
                cb.setName(rs.getString("name"));
                cb.setLocation(rs.getString("location"));
                cb.setDescription(rs.getString("description"));
                cb.setStatus(rs.getString("status"));
                cb.setCategory(rs.getString("category"));
                cb.setImgSrc(rs.getString("imgSrc"));

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
    
    public EquipmentBean getEquipmentById(Integer equipmentId) throws SQLException, IOException {
        EquipmentBean equipment = null;
        String sql = "SELECT * FROM equipment WHERE equipmentID = ?";
        try (Connection conn = this.getConnection(); // Assuming getConnection() handles database connections
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipmentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                equipment = new EquipmentBean();
                equipment.setEquipmentID(rs.getInt("equipmentID"));
                equipment.setName(rs.getString("name"));
                equipment.setDescription(rs.getString("description"));
                equipment.setLocation(rs.getString("location"));
                equipment.setStatus(rs.getString("status"));
                // Assume other setters as necessary
            }
        }
        return equipment;
    }
}
