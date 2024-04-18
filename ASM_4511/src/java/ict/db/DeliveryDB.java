package ict.db;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.IOException;
import java.sql.*;

import ict.bean.DeliveryBean;
import java.util.ArrayList;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */
public class DeliveryDB {

    private String url = "";
    private String username = "";
    private String password = "";

    public DeliveryDB(String url, String username, String password) {
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

    public ArrayList<DeliveryBean> queryDeliveryList(int userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        DeliveryBean cb = null;
        ArrayList<DeliveryBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT d.deliveryID, d.status, eq.location AS respondLocation, ee.requestLocation, eq.name\n"
                    + "FROM delivery d\n"
                    + "JOIN equipmentrequest_equipment ee ON ee.DeliverydeliveryID = d.deliveryID\n"
                    + "JOIN equipment eq ON ee.EquipmentequipmentID = eq.equipmentID\n"
                    + "WHERE d.courierID = ? AND d.status = 'waiting';";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userID);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {
                cb = new DeliveryBean();
                cb.setDeliveryID(rs.getInt("deliveryID"));
                cb.setStatus(rs.getString("status"));
                cb.setRespondLocation(rs.getString("respondLocation"));
                cb.setRequestLocation(rs.getString("requestLocation"));
                cb.setEquipmentName(rs.getString("name"));
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

    public ArrayList<DeliveryBean> queryDeliveryListPlace(int userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        DeliveryBean cb = null;
        ArrayList<DeliveryBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT d.deliveryID, d.status, eq.location AS respondLocation, ee.requestLocation, eq.name\n"
                    + "FROM delivery d\n"
                    + "JOIN equipmentrequest_equipment ee ON ee.DeliverydeliveryID = d.deliveryID\n"
                    + "JOIN equipment eq ON ee.EquipmentequipmentID = eq.equipmentID\n"
                    + "WHERE d.courierID = ? AND d.status = 'pickuped';";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userID);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {
                cb = new DeliveryBean();
                cb.setDeliveryID(rs.getInt("deliveryID"));
                cb.setStatus(rs.getString("status"));
                cb.setRespondLocation(rs.getString("respondLocation"));
                cb.setRequestLocation(rs.getString("requestLocation"));
                cb.setEquipmentName(rs.getString("name"));
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

    public boolean pickupDelivery(int deliveryId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean updateSuccessful = false;

        try {
            cnnct = getConnection(); // 取得連線
            String preQueryStatement = "UPDATE delivery SET status = 'pickuped' WHERE deliveryID = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement); // 取得預備陳述式
            pStmnt.setInt(1, deliveryId);

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

    public boolean placeDelivery(int deliveryId) {
        Connection cnnct = null;
        PreparedStatement pStmnt1 = null;
        PreparedStatement pStmnt2 = null;
        boolean updateSuccessful = false;

        try {
            cnnct = getConnection(); // 取得連線

            // 更新delivery表的status欄位為"place"
            String deliveryUpdateStatement = "UPDATE delivery SET status = 'place' WHERE deliveryID = ?;";
            pStmnt1 = cnnct.prepareStatement(deliveryUpdateStatement); // 取得預備陳述式1
            pStmnt1.setInt(1, deliveryId);
            pStmnt1.executeUpdate();

            // 更新equipmentrequest_equipment表的status欄位為"borrowing"
            String equipmentUpdateStatement = "UPDATE equipmentrequest_equipment SET status = 'borrowing' WHERE DeliverydeliveryID = ?;";
            pStmnt2 = cnnct.prepareStatement(equipmentUpdateStatement); // 取得預備陳述式2
            pStmnt2.setInt(1, deliveryId);
            pStmnt2.executeUpdate();

            updateSuccessful = true;

            pStmnt1.close();
            pStmnt2.close();
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

}
