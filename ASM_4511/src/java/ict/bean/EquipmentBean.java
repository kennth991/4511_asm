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
    private String location;
    private String des;
    private String status;

    public EquipmentBean(int equipmentId, String name, String location, String des, String status) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.location = location;
        this.des = des;
        this.status = status;
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

    public void setLocation(String location) {
        this.location = location;

    }

    public String getLocation() {
        return location;
    }

    public void setDescription(String des) {
        this.des = des;
    }

    public String getDescription() {
        return des;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public EquipmentBean() {

    }
;
}
