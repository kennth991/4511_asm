/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

/**
 *
 * @author kenneth
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ict.bean.User;

public class UserDB {

    public UserDB(String url, String username, String password) {
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/4511_asm";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    public User authenticate(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Query the database for the user
            String sql = "SELECT * FROM user WHERE userName=? AND password=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            // If user exists, create User object
            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setUserName(rs.getString("userName"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setLocation(rs.getString("location"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close database resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public boolean createUser(User user) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "INSERT INTO user (name, userName, location, role, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getuserName());
            pstmt.setString(3, user.getLocation());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getPassword());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
