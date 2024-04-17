/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import ict.bean.WishListEquipmentBean;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */
public class WishListEquipmentDB {

    private String url = "";
    private String username = "";
    private String password = "";
    public int userid;

    public WishListEquipmentDB(String url, String username, String password) {
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

    public ArrayList<WishListEquipmentBean> queryEquip() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        WishListEquipmentBean cb = null;
        ArrayList<WishListEquipmentBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT\n"
                    + "    wishlist_equipment.requestDateTime,\n"
                     + "    wishlist_equipment.WishListwishlistID,\n"
                    + "    wishlist_equipment.status,\n"
                    + "    user.name AS requester,\n"
                    + "    equipment.equipmentID,\n"
                    + "    equipment.name AS equipment,\n"
                    + "    equipment.location\n"
                    + "FROM\n"
                    + "    equipment\n"
                    + "JOIN\n"
                    + "    wishlist_equipment ON wishlist_equipment.EquipmentequipmentID = equipment.equipmentID\n"
                    + "JOIN\n"
                    + "    wishlist ON wishlist_equipment.WishListwishlistID = wishlist.whislistID\n"
                    + "JOIN\n"
                    + "    user ON wishlist.requesterID = user.userID\n"
                    + "WHERE\n"
                    + "    equipment.status = 'available'\n"
                    + "    AND wishlist_equipment.status = 'Pending';";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {

                cb = new WishListEquipmentBean();
                cb.setWishListwishID(rs.getInt("WishListwishlistID"));
                cb.setEquipmentequipmentID(rs.getInt("equipmentID"));
                //cb.setResponderID(rs.getInt("responderID"));
                cb.setStatus(rs.getString("status"));
                cb.setRequestDateTime(rs.getString("requestDateTime"));
                //cb.setRespondDateTime(rs.getString("respondDateTime"));
                cb.setRequesterName(rs.getString("requester"));
                cb.setEquipmentName(rs.getString("equipment"));
                cb.setLocation(rs.getString("location"));
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

    public ArrayList<WishListEquipmentBean> queryEquipAvailable() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        WishListEquipmentBean cb = null;
        ArrayList<WishListEquipmentBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT\n"
                    + "    wishlist_equipment.requestDateTime,\n"
                    + "    wishlist_equipment.status,\n"
                    + "    user.name AS requester,\n"
                    + "    equipment.equipmentID,\n"
                    + "    equipment.name AS equipment,\n"
                    + "    equipment.location\n"
                    + "FROM\n"
                    + "    equipment\n"
                    + "JOIN\n"
                    + "    wishlist_equipment ON wishlist_equipment.EquipmentequipmentID = equipment.equipmentID\n"
                    + "JOIN\n"
                    + "    wishlist ON wishlist_equipment.WishListwishlistID = wishlist.whislistID\n"
                     + "    user ON wishlist.requesterID = user.userID\n"
                    + "WHERE\n"
                    + "    equipment.status = 'unavailable'\n"
                    + "    AND wishlist_equipment.status = 'Pending';";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {

                cb = new WishListEquipmentBean();
                //cb.setWishListwhisID(rs.getInt("WishListwishlistID"));
                cb.setEquipmentequipmentID(rs.getInt("equipmentID"));
                //cb.setResponderID(rs.getInt("responderID"));
                cb.setStatus(rs.getString("status"));
                cb.setRequestDateTime(rs.getString("requestDateTime"));
                //cb.setRespondDateTime(rs.getString("respondDateTime"));
                cb.setRequesterName(rs.getString("requester"));
                cb.setEquipmentName(rs.getString("equipment"));
                cb.setLocation(rs.getString("location"));
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

    public boolean deleteWishListEquipment(int wishListID, int equipmentID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean deletionSuccessful = false;

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "DELETE FROM wishlist_equipment WHERE WishListwishlistID = ? AND EquipmentequipmentID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement); // get the prepare Statement
            pStmnt.setInt(1, wishListID);
            pStmnt.setInt(2, equipmentID);// update the placehoder with id

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
    
   public boolean confirmWishList(int wishListID, int equipmentID) {
    Connection cnnct = null;
    PreparedStatement pStmnt = null;
    boolean updateSuccessful = false;

    try {
        cnnct = getConnection(); // get Connection
        String preQueryStatement = "UPDATE wishlist_equipment SET status = 'approved' WHERE WishListwishlistID = ? AND EquipmentequipmentID = ?";
        pStmnt = cnnct.prepareStatement(preQueryStatement); // get the prepare Statement
        pStmnt.setInt(1, wishListID);
        pStmnt.setInt(2, equipmentID); // update the placeholder with id

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

    public ArrayList<WishListEquipmentBean> queryWishList() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        WishListEquipmentBean cb = null;
        ArrayList<WishListEquipmentBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            // Retrieve the userId from the session

            String sql = "SELECT  wishlist_equipment.WishListwishlistID, wishlist_equipment.requestDateTime, wishlist_equipment.status, user.name AS responder, equipment.equipmentID, equipment.name AS equipment, equipment.location "
                    + "FROM equipment "
                    + "JOIN wishlist_equipment ON wishlist_equipment.EquipmentequipmentID = equipment.equipmentID "
                    + "JOIN wishlist ON wishlist_equipment.WishListwishlistID = wishlist.whislistID "
                    + "JOIN user ON wishlist_equipment.responderID = user.userID "
                    + "WHERE wishlist.requesterID = ? AND wishlist_equipment.status = 'pending'";
            pStmnt = cnnct.prepareStatement(sql);
            pStmnt.setInt(1, userid);

            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {

                cb = new WishListEquipmentBean();
                cb.setWishListwishID(rs.getInt("WishListwishlistID"));
                cb.setEquipmentequipmentID(rs.getInt("equipmentID"));
                //cb.setResponderID(rs.getInt("responderID"));
                cb.setStatus(rs.getString("status"));
                cb.setRequestDateTime(rs.getString("requestDateTime"));
                //cb.setRespondDateTime(rs.getString("respondDateTime"));
                cb.setRequesterName(rs.getString("responder"));
                cb.setEquipmentName(rs.getString("equipment"));
                cb.setLocation(rs.getString("location"));
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

    public ArrayList<WishListEquipmentBean> queryWishListApproved(HttpSession session) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        WishListEquipmentBean cb = null;
        ArrayList<WishListEquipmentBean> equipments = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            // Retrieve the userId from the session

            userid = (int) session.getAttribute("userId");

            String sql = "SELECT  wishlist_equipment.WishListwishlistID, wishlist_equipment.requestDateTime, wishlist_equipment.status, user.name AS responder, equipment.equipmentID, equipment.name AS equipment, equipment.location "
                    + "FROM equipment "
                    + "JOIN wishlist_equipment ON wishlist_equipment.EquipmentequipmentID = equipment.equipmentID "
                    + "JOIN wishlist ON wishlist_equipment.WishListwishlistID = wishlist.whislistID "
                    + "JOIN user ON wishlist.requesterID = user.userID "
                    + "WHERE wishlist.requesterID = ? AND wishlist_equipment.status = 'approved'";
            pStmnt = cnnct.prepareStatement(sql);
            pStmnt.setInt(1, userid);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();

            while (rs.next()) {

                cb = new WishListEquipmentBean();
                cb.setWishListwishID(rs.getInt("WishListwishlistID"));
                cb.setEquipmentequipmentID(rs.getInt("equipmentID"));
                //cb.setResponderID(rs.getInt("responderID"));
                cb.setStatus(rs.getString("status"));
                cb.setRequestDateTime(rs.getString("requestDateTime"));
                //cb.setRespondDateTime(rs.getString("respondDateTime"));
                cb.setResponderName(rs.getString("responder"));
                cb.setEquipmentName(rs.getString("equipment"));
                cb.setLocation(rs.getString("location"));
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
