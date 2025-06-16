package controllers;

import dto.Room;
import java.io.*;
import java.util.*;
import dto.Guest;
import dto.I_List;
import utils.Utils;

public class ReservationList implements I_List {
    private ArrayList<Room> roomList;
    private ArrayList<Guest> guestList;
    private Scanner scanner;

     public ReservationList() {
        roomList = new ArrayList<>();
        guestList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    @Override
    public boolean loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int successCount = 0;
            int failCount = 0;
            Set<String> roomIDs = new HashSet<>();
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5) { // At least 5 fields required
                    try {
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        String type = parts[2].trim();
                        double rate = Double.parseDouble(parts[3].trim());
                        int capacity = Integer.parseInt(parts[4].trim());
                        String furniture = parts.length > 5 ? parts[5].trim() : "Basic furniture";
                        
                        // Validate unique room ID and positive values
                        if (!roomIDs.contains(id) && rate > 0 && capacity > 0) {
                            roomList.add(new Room(id, name, type, rate, capacity, furniture));
                            roomIDs.add(id);
                            successCount++;
                        } else {
                            failCount++;
                        }
                    } catch (NumberFormatException e) {
                        failCount++;
                    }
                } else {
                    failCount++;
                }
            }
            
            System.out.println(successCount + " rooms successfully loaded.");
            if (failCount > 0) {
                System.out.println(failCount + " entries failed.");
            }
            return true;
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean enterGuest() {
        try {
            String nationalID, fullName, birthdate, gender, phoneNumber, roomID, startDate, nameOfCoTenant;
            int rentalDays;
            
            // Input and validate National ID
            do {
                nationalID = Utils.getString("Input National ID (12 digits): ");
                if (!Utils.isValidNationalID(nationalID)) {
                    System.out.println("Invalid National ID. Must be 12 digits.");
                    continue;
                }
                if (findGuestByNationalID(nationalID) != null) {
                    System.out.println("National ID already exists in system.");
                    continue;
                }
                break;
            } while (true);
            
            // Input and validate Full Name
            do {
                fullName = Utils.getString("Input full Name (2-25 chars, starts with letter): ");
                if (Utils.isValidFullName(fullName)) break;
                System.out.println("Invalid name format.");
            } while (true);
            
            // Input birthdate
            birthdate = Utils.getString("Input birthdate (dd/MM/yyyy): ");
            
            // Input and validate gender
            do {
                gender = Utils.getString("Input gender (Male/Female): ");
                if (Utils.isValidGender(gender)) break;
                System.out.println("Invalid gender. Use Male or Female.");
            } while (true);
            
            // Input and validate phone number
            do {
                phoneNumber = Utils.getString("Input phone number (10 digits): ");
                if (Utils.isValidPhoneNumber(phoneNumber)) break;
                System.out.println("Invalid phone number. Must be 10 digits.");
            } while (true);
            
            // Input and validate room ID
            do {
                roomID = Utils.getString("Input desired room ID: ");
                if (findRoomByID(roomID) != null) break;
                System.out.println("Room ID not found in system.");
            } while (true);
            
            // Input rental days
            rentalDays = Utils.getInt("Input number of rent days: ", Utils.MIN, Utils.MAX);
            
            // Input and validate start date
            do {
                startDate = Utils.getString("Input start date (dd/MM/yyyy): ");
                if (Utils.isValidFutureDate(startDate)) break;
                System.out.println("Start date must be a future date.");
            } while (true);
            
            nameOfCoTenant = Utils.getString("Input co-tenant name (optional): ");
            
            // Generate reservation ID
            String reservationID = "RES" + System.currentTimeMillis();
            
            // Create and add guest
            Guest newGuest = new Guest(reservationID, nationalID, fullName, birthdate, 
                                     gender, phoneNumber, roomID, rentalDays, startDate, nameOfCoTenant);
            guestList.add(newGuest);
            
            System.out.println("Guest registered successfully for room " + roomID);
            System.out.println("Rental from " + startDate + " for " + rentalDays + " days");
            return true;
            
        } catch (Exception e) {
            System.out.println("Error entering guest information: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateGuest(String nationalID) {
        Guest guest = (Guest) findGuestByNationalID(nationalID);
        if (guest == null) {
            System.out.println("No guest found with the requested ID!");
            return false;
        }
        
        System.out.println("Current guest information:");
        guest.display();
        
        boolean updated = guest.update();
        if (updated) {
            System.out.println("Guest information updated for ID: " + nationalID);
        }
        return updated;
    }

    @Override
    public List<Guest> searchGuest(String nationalID) {
        List<Guest> result = new ArrayList<>();
        Guest guest = (Guest) findGuestByNationalID(nationalID);
        
        if (guest != null) {
            result.add(guest);
            displayGuestDetails(guest);
        } else {
            System.out.println("No guest found with the requested ID '" + nationalID + "'");
        }
        
        return result;
    }

    @Override
    public Guest deleteGuest(String nationalID) {
        Guest guest = (Guest) findGuestByNationalID(nationalID);
        
        if (guest == null) {
            System.out.println("Booking details for ID '" + nationalID + "' could not be found");
            return null;
        }
        
        // Check if start date is in the future
        if (!Utils.isValidFutureDate(guest.getStartDate())) {
            System.out.println("The room booking for this guest cannot be cancelled!");
            return null;
        }
        
        displayGuestDetails(guest);
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you really want to cancel this guest's room booking? [Y/N]: ");
        String confirm = scanner.nextLine().trim().toUpperCase();
        
        if (confirm.equals("Y") || confirm.equals("YES")) {
            guestList.remove(guest);
            System.out.println("The booking associated with ID " + nationalID + " has been successfully canceled.");
            return guest;
        }
        
        return null;
    }

    @Override
    public void displayAvaiableRoom() {
        if (roomList.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet.");
            return;
        }
        
        System.out.println("\nActive Room List");
        System.out.println("RoomID | RoomName        | Type     | Rate  | Capacity | Furniture");
        System.out.println("-------+-----------------+----------+-------+----------+-----------------------");
        
        for (Room room : roomList) {
            System.out.printf("%-6s | %-15s | %-8s | %5.1f | %-8d | %s%n",
                room.getId(), room.getName(), room.getType(), 
                room.getRate(), room.getCapacity(), room.getFurniture());
        }
    }

    @Override
    public void listVacantRooms() {
        if (roomList.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet.");
            return;
        }
        
        // Get list of occupied rooms
        Set<String> occupiedRooms = new HashSet<>();
        for (Guest guest : guestList) {
            // Check if guest is currently staying (simplified check)
            occupiedRooms.add(guest.getRoomID());
        }
        
        // Filter vacant rooms
        List<Room> vacantRooms = new ArrayList<>();
        for (Room room : roomList) {
            if (!occupiedRooms.contains(room.getId())) {
                vacantRooms.add(room);
            }
        }
        
        if (vacantRooms.isEmpty()) {
            System.out.println("All rooms have currently been rented out; no rooms are available");
            return;
        }
        
        System.out.println("\nAvailable Room List");
        System.out.println("RoomID | RoomName        | Type     | Rate  | Capacity | Furniture");
        System.out.println("-------+-----------------+----------+-------+----------+-----------------------");
        
        for (Room room : vacantRooms) {
            System.out.printf("%-6s | %-15s | %-8s | %5.1f | %-8d | %s%n",
                room.getId(), room.getName(), room.getType(), 
                room.getRate(), room.getCapacity(), room.getFurniture());
        }
    }

    @Override
    public double monthlyRevenueReport() {
        String monthYear = Utils.getString("Enter target month (MM/yyyy): ");
        
        if (guestList.isEmpty()) {
            System.out.println("There is no data on guests who have rented rooms");
            return 0.0;
        }
        
        System.out.println("\nMonthly Revenue Report - '" + monthYear + "'");
        System.out.println("----------------------------------------------------------------");
        System.out.println("RoomID | Room Name       | Room type | DailyRate | Amount");
        System.out.println("----------------------------------------------------------------");
        
        double totalRevenue = 0.0;
        
        for (Guest guest : guestList) {
            Room room = (Room) findRoomByID(guest.getRoomID());
            if (room != null) {
                double amount = room.getRate() * guest.getRentalDays();
                totalRevenue += amount;
                
                System.out.printf("%-6s | %-15s | %-9s | %8.2f | %8.2f%n",
                    room.getId(), room.getName(), room.getType(), 
                    room.getRate(), amount);
            }
        }
        
        System.out.println("----------------------------------------------------------------");
        System.out.println("Total Revenue: $" + String.format("%.2f", totalRevenue));
        
        return totalRevenue;
    }

    @Override
    public double revenueReportByRoomType() {
        String roomType = Utils.getString("Enter room type: ");
        
        if (guestList.isEmpty()) {
            System.out.println("There is no data on guests who have rented rooms");
            return 0.0;
        }
        
        Map<String, Double> revenueByType = new HashMap<>();
        
        for (Guest guest : guestList) {
            Room room = (Room) findRoomByID(guest.getRoomID());
            if (room != null && room.getType().equalsIgnoreCase(roomType)) {
                double amount = room.getRate() * guest.getRentalDays();
                revenueByType.put(room.getType(), 
                    revenueByType.getOrDefault(room.getType(), 0.0) + amount);
            }
        }
        
        if (revenueByType.isEmpty()) {
            System.out.println("Invalid room type!");
            return 0.0;
        }
        
        System.out.println("\nRevenue Report by Room Type");
        System.out.println("----------------------------");
        System.out.println("Room type | Amount");
        System.out.println("--------------------------");
        
        double total = 0.0;
        for (Map.Entry<String, Double> entry : revenueByType.entrySet()) {
            System.out.printf("%-9s | $%,.2f%n", entry.getKey(), entry.getValue());
            total += entry.getValue();
        }
        
        return total;
    }

    @Override
    public boolean saveGuestInformation() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("guestInfo.dat"))) {
            oos.writeObject(guestList);
            System.out.println("Guest information has been successfully saved to \"guestInfo.dat\".");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving guest information: " + e.getMessage());
            return false;
        }
    }

    private Object findGuestByNationalID(String nationalID) {
        for (Guest guest : guestList) {
            if (guest.getNationalID().equals(nationalID)) {
                return guest;
            }
        }
        return null;
    }

    private Object findRoomByID(String roomID) {
         for (Room room : roomList) {
            if (room.getId().equals(roomID)) {
                return room;
            }
        }
        return null;
    }

    private void displayGuestDetails(Guest guest) {
        Room room = (Room) findRoomByID(guest.getRoomID());
        
        System.out.println("----------------------------------------------------------------");
        System.out.println("Guest information [National ID: " + guest.getNationalID() + "]");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Full name : " + guest.getFullName());
        System.out.println("Phone number : " + guest.getPhoneNumber());
        System.out.println("Birth day : " + guest.getBirthdate());
        System.out.println("Gender : " + guest.getGender());
        System.out.println("----------------------------------------------------------------");
        System.out.println("Rental room : " + guest.getRoomID());
        System.out.println("Check in : " + guest.getStartDate());
        System.out.println("Rental days : " + guest.getRentalDays());
        System.out.println("----------------------------------------------------------------");
        
        if (room != null) {
            System.out.println("Room information:");
            System.out.println("+ ID : " + room.getId());
            System.out.println("+ Room : " + room.getName());
            System.out.println("+ Type : " + room.getType());
            System.out.println("+ Daily rate: " + room.getRate() + "$");
            System.out.println("+ Capacity : " + room.getCapacity());
            System.out.println("+ Furniture : " + room.getFurniture());
        }
    }
} 
