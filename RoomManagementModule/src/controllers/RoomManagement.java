package controllers;

import dto.I_RoomManagement;
import dto.Room;
import java.io.*;
import java.util.*;


public class RoomManagement implements I_RoomManagement {
    private List<Room> rooms;
    
    public RoomManagement () {
        this.rooms = new ArrayList<>();
    }
    
    @Override
    public boolean loadRoomsFromFile(String filePath) {
        int successCount = 0;
        int failCount = 0;
        Set<String> uniqueRoomIDs = new HashSet<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                
                if (parts.length != 6) {
                    failCount++;
                    continue;
                }
                
                try {
                    String roomID = parts[0].trim();
                    String roomName = parts[1].trim();
                    String roomType = parts[2].trim();
                    double dailyRate = Double.parseDouble(parts[3].trim());
                    int capacity = Integer.parseInt(parts[4].trim());
                    
                    if (!uniqueRoomIDs.add(roomID) || dailyRate <= 0 || capacity <= 0) {
                        failCount++;
                        continue;
                    }
                    
                    Room room = new Room(roomID, roomName, roomType, dailyRate, capacity, filePath, true);
                    rooms.add(room);
                    successCount++;
                } catch (NumberFormatException e) {
                    failCount++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error raading file: " + e.getMessage());
            return false;
        }
        
        System.out.println(successCount +   " rooms successfully loaded.");
        if (failCount > 0) {
            System.out.println(failCount + " entries failed.");
        }
        return true;
    }

    @Override
    public void displayAvailableRooms() {
        if (rooms.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet.");
            return;
        }
        
        System.out.println("\nActive Room List");
        System.out.println("RoomID | RoomName        | Type     | Rate   | Capacity | Furniture");
        System.out.println("-------+-----------------+----------+--------+----------+-----------------------");
        
        for (Room room : rooms) {
            System.out.println(room.toString());
        }
    }

    @Override
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isIsOccupied()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    @Override
    public Room getRoomById(String roomID) {
        for (Room room : rooms) {
            if (room.getRoomID().equals(roomID)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public boolean isRoomAvailable(String roomID) {
        Room room = getRoomById(roomID);
        return room != null && !room.isIsOccupied();
    }

    @Override
    public void setRoomOccupied(String roomID, boolean occupied) {
        Room room = getRoomById(roomID);
        if (room != null) {
            room.setIsOccupied(occupied);
        }
    }
    
    public void listVacantRooms () {
        List<Room> vacantRooms = getAvailableRooms();
        
        if (vacantRooms.isEmpty()) {
            System.out.println("All rooms are currently rented out - no avability at the moment");
            return;
        }
        
        System.out.println("\nAvailable Room List");
        System.out.println("RoomID | RoomName        | Type     | Rate   | Capacity | Furniture");
        System.out.println("-------+-----------------+----------+--------+----------+-----------------------");
        
        for (Room room : vacantRooms) {
            System.out.println(room.toString());
        }
    }
    
    
}
