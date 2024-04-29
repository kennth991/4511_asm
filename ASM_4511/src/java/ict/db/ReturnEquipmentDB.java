/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;


import java.io.IOException;
import java.sql.*;

import ict.bean.ReturnEquipmentBean;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */
public class ReturnEquipmentDB {

    private String url = "";
    private String username = "";
    private String password = "";
    private int userid;

    public ReturnEquipmentDB(String url, String username, String password) {
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

    public ArrayList<ArrayList<ReturnEquipmentBean>> queryEquip() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<ArrayList<ReturnEquipmentBean>> requestList = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT er.requestID, er.requestDateTime, er.status, u.name as requesterName, "
                    + "eq.equipmentID, eq.name as equipmentName, eq.location "
                    + "FROM equipmentrequest er "
                    + "JOIN equipmentrequest_equipment ere ON er.requestID = ere.EquipmentRequestrequestID "
                    + "JOIN equipment eq ON eq.equipmentID = ere.EquipmentequipmentID "
                    + "JOIN user u ON u.userID = er.requesterID "
                    + // Ensuring to fetch requester's name
                    "WHERE er.status = 'return pending' "
                    + "ORDER BY er.requestID, er.requestDateTime";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // execute the query and assign to the result
            rs = pStmnt.executeQuery();

            int currentRequestID = -1;
            ArrayList<ReturnEquipmentBean> currentRequest = null;

            while (rs.next()) {
                int requestID = rs.getInt("requestID");
                if (requestID != currentRequestID) {
                    // New request encountered, initialize a new ArrayList for it
                    currentRequest = new ArrayList<>();
                    requestList.add(currentRequest);
                    currentRequestID = requestID;
                }

                ReturnEquipmentBean cb = new ReturnEquipmentBean();
                cb.setRequestId(requestID);
                cb.setRequestDateTime(rs.getString("requestDateTime"));
                cb.setStatus(rs.getString("status"));
                cb.setRequesterName(rs.getString("requesterName"));
                cb.setEquipmentId(rs.getInt("equipmentID"));
                cb.setEquipmentName(rs.getString("equipmentName"));
                cb.setLocation(rs.getString("location"));

                currentRequest.add(cb);
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
        return requestList;
    }
public boolean editRecord(String requestID, String equipmentId, String returnStatus, HttpSession session) {
    Connection cnnct = null;
    PreparedStatement pStmnt = null;
    boolean updateSuccessful = false;

    try {
        cnnct = getConnection(); // get Connection
        userid = (int) session.getAttribute("userId");

        String query = "UPDATE equipmentrequest_equipment SET status = ? WHERE EquipmentRequestrequestID = ? AND EquipmentequipmentID = ?";
        pStmnt = cnnct.prepareStatement(query);
        pStmnt.setString(1, returnStatus);
        pStmnt.setString(2, requestID);
        pStmnt.setString(3, equipmentId);

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

public boolean finishRecord(String requestID ) {
    Connection cnnct = null;
    PreparedStatement pStmnt = null;
    boolean updateSuccessful = false;

    try {
        cnnct = getConnection(); // get Connection
        
        String query = "UPDATE equipmentrequest SET status = ? WHERE requestID = ? ";
        pStmnt = cnnct.prepareStatement(query);
        pStmnt.setString(1, "returned");
        pStmnt.setString(2, requestID);
       

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
public void damageRecord(String damageReport, String equipmentId, HttpSession session) {
    Connection cnnct = null;
    PreparedStatement pStmnt = null;

    try {
        cnnct = getConnection(); // Get connection
        int userId = (int) session.getAttribute("userId");

        // Insert new damage report
        int reportId = getMaxDamageReportId(cnnct) + 1;
        String damageReportQuery = "INSERT INTO damagereport (reportID, description, proposerID, status, reportDateTime, EquipmentequipmentID) VALUES (?, ?, ?, ?, NOW(), ?)";
        pStmnt = cnnct.prepareStatement(damageReportQuery);
        pStmnt.setInt(1, reportId);
        System.out.print(reportId);
        pStmnt.setString(2, damageReport);
        pStmnt.setInt(3, userId);
        pStmnt.setString(4, "reporting");
        pStmnt.setString(5, equipmentId);

        int insertedRows = pStmnt.executeUpdate();
        if (insertedRows >= 1) {
            System.out.println("Damage report created successfully.");
        }

        // Update equipment table
        String updateEquipmentQuery = "UPDATE equipment SET status = ? WHERE equipmentID = ?";
        pStmnt = cnnct.prepareStatement(updateEquipmentQuery);
        pStmnt.setString(1, "unavailable");
        pStmnt.setString(2, equipmentId);

        int updatedRows = pStmnt.executeUpdate();
        if (updatedRows >= 1) {
            System.out.println("Equipment status updated successfully.");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    } catch (IOException ex) {
        ex.printStackTrace();
    }finally {
        try {
            if (pStmnt != null) {
                pStmnt.close();
            }
            if (cnnct != null) {
                cnnct.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    private int getMaxDamageReportId(Connection cnnct) throws SQLException {
        int maxId = 0;
        Statement stmt = cnnct.createStatement();
        String query = "SELECT MAX(reportID) FROM damagereport";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            maxId = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        return maxId;
    }

}
