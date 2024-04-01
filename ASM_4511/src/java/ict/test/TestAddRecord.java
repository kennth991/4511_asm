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
public class TestAddRecord {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ITP4511_DB";
        String username = "root";
        String password = "";
        CustomerDB custDb = new CustomerDB(url, username, password);

        String[][] customers = {
            {"1", "Peter", "12345688", "18"},
            {"2", "Nancy", "12345678", "21"},
            {"3", "Peter 2", "12345678", "21"},
            {"4", "Peter 3", "12345678", "15"},
            {"5", "Peter 4", "12345688", "12"},
            {"6", "Peter 5", "12345688", "13"}
            
        };

        for (String[] customer : customers) {
            boolean addResult = custDb.addRecord(customer[0], customer[1], customer[2], Integer.parseInt(customer[3]));
            if (addResult) {
                System.out.println(customer[1] + " is added");
            } else {
                System.out.println("Failed to add the record.");
            }
        }
    }
}