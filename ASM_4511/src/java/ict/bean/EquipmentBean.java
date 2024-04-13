package ict.bean;

import java.io.Serializable;

public class EquipmentBean implements Serializable {
    private int equipmentID;
    private String name;
    private String location;
    private String description;
    private String status;
    private String category;
    private String imgSrc;

    // Default constructor
    public EquipmentBean() {
    }

    // Constructor with all fields
    public EquipmentBean(int equipmentID, String name, String location, String description, String status, String category, String imgSrc) {
        this.equipmentID = equipmentID;
        this.name = name;
        this.location = location;
        this.description = description;
        this.status = status;
        this.category = category;
        this.imgSrc = imgSrc;
    }

    // Getters and Setters
    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "Equipment{" +
               "equipmentID=" + equipmentID +
               ", name='" + name + '\'' +
               ", location='" + location + '\'' +
               ", description='" + description + '\'' +
               ", status='" + status + '\'' +
               ", category='" + category + '\'' +
               ", imgSrc='" + imgSrc + '\'' +
               '}';
    }
}
