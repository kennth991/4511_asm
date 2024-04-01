/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;

import ict.bean.CustomerBean;
import ict.db.CustomerDB;
import java.util.ArrayList;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */
public class TestQueryCustByName {
    
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ITP4511_DB";
        String username = "root";
        String password = "";
        CustomerDB custDb = new CustomerDB(url, username, password);

        ArrayList<CustomerBean> allCustomers = custDb.queryCustByName("Peter");
        for (CustomerBean customer : allCustomers) {
            System.out.println("Customer ID: " + customer.getCustId());
            System.out.println("Name: " + customer.getName());
            System.out.println("Tel: " + customer.getTel());
            System.out.println("Age: " + customer.getAge() + "\n");
        }
    }
    
}
