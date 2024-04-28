/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author SmartCity_TY
 */
public class ReturnEquipmentBean {
    private int requestId;
    private String requestDateTime;
    private String status;
    private String requesterName;
    private int equipmentId;
    private String equipmentName;
    private String location;

    public ReturnEquipmentBean() {
        // Default constructor
    }

    public ReturnEquipmentBean(int requestId, String requestDateTime, String status, String requesterName,
                            int equipmentId, String equipmentName, String location) {
        this.requestId = requestId;
        this.requestDateTime = requestDateTime;
        this.status = status;
        this.requesterName = requesterName;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.location = location;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "EquipmentRequest{" +
                "requestId=" + requestId +
                ", requestDateTime=" + requestDateTime +
                ", status='" + status + '\'' +
                ", requesterName='" + requesterName + '\'' +
                ", equipmentId=" + equipmentId +
                ", equipmentName='" + equipmentName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}