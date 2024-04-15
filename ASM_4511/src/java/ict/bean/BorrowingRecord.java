/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
/**
 *
 * @author kenneth
 */


public class BorrowingRecord implements Serializable {

    private int recordId; // Represents the requestId in the context of new requirements
    private int userId;
    private String equipmentName; // We'll use equipmentName instead of equipmentId
    private Timestamp requestDateTime; // Date and time when the request was made
    private Date startDate; // The start date of the equipment borrowing
    private Date returnDate; // The expected return date of the equipment borrowing
    private String status; // The status of the borrowing

    // Constructor
    public BorrowingRecord() {
    }

    // Getters and Setters
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Timestamp getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(Timestamp requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
