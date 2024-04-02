/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.test;

import ict.db.EquipmentDB;

/**
 *
 * @author Lau Ka Ming Benjamin
 */
public class TestPrintEquipTable {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/4511_asm";
        String username = "root";
        String password = "";

        EquipmentDB equipDb = new EquipmentDB(url, username, password);
        equipDb.createEquipTable();
        equipDb.queryEquip();
    }
}
