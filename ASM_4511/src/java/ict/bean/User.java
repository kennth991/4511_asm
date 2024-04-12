/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.io.Serializable;

/**
 *
 * @author kenneth
 */
public class User implements Serializable {

    private int userID;
    private String name;
    private String userName;
    private String location;
    private String role;
    private String password;

    public User() {

    }

    public User(int userID, String name, String userName, String location, String role, String password) {
        this.userID = userID;
        this.name = name;
        this.userName = userName;
        this.location = location;
        this.role = role;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getuserName() {
        return userName;
    }

    public String getLocation() {
        return location;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
}
