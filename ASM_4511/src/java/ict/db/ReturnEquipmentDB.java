/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.io.IOException;
import java.sql.*;

import ict.bean.ReturnEquipmentBean;
import java.util.ArrayList;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */
public class ReturnEquipmentDB {

    private String url = "";
    private String username = "";
    private String password = "";

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
        String preQueryStatement = "SELECT er.requestID, er.requestDateTime, er.status, u.name as requesterName, " +
                 "eq.equipmentID, eq.name as equipmentName, eq.location " +
                 "FROM equipmentrequest er " +
                 "JOIN equipmentrequest_equipment ere ON er.requestID = ere.EquipmentRequestrequestID " +
                 "JOIN equipment eq ON eq.equipmentID = ere.EquipmentequipmentID " +
                 "JOIN user u ON u.userID = er.requesterID " +  // Ensuring to fetch requester's name
                 "WHERE er.status = 'return pending' " +
                 "ORDER BY er.requestID, er.requestDateTime";
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
  
   
}
