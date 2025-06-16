/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import utils.Utils;

/**
 *
 * @author hd
 */
public class Guest implements Workable {
    private String reservationID;
    private String nationalID;
    private String fullName;
    private String birthdate;
    private String gender;
    private String phoneNumber;
    private String roomID;
    private int rentalDays;
    private String startDate;
    private String nameOfCoTenant;

    @Override
    public boolean equals(Object obj) {
        Guest guest= (Guest)obj;
        return this.nationalID.equals(guest.getNationalID()) 
                && this.roomID.equals(guest.getRoomID()) 
                 && this.startDate.equals(guest.getStartDate()); 

    }

    public Guest() {
    }

    public Guest(String nationalID, String roomID, String startDate) {
        this.nationalID = nationalID;
        this.roomID = roomID;
        this.startDate = startDate;
    }

    public Guest(String reservationID, String nationalID, String fullName, String birthdate, String gender, String phoneNumber, String roomID, int rentalDays, String startDate, String nameOfCoTenant) {
        this.reservationID = reservationID;
        this.nationalID = nationalID;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.roomID = roomID;
        this.rentalDays = rentalDays;
        this.startDate = startDate;
        this.nameOfCoTenant = nameOfCoTenant;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNameOfCoTenant() {
        return nameOfCoTenant;
    }

    public void setNameOfCoTenant(String nameOfCoTenant) {
        this.nameOfCoTenant = nameOfCoTenant;
    }

    @Override
    public String toString() {
        return "Guest{" + "nationalID=" + nationalID + ", fullName=" + fullName + ", birthdate=" + birthdate + ", gender=" + gender + ", phoneNumber=" + phoneNumber + ", roomID=" + roomID + ", rentalDays=" + rentalDays + ", startDate=" + startDate + ", nameOfCoTenant=" + nameOfCoTenant + '}';
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public boolean create() {
        boolean check = false;
        try {
            fullName = Utils.getString("Input full Name: ");
            birthdate = Utils.getString("Input birthdate: ");
            gender = Utils.getString("Input gender: ");
            phoneNumber = Utils.getString("Input phone number: ");
            rentalDays = Utils.getInt("Input number of rent days: ", Utils.MIN, Utils.MAX);
            nameOfCoTenant = Utils.getString("Input ten partner: ");
            check = true;
        } catch (Exception e) {
        }
        return check;
    }

    @Override
    public boolean update() {
        boolean check = false;
        try {
            fullName = Utils.updateString("Update full Name: ",fullName);
            birthdate = Utils.updateString("Update birthdate: ", birthdate);
            gender = Utils.updateString("Update gender: ", gender);
            phoneNumber = Utils.updateString("Update phone number: ", phoneNumber);
            rentalDays = Utils.updateInt("Update number of rent days: ", Utils.MIN, Utils.MAX, rentalDays);
            nameOfCoTenant = Utils.updateString("Update ten partner: ", nameOfCoTenant);
            check = true;
        } catch (Exception e) {
        }
        return check;
    }

}
