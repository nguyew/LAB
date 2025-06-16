package dto;

import utils.Utils;

public class Room implements Workable {

    private String id;
    private String name;
    private String type;
    private double rate;
    private int capacity;
    private String furniture;

    public Room() {
    }

    public Room(String id, String name, String type, double rate, int capacity, String furniture) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rate = rate;
        this.capacity = capacity;
        this.furniture = furniture;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name=" + name + ", type=" + type + ", rate=" + rate + ", capacity=" + capacity + ", furniture=" + furniture + '}';
    }
    
    @Override
    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public boolean create() {
        boolean check = false;
        try {
            id = Utils.getString("Input Room ID: ");
            name = Utils.getString("Input Room Name: ");
            type = Utils.getString("Input Room Type: ");
            rate = Double.parseDouble(Utils.getString("Input Daily Rate: "));
            capacity = Utils.getInt("Input Capacity: ", 1, 10);
            furniture = Utils.getString("Input Furniture Description: ");
            check = true;
        } catch (Exception e) {
            System.out.println("Error creating room: " + e.getMessage());
        }
        return check;
    }

    @Override
    public boolean update() {
        boolean check = false;
        try {
            name = Utils.updateString("Update Room Name: ", name);
            type = Utils.updateString("Update Room Type: ", type);
            
            String rateInput = Utils.getString("Update Daily Rate (current: " + rate + "): ");
            if (!rateInput.isEmpty()) {
                rate = Double.parseDouble(rateInput);
            }
            
            capacity = Utils.updateInt("Update Capacity: ", 1, 10, capacity);
            furniture = Utils.updateString("Update Furniture Description: ", furniture);
            check = true;
        } catch (Exception e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
        return check;
    }
}