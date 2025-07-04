package model;

import java.io.Serializable;


public class Customer implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String email;
    
    public Customer () {
        
    }

    public Customer(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String toString () {
         return String.format("%-6s | %-20s | %-10s | %-25s", id, name, phone, email);
    }
}
