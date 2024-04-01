/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;
import ict.db.CustomerDB;

/**
 *
* @author Lau Ka Ming Benjamin-
 */
public class TestDelete {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ITP4511_DB";
        String username = "root";
        String password = "";
        CustomerDB custDb = new CustomerDB(url, username, password);

        boolean deletionResult = custDb.delRecord("2");
        if (deletionResult) {
            System.out.println("Record deleted successfully!");
        } else {
            System.out.println("Failed to delete the record.");
        }
    }
}
