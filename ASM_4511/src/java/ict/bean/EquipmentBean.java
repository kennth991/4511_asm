/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author Lau Ka Ming Benjamin-
 */
public class EquipmentBean {

    private int equipmentId;
    private String name;
    private String des;
    private int qty;

    public EquipmentBean(int equipmentId, String name, String des, int qty) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.des = des;
        this.qty = qty;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String des) {
        this.des = des;
    }

    public String getDescription() {
        return des;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public EquipmentBean() {

    }
;
}
