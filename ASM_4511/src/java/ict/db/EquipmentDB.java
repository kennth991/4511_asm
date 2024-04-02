/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.io.IOException;
import java.sql.*;

import java.util.ArrayList;
import ict.bean.User;
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
        return DriverManager.getConnection(url,username,password);
    }
    
    public void createEquipTable() {
        Statement stmnt = null;
        Connection cnnct = null;
        
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Equipment ("
                    + "equipmentID varchar(8) NOT NULL,"
                    + "name varchar(50) NOT NULL,"
                    + "description varchar(50) NOT NULL,"
                    + "qty int(4) NOT NULL,"
                    + "VenueID varchar(8) NOT NULL,"
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

     
    public void insertRecord(){
    
    
    }
    public boolean addRecord(String CustId, String Name, String Tel, int Age) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO customer VALUES (?,?,?,?)";
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
    
    public boolean delRecord(String CustId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean deletionSuccessful = false;

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "DELETE FROM CUSTOMER WHERE custId = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement); // get the prepare Statement
            pStmnt.setString(1, CustId); // update the placehoder with id
            
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >= 1){
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
    
    public boolean editRecord(CustomerBean cb) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean updateSuccessful = false;

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "UPDATE customer SET name = ?, tel = ?, age = ? WHERE custId = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, "Peter");
            pStmnt.setString(2, "12345688");
            pStmnt.setInt(3, 18);
            pStmnt.setString(4, "1");

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

    public void dropCustTable() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "DROP TABLE customer";
            pStmnt = cnnct.prepareStatement(preQueryStatement); // get the prepare Statement
            
            pStmnt.executeUpdate();
            
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
    }
    
    public ArrayList<CustomerBean> queryCust() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        CustomerBean cb = null;
        ArrayList<CustomerBean> customers = new ArrayList<>();

        try {
            cnnct = getConnection(); // get Connection
            String preQueryStatement = "SELECT * FROM customer";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();
            
            while (rs.next()) {
                cb = new CustomerBean();
                cb.setCustId(rs.getString("custId"));
                cb.setName(rs.getString("name"));
                cb.setTel(rs.getString("tel"));
                cb.setAge(rs.getInt("age"));
                customers.add(cb);
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
        return customers;
    }
    
    public ArrayList<CustomerBean> queryCustByID(String ID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        CustomerBean cb = null;
        ArrayList<CustomerBean> customers = new ArrayList<>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM customer WHERE LOWER(custId) LIKE ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "%" + ID.toLowerCase() + "%");
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();
            
            while (rs.next()) {
                cb = new CustomerBean();
                cb.setCustId(rs.getString("custId"));
                cb.setName(rs.getString("name"));
                cb.setTel(rs.getString("tel"));
                cb.setAge(rs.getInt("age"));
                customers.add(cb);
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
        return customers;
    }
    
    public ArrayList<CustomerBean> queryCustByTel(String tel) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        CustomerBean cb = null;
        ArrayList<CustomerBean> customers = new ArrayList<>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM customer WHERE LOWER(tel) LIKE ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "%" + tel.toLowerCase() + "%");
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();
            
            while (rs.next()) {
                cb = new CustomerBean();
                cb.setCustId(rs.getString("custId"));
                cb.setName(rs.getString("name"));
                cb.setTel(rs.getString("tel"));
                cb.setAge(rs.getInt("age"));
                customers.add(cb);
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
        return customers;
    }
    
    public ArrayList<CustomerBean> queryCustByName(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        CustomerBean cb = null;
        ArrayList<CustomerBean> customers = new ArrayList<>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM customer WHERE LOWER(name) LIKE ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "%" + name.toLowerCase() + "%");
            ResultSet rs = null; // exeute the query and assign to the result
            rs = pStmnt.executeQuery();
            
            while (rs.next()) {
                cb = new CustomerBean();
                cb.setCustId(rs.getString("custId"));
                cb.setName(rs.getString("name"));
                cb.setTel(rs.getString("tel"));
                cb.setAge(rs.getInt("age"));
                customers.add(cb);
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
        return customers;
    }
}
