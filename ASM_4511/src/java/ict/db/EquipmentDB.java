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

            String preQueryStatement = "UPDATE equipment SET name = ?, location = ?, description = ?, status = ?,category = ?,imgSrc = ? WHERE equipmentID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, eb.getName());
            pStmnt.setString(2, eb.getLocation());
            pStmnt.setString(3, eb.getDescription());
            pStmnt.setString(4, eb.getStatus());
            pStmnt.setString(5, eb.getCategory());
            pStmnt.setString(6, eb.getImgSrc());
            pStmnt.setInt(7, eb.getEquipmentID());

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
            String preQueryStatement = "SELECT * FROM Equipment WHERE 'status'= 'available'";
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
}
