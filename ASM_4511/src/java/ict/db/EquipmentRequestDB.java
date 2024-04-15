/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.EquipmentRequestBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String preQueryStatement = "SELECT e.requestID, e.requesterID, e.responderID, e.requestDateTime, e.returnDate, e.startDate, e.status, u.userName "
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
                cb.setRequesterName(rs.getString("userName"));
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
