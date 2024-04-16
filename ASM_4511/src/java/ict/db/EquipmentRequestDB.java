/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.EquipmentRequestBean;
import ict.bean.EquipmentRequestEquipmentBean;
import ict.bean.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SmartCity_TY
 */
public class EquipmentRequestDB {

    private String url = "";
    private String username = "";
    private String password = "";

    public EquipmentRequestDB(String url, String username, String password) {
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

    public ArrayList<EquipmentRequestBean> queryEquip() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        EquipmentRequestBean cb = null;
        ArrayList<EquipmentRequestBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT e.requestID, e.requesterID, e.responderID, e.requestDateTime, e.returnDate, e.startDate, e.status, u.name "
                    + "FROM equipmentrequest e "
                    + "JOIN user u ON e.requesterID = u.userID";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {
                cb = new EquipmentRequestBean();
                cb.setRequestID(rs.getInt("requestID"));
                cb.setRequesterID(rs.getInt("requesterID"));
                cb.setResponderID(rs.getInt("responderID"));
                cb.setRequestDateTime(rs.getString("requestDateTime"));
                cb.setReturnDate(rs.getString("returnDate"));
                cb.setStartDate(rs.getString("startDate"));
                cb.setStatus(rs.getString("status"));
                cb.setRequesterName(rs.getString("name"));
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

    public ArrayList<EquipmentRequestEquipmentBean> queryEquipList(int equipmentrequestID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        EquipmentRequestEquipmentBean equipBean = null;
        ArrayList<EquipmentRequestEquipmentBean> equipList = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT er_e.EquipmentRequestrequestID, er_e.EquipmentequipmentID, er_e.requestLocation, er_e.respondLocation, "
                    + "u.name AS userName, er.requestDateTime, er.returnDate, er.startDate, er.status, e.name AS equipmentName, e.location AS equipmentLocation "
                    + "FROM equipmentrequest_equipment er_e "
                    + "JOIN equipmentrequest er ON er.requestID = er_e.EquipmentRequestrequestID "
                    + "JOIN user u ON u.userID = er.requestID "
                    + "JOIN equipment e ON e.equipmentID = er_e.EquipmentequipmentID "
                    + "WHERE er_e.EquipmentRequestrequestID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipmentrequestID);

            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                equipBean = new EquipmentRequestEquipmentBean();
                equipBean.setEquipmentRequestID(rs.getInt("EquipmentRequestrequestID"));
                equipBean.setEquipmentID(rs.getInt("EquipmentequipmentID"));
                equipBean.setRequestLocation(rs.getString("requestLocation"));
                equipBean.setRespondLocation(rs.getString("respondLocation"));
                equipBean.setUserName(rs.getString("userName"));
                equipBean.setRequestDateTime(rs.getString("requestDateTime"));
                equipBean.setReturnDate(rs.getString("returnDate"));
                equipBean.setStartDate(rs.getString("startDate"));
                equipBean.setStatus(rs.getString("status"));
                equipBean.setEquipmentName(rs.getString("equipmentName"));
                equipBean.setEquipmentLocation(rs.getString("equipmentLocation"));
                equipList.add(equipBean);
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
        return equipList;
    }

    public ArrayList<User> queryCourList() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        User courierBean = null;
        ArrayList<User> courierList = new ArrayList<>();

        try {

            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT * FROM `user` WHERE role = 'courier';";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                courierBean = new User();
                courierBean.setName(rs.getString("name"));
                courierBean.setUserID(rs.getInt("userID"));

                courierList.add(courierBean);
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
        return courierList;
    }

    public boolean rejectRequest(int requestID, int equipmentID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();

            // Update equipmentrequest_equipment table
            String updateQuery = "UPDATE equipmentrequest_equipment SET status = 'assigned' WHERE EquipmentRequestrequestID = ? AND EquipmentequipmentID = ?";
            pStmnt = cnnct.prepareStatement(updateQuery);
            pStmnt.setInt(1, requestID);
            pStmnt.setInt(2, equipmentID);

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

   public boolean assignRequestEquipment(int requestID, int equipmentID, int userId) {
    Connection cnnct = null;
    PreparedStatement pStmnt = null;
    boolean isSuccess = false;
    try {
        cnnct = getConnection();

        // Retrieve the maximum deliveryID from the delivery table
        String maxIdQuery = "SELECT MAX(deliveryID) FROM delivery";
        Statement statement = cnnct.createStatement();
        ResultSet resultSet = statement.executeQuery(maxIdQuery);
        int maxId = 0;
        if (resultSet.next()) {
            maxId = resultSet.getInt(1);
        }
        resultSet.close();

        // Update equipmentrequest_equipment table
        String updateQuery = "UPDATE equipmentrequest_equipment SET DeliverydeliveryID = ?, status = 'assigned' WHERE EquipmentRequestrequestID = ? AND EquipmentequipmentID = ?";
        pStmnt = cnnct.prepareStatement(updateQuery);
        pStmnt.setInt(1, maxId);
        pStmnt.setInt(2, requestID);
        pStmnt.setInt(3, equipmentID);

        int rowCount = pStmnt.executeUpdate();
        pStmnt.close();

        // Update equipmentrequest table
        String updateRequestQuery = "UPDATE equipmentrequest SET status = 'assigned', responderID = ? WHERE requestID = ?";
        pStmnt = cnnct.prepareStatement(updateRequestQuery);
        pStmnt.setInt(1, userId);
        pStmnt.setInt(2, requestID);

        int requestRowCount = pStmnt.executeUpdate();
        if (requestRowCount >= 1) {
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

    public boolean assignRequest(int assignedCourier, int userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            // Retrieve the maximum deliveryID from the delivery table
            String maxIdQuery = "SELECT MAX(deliveryID) FROM delivery";
            Statement statement = cnnct.createStatement();
            int maxId;
            try (ResultSet resultSet = statement.executeQuery(maxIdQuery)) {
                maxId = 0;
                if (resultSet.next()) {
                    maxId = resultSet.getInt(1);
                }
            }

           String preQueryStatement = "INSERT INTO delivery VALUES (?,?,?,'waiting',null,null)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, maxId + 1); // Set the next available ID
            pStmnt.setInt(2, assignedCourier);
            pStmnt.setInt(3, userID);

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

}
