package dto;

import java.io.Serializable;
import java.util.Date;


public class Guest implements Serializable{
    private String nationalID;
    private String fullName;
    private Date birthDate;
    private String gender;
    private String phoneNumber;
    private String roomID;
    private String desiredRoomID;
    private int rentalDays;
    private Date startDate;
    private String coTenantName;
    
    public Guest () {
        
    }

    public Guest(String nationalID, String fullName, Date birthDate, String gender, String phoneNumber, String roomID, int rentalDays, Date startDate, String coTenantName) {
        this.nationalID = nationalID;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.roomID = roomID;
        this.rentalDays = rentalDays;
        this.startDate = startDate;
        this.coTenantName = coTenantName;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCoTenantName() {
        return coTenantName;
    }

    public void setCoTenantName(String coTenantName) {
        this.coTenantName = coTenantName;
    }

    public Date getCheckOutDate() {
        if (startDate != null) {
            long checkOutTime = startDate.getTime() + (rentalDays * 24L * 60 * 60 * 1000);
            return new Date(checkOutTime);
        }
        return null;
    }
    
    
}
