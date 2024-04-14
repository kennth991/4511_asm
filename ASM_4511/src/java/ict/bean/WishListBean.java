
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author LauKaMingBenjamin
 */
import java.io.Serializable;
import java.util.ArrayList;

public class WishListBean implements Serializable {

    private int wishlistID;
    private int requesterID;
    private ArrayList<WishListEquipmentBean> wishListEquipmens;

    // Constructor
    public WishListBean() {
    }

    public WishListBean(int wishlistID, int requesterID, ArrayList<WishListEquipmentBean> wishListEquipmens) {
        this.wishlistID = wishlistID;
        this.requesterID = requesterID;
        this.wishListEquipmens = wishListEquipmens;
    }

    // Getters and Setters
    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public int getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(int requesterID) {
        this.requesterID = requesterID;
    }

    public ArrayList<WishListEquipmentBean> getWishListEquipmens() {
        return wishListEquipmens;
    }
    public void setWishListEquipmens(ArrayList<WishListEquipmentBean> wishListEquipmens)
    {
        this.wishListEquipmens = wishListEquipmens;
    }
}
