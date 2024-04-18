/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author SmartCity_TY
 */
public class DeliveryBean {
    private int deliveryID;
    private int courierID;
    private int allocatorID;
    private String status;
    private String pickupDateTime;
    private String placeDateTime;
    private String requestLocation;
    private String respondLocation;
    private String equipmentName;
    

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public int getCourierID() {
        return courierID;
    }

    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }

    public int getAllocatorID() {
        return allocatorID;
    }

    public void setAllocatorID(int allocatorID) {
        this.allocatorID = allocatorID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(String pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public String getPlaceDateTime() {
        return placeDateTime;
    }

    public void setPlaceDateTime(String placeDateTime) {
        this.placeDateTime = placeDateTime;
    }
    
    public String getRequestLocation() {
        return requestLocation;
    }

    public void setRequestLocation(String requestLocation) {
        this.requestLocation = requestLocation;
    }

    public String getRespondLocation() {
        return respondLocation;
    }

    public void setRespondLocation(String respondLocation) {
        this.respondLocation = respondLocation;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
