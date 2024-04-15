/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author Lau Ka Ming Benjamin
 */
import java.io.Serializable;

public class WishListEquipmentBean implements Serializable {

    private int wishListwishlistID;
    private int equipmentequipmentID;
    private int responderID;
    private String status;
    private String requestDateTime;
    private String respondDateTime;
    private String requesterName;
    private String responderName;
    private String equipmentName;
    private String location;

    // Constructor
    public WishListEquipmentBean() {
    }
    // Constructor

    public WishListEquipmentBean(int wishListwishlistID, int equipmentequipmentID, int responderID, String status, String requestDateTime, String respondDateTime) {
        this.wishListwishlistID = wishListwishlistID;
        this.equipmentequipmentID = equipmentequipmentID;
        this.responderID = responderID;
        this.status = status;
        this.requestDateTime = requestDateTime;
        this.respondDateTime = respondDateTime;
    }
    // Getters and setters

    public int getWishListwishID() {
        return wishListwishlistID;
    }

    public void setWishListwishID(int wishListwhislistID) {
        this.wishListwishlistID = wishListwhislistID;
    }

    public int getEquipmentequipmentID() {
        return equipmentequipmentID;
    }

    public void setEquipmentequipmentID(int equipmentequipmentID) {
        this.equipmentequipmentID = equipmentequipmentID;
    }

    public int getResponderID() {
        return responderID;
    }

    public void setResponderID(int responderID) {
        this.responderID = responderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getRespondDateTime() {
        return respondDateTime;
    }

    public void setRespondDateTime(String respondDateTime) {
        this.respondDateTime = respondDateTime;
    }

    public String getRequesterName() {
        return requesterName;
    }

    // Setter for requesterName
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getResponderName() {
        return responderName;
    }

    // Setter for requesterName
    public void setResponderName(String responderName) {
        this.responderName = responderName;
    }

    // Getter for equipmentName
    public String getEquipmentName() {
        return equipmentName;
    }

    // Setter for equipmentName
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    // Getter for location
    public String getLocation() {
        return location;
    }

    // Setter for location
    public void setLocation(String location) {
        this.location = location;
    }
}
