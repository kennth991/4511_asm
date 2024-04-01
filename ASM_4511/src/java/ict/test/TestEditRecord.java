/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;
import ict.db.CustomerDB;
import ict.bean.CustomerBean;

/**
 *
* @author Lau Ka Ming Benjamin-
 */
public class TestEditRecord {
    public static void main(String[] arg){
        String url = "jdbc:mysql://localhost:3306/itp4511_db";
        String username = "root";
        String password = "";
        CustomerDB custDb = new CustomerDB(url, username, password);
        CustomerBean cb = new CustomerBean();

        boolean editResult = custDb.editRecord(cb);
        if (editResult) {
            System.out.println("Record edit successfully!");
        } else {
            System.out.println("Failed to edit the record.");
        }
    }
}
