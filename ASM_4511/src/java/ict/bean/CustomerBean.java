/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
* @author Lau Ka Ming Benjamin-
 */
public class CustomerBean {
    private String custId;
    private String name;
    private String tel;
    private int age;

    public void setCustId(String custId){
        this.custId = custId;
    }
    public String getCustId(){
        return custId;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setTel(String tel){
        this.tel = tel;
    }
    public String getTel(){
        return tel;
    }

    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return age;
    }

    public CustomerBean(){

    }
}
