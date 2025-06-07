package controllers;

import dto.Guest;
import dto.I_GuestManagement;
import dto.Room;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import utils.Utils;

public class GuestManagement implements I_GuestManagement{
    private List<Guest> guests;
    private RoomManagement roomManagement;
    
    public GuestManagement (RoomManagement roomManagement) {
        this.guests = new ArrayList<>();
        this.roomManagement = roomManagement;
    }

    @Override
    public boolean addGuest(Guest guest) {
        if (searchGuestByNationalId(guest.getNationalID()) != null) {
            System.out.println("Guest with this National ID already exists!");
            return false;
        }
        
        if (!roomManagement.isRoomAvailable(guest.getRoomID())) {
            System.out.println("Room is not available!");
            return false;
        }
        
        guests.add(guest);
        roomManagement.setRoomOccupied(guest.getRoomID(), true);
        return true;
    }

    @Override
    public boolean updateGuest(String nationalID, Guest updatedGuest) {
        Guest existingGuest = searchGuestByNationalId(nationalID);
        if (existingGuest == null) {
            return false;
        }
        
        if (!existingGuest.getRoomID().equals(updatedGuest.getRoomID())) {
            roomManagement.setRoomOccupied(existingGuest.getRoomID(), false);
            if (!roomManagement.isRoomAvailable(updatedGuest.getRoomID())) {
                roomManagement.setRoomOccupied(existingGuest.getRoomID(), true);
                return false;
            }
            roomManagement.setRoomOccupied(updatedGuest.getRoomID(), true);
        }
        
        existingGuest.setFullName(updatedGuest.getFullName());
        existingGuest.setBirthDate(updatedGuest.getBirthDate());
        existingGuest.setGender(updatedGuest.getGender());
        existingGuest.setPhoneNumber(updatedGuest.getPhoneNumber());
        existingGuest.setRoomID(updatedGuest.getRoomID());
        existingGuest.setRentalDays(updatedGuest.getRentalDays());
        existingGuest.setStartDate(updatedGuest.getStartDate());
        existingGuest.setCoTenantName(updatedGuest.getCoTenantName());
        
        return true;
    }

    @Override
    public Guest searchGuestByNationalId(String nationalID) {
        for (Guest guest : guests) {
            if (guest.getNationalID().equals(nationalID)) {
                return guest;
            }
        }
        return null;
    }

    @Override
    public boolean deleteGuestReservation(String nationalID) {
        Guest guest = searchGuestByNationalId(nationalID);
        if (guest == null) {
            return false;
        }
        
        if (!Utils.isFutureDate(guest.getStartDate())) {
            return false;
        }
        
        guests.remove(guest);
        roomManagement.setRoomOccupied(guest.getRoomID(), false);
        return true;
    }

    @Override
    public List<Guest> getAllGuests() {
        return new ArrayList<>(guests);
    }

    @Override
    public boolean saveGuestInfo(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(guests);
            System.out.println("Guest information has been successfully saved to \"" + filePath + "\".");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving guest information: " + e.getMessage());
            return false;
        }
    }

    @Override
    public double calculateMonthlyRevenue(int month, int year) {
        double totalRevennue = 0;
        Calendar cal = Calendar.getInstance();
        
        for (Guest guest : guests) {
            cal.setTime(guest.getStartDate());
            if (cal.get(Calendar.MONTH) + 1 == month && cal.get(Calendar.YEAR) == year) {
                Room room = roomManagement.getRoomById(guest.getRoomID());
                if (room != null) {
                    totalRevennue += room.getDailyRate() * guest.getRentalDays();
                }
            }
        }
        
        return totalRevennue;
    }

    @Override
    public double calculateRevenueByRoomType(String roomType) {
        double totalRevenue = 0;
        
        for (Guest guest : guests) {
            Room room = roomManagement.getRoomById(guest.getRoomID());
            if (room != null && room.getRoomType().equalsIgnoreCase(roomType)) {
                totalRevenue += room.getDailyRate() * guest.getRentalDays();
            }
        }
        
        return totalRevenue;
    }
    
    public void displayGuestInfo(Guest guest) {
        Room room = roomManagement.getRoomById(guest.getRoomID());
        
        System.out.println("----------------------------------------------------------------");
        System.out.println("Guest information [National ID: " + guest.getNationalID() + "]");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Full name : " + guest.getFullName());
        System.out.println("Phone number : " + guest.getPhoneNumber());
        System.out.println("Birth day : " + Utils.formatDate(guest.getBirthDate()));
        System.out.println("Gender : " + guest.getGender());
        System.out.println("----------------------------------------------------------------");
        System.out.println("Rental room : " + guest.getRoomID());
        System.out.println("Check in : " + Utils.formatDate(guest.getStartDate()));
        System.out.println("Rental days : " + guest.getRentalDays());
        System.out.println("Check out : " + Utils.formatDate(guest.getCheckOutDate()));
        System.out.println("----------------------------------------------------------------");
        
        if (room != null) {
            System.out.println("Room information:");
            System.out.println("+ ID : " + room.getRoomID());
            System.out.println("+ Room : " + room.getRoomName());
            System.out.println("+ Type : " + room.getRoomType());
            System.out.println("+ Daily rate: " + room.getDailyRate() + "$");
            System.out.println("+ Capacity : " + room.getCapacity());
            System.out.println("+ Furniture : " + room.getFurnitureDescription());
        }
    }
    
    public void displayMonthlyRevenue(int month, int year) {
        Calendar cal = Calendar.getInstance();
        List<Guest> monthlyGuests = guests.stream()
            .filter(guest -> {
                cal.setTime(guest.getStartDate());
                return cal.get(Calendar.MONTH) + 1 == month && cal.get(Calendar.YEAR) == year;
            })
            .collect(Collectors.toList());
        
        if (monthlyGuests.isEmpty()) {
            System.out.println("There is no data on guests who have rented rooms");
            return;
        }
        
        System.out.println("Monthly Revenue Report - '" + String.format("%02d/%d", month, year) + "'");
        System.out.println("----------------------------------------------------------------");
        System.out.println("RoomID | Room Name       | Room type | DailyRate | Amount");
        System.out.println("----------------------------------------------------------------");
        
        for (Guest guest : monthlyGuests) {
            Room room = roomManagement.getRoomById(guest.getRoomID());
            if (room != null) {
                double amount = room.getDailyRate() * guest.getRentalDays();
                System.out.printf("%-6s | %-15s | %-9s | %9.2f | %6.2f%n",
                    room.getRoomID(), room.getRoomName(), room.getRoomType(),
                    room.getDailyRate(), amount);
            }
        }
        System.out.println("----------------------------------------------------------------");
    }   
    
    public void displayRevenueByRoomType(String roomType) {
        Map<String, Double> revenueByType = new HashMap<>();
        
        for (Guest guest : guests) {
            Room room = roomManagement.getRoomById(guest.getRoomID());
            if (room != null) {
                double amount = room.getDailyRate() * guest.getRentalDays();
                revenueByType.merge(room.getRoomType(), amount, Double::sum);
            }
        }
        
        if (roomType != null && !roomType.isEmpty()) {
            Double revenue = revenueByType.get(roomType);
            if (revenue != null) {
                System.out.println("Revenue for " + roomType + " rooms: $" + String.format("%.2f", revenue));
            } else {
                System.out.println("No revenue data found for room type: " + roomType);
            }
        } else {
            System.out.println("Revenue Report by Room Type");
            System.out.println("----------------------------");
            System.out.println("Room type | Amount");
            System.out.println("--------------------------");
            
            for (Map.Entry<String, Double> entry : revenueByType.entrySet()) {
                System.out.printf("%-9s | $%,.2f%n", entry.getKey(), entry.getValue());
            }
        }
    }
}
