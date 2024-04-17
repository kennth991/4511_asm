/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author kenneth
 */


public class VenueBooking {
    private int bookingID;
    private int requesterID;
    private Integer responderID;
    private int venueVenueID;
    private Timestamp requestDatetime;
    private Date bookingDate;
    private String checkinTime;
    private String checkoutTime;
    private String status;

    public VenueBooking() {
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(int requesterID) {
        this.requesterID = requesterID;
    }

    public Integer getResponderID() {
        return responderID;
    }

    public void setResponderID(Integer responderID) {
        this.responderID = responderID;
    }

    public int getVenueVenueID() {
        return venueVenueID;
    }

    public void setVenueVenueID(int venueVenueID) {
        this.venueVenueID = venueVenueID;
    }

    public Timestamp getRequestDatetime() {
        return requestDatetime;
    }

    public void setRequestDatetime(Timestamp requestDatetime) {
        this.requestDatetime = requestDatetime;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
