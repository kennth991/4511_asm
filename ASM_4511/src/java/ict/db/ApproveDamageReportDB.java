package ict.db;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.IOException;
import java.sql.*;

import ict.bean.DamageReportBean;
import java.util.ArrayList;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */
public class ApproveDamageReportDB {

    private String url = "";
    private String username = "";
    private String password = "";

    public ApproveDamageReportDB(String url, String username, String password) {
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

   public ArrayList<DamageReportBean> queryDamageReportList() {
    Connection cnnct = null;
    PreparedStatement pStmnt = null;
    ArrayList<DamageReportBean> damageReports = new ArrayList<>();

    try {
        cnnct = getConnection(); // get Connection
        String preQueryStatement = "SELECT dr.reportID, dr.description, dr.proposerID, u.name AS proposerName, dr.status, dr.reportDateTime, eq.name AS equipmentName " +
                "FROM damagereport dr " +
                "JOIN user u ON dr.proposerID = u.userID " +
                "JOIN equipment eq ON dr.EquipmentequipmentID = eq.name";
        pStmnt = cnnct.prepareStatement(preQueryStatement);
        ResultSet rs = pStmnt.executeQuery();

        while (rs.next()) {
            DamageReportBean cb = new DamageReportBean();
            cb.setReportID(rs.getInt("reportID"));
            cb.setDescription(rs.getString("description"));
            cb.setProposerID(rs.getInt("proposerID"));
            cb.setProposerName(rs.getString("proposerName"));
            cb.setStatus(rs.getString("status"));
            cb.setReportDateTime(rs.getString("reportDateTime"));
            cb.setEquipmentName(rs.getString("equipmentName"));

            damageReports.add(cb);
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
    return damageReports;
}
   public boolean  approveReport(int reportID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean updateSuccessful = false;

        try {
            cnnct = getConnection(); 
            String preQueryStatement = "UPDATE damagereport SET status = 'approved' WHERE reportID = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement); 
            pStmnt.setInt(1, reportID);

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

  

}
