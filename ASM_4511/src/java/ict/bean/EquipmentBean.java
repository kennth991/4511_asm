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
    private String equipmentId;
    private String name;
    private String des;
    private int qty;

    public void setEquipmentId(String equipmentId){
        this.equipmentId = equipmentId;
    }
    public String getEquipmentId(){
        return equipmentId;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setDescription(String des){
        this.des = des;
    }
    public String getDescription(){
        return des;
    }

    public void setQty(int qty){
        this.qty = qty;
    }
    public int getQty(){
        return qty;
    }

    public EquipmentBean(){

    };
}
