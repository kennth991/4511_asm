/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

import java.io.Serializable;
/**
 *
 * @author kenneth
 */



public class Venue implements Serializable {
    private int venueID;
    private String name;
    private String description;
    private String status;
    private String location;
    private String imgSrc;

    // No-arg constructor
    public Venue() {
    }

    // Parameterized constructor
    public Venue(int venueID, String name, String description, String status, String location, String imgSrc) {
        this.venueID = venueID;
        this.name = name;
        this.description = description;
        this.status = status;
        this.location = location;
        this.imgSrc = imgSrc;
    }

    // Getters and setters
    public int getVenueID() {
        return venueID;
    }

    public void setVenueID(int venueID) {
        this.venueID = venueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
