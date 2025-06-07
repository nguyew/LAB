package view;

import controllers.*;
import dto.Guest;
import dto.Room;
import java.util.Date;
import utils.Utils;


public class Main {
    private RoomManagement roomManagement;
    private GuestManagement guestManagement;
    
    public Main() {
        this.roomManagement = new RoomManagement();
        this.guestManagement = new GuestManagement(roomManagement);
    }
    
    public void run () {
        System.out.println("=== ATZ Resort Complex - Room Management System ===");
        
        while (true) {
            displayMenu();
            int choice = Utils.getInt("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    importRoomData();
                    break;
                case 2:
                    displayAvailableRooms();
                    break;
                case 3:
                    enterGuestInformation();
                    break;
                case 4:
                    updateGuestStayInformation();
                    break;
                case 5:
                    searchGuestByNationalID();
                    break;
                case 6:
                    deleteGuestReservation();
                    break;
                case 7:
                    listVacantRooms();
                    break;
                case 8:
                    monthlyRevenueReport();
                    break;
                case 9:
                    revenueReportByRoomType();
                    break;
                case 10:
                    saveGuestInformation();
                    break;
                case 0:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            Utils.getString("");
        }
    }

    private void displayMenu() {
        System.out.println("\n=== ROOM MANAGEMENT MENU ===");
        System.out.println("1. Import Room Data from Text File");
        System.out.println("2. Display Available Room List");
        System.out.println("3. Enter Guest Information");
        System.out.println("4. Update Guest Stay Information");
        System.out.println("5. Search Guest by National ID");
        System.out.println("6. Delete Guest Reservation Before Arrival");
        System.out.println("7. List Vacant Rooms");
        System.out.println("8. Monthly Revenue Report");
        System.out.println("9. Revenue Report by Room Type");
        System.out.println("10.Save Guest Information");
        System.out.println("0. Quit");
        System.out.println("============================");
    }

    private void importRoomData() {
        String filePath = Utils.getString("Enter file path (or press Enter for 'Active_Room_list.txt')");
        if (filePath.isEmpty()) {
            filePath = "Active_Room_List.txt";
        }
        roomManagement.loadRoomsFromFile(filePath);
    }
    
    private void displayAvailableRooms() {
        roomManagement.displayAvailableRooms();
    }
    
    private void enterGuestInformation() {
        System.out.println("\n=== Enter Guest Information ===");
        String nationalID;
        do {            
            nationalID = Utils.getString("National ID (12 digits): ");
            if (!Utils.isValidNationalID(nationalID)) {
                System.out.println("Invalid National ID format. Must be 12 digits.");
            }
        } while (!Utils.isValidNationalID(nationalID));
        
        String fullName;
        do {
            fullName = Utils.getString("Full name: ");
            if (!Utils.isValidName(fullName)) {
                System.out.println("Invalid name. Must be 2-25 characters and start with a letter.");
            }
        } while (!Utils.isValidName(fullName));
        
                Date birthDate = Utils.getDate("Birth date (dd/MM/yyyy): ");
        
        String gender;
        do {
            gender = Utils.getString("Gender (Male/Female): ");
        } while (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"));
        
        String phoneNumber;
        do {
            phoneNumber = Utils.getString("Phone number (10 digits, starts with 0): ");
            if (!Utils.isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid phone number format.");
            }
        } while (!Utils.isValidPhoneNumber(phoneNumber));
        
        String roomId;
        do {
            roomId = Utils.getString("Desired room ID: ");
            if (!Utils.isValidRoomID(roomId)) {
                System.out.println("Invalid room ID format.");
            } else if (!roomManagement.isRoomAvailable(roomId)) {
                System.out.println("Room is not available.");
                roomId = "";
            }
        } while (roomId.isEmpty());
        
        int rentalDays = Utils.getInt("Number of rental days: ");
        
        Date startDate;
        do {
            startDate = Utils.getDate("Start date (dd/MM/yyyy): ");
            if (!Utils.isFutureDate(startDate)) {
                System.out.println("Start date must be in the future.");
                startDate = null;
            }
        } while (startDate == null);
        
        String coTenantName = Utils.getString("Co-tenant name (optional): ");
        
        Guest guest = new Guest(nationalID, fullName, birthDate, gender, phoneNumber, 
                                roomId, rentalDays, startDate, coTenantName);
        
        if (guestManagement.addGuest(guest)) {
            System.out.println("Guest registered successfully for room " + roomId);
            System.out.println("Rental from " + Utils.formatDate(startDate) + " for " + rentalDays + " days");
        } else {
            System.out.println("Failed to register guest.");
        }
    }
    
    private void updateGuestStayInformation() {
        System.out.println("\n=== Update Guest Stay Information ===");
        
        String nationalId = Utils.getString("Enter National ID: ");
        Guest existingGuest = guestManagement.searchGuestByNationalId(nationalId);
        
        if (existingGuest == null) {
            System.out.println("No guest found with the requested ID!");
            return;
        }
        
        guestManagement.displayGuestInfo(existingGuest);
        
        System.out.println("\nEnter new information (press Enter to keep current value):");
        
        String newName = Utils.getString("Full name [" + existingGuest.getFullName() + "]: ");
        if (!newName.isEmpty() && Utils.isValidName(newName)) {
            existingGuest.setFullName(newName);
        }
        
        String newPhone = Utils.getString("Phone number [" + existingGuest.getPhoneNumber() + "]: ");
        if (!newPhone.isEmpty() && Utils.isValidPhoneNumber(newPhone)) {
            existingGuest.setPhoneNumber(newPhone);
        }
        
        String newRentalDaysStr = Utils.getString("Rental days [" + existingGuest.getRentalDays() + "]: ");
        if (!newRentalDaysStr.isEmpty()) {
            try {
                int newRentalDays = Integer.parseInt(newRentalDaysStr);
                if (newRentalDays > 0) {
                    existingGuest.setRentalDays(newRentalDays);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format for rental days.");
            }
        }
        
        String newCoTenant = Utils.getString("Co-tenant name [" + (existingGuest.getCoTenantName() != null ? existingGuest.getCoTenantName() : "") + "]: ");
        if (!newCoTenant.isEmpty()) {
            existingGuest.setCoTenantName(newCoTenant);
        }
        
        System.out.println("Guest information updated for ID: " + nationalId);
    }
    
     private void searchGuestByNationalID() {
        System.out.println("\n=== Search Guest by National ID ===");
        
        String nationalId = Utils.getString("Enter National ID: ");
        Guest guest = guestManagement.searchGuestByNationalId(nationalId);
        
        if (guest != null) {
            guestManagement.displayGuestInfo(guest);
        } else {
            System.out.println("----------------------------------------------------------------");
            System.out.println("No guest found with the requested ID '" + nationalId + "'");
        }
    }
    
    private void deleteGuestReservation() {
        System.out.println("\n=== Delete Guest Reservation Before Arrival ===");
        
        String nationalId = Utils.getString("Enter National ID: ");
        Guest guest = guestManagement.searchGuestByNationalId(nationalId);
        
        if (guest == null) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Booking details for ID '" + nationalId + "' could not be found");
            return;
        }
        
        if (!Utils.isFutureDate(guest.getStartDate())) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("The room booking for this guest cannot be cancelled!");
            return;
        }
        
        guestManagement.displayGuestInfo(guest);
        
        String confirm = Utils.getString("Do you really want to cancel this guest's room booking? [Y/N]: ");
        if (confirm.equalsIgnoreCase("Y")) {
            if (guestManagement.deleteGuestReservation(nationalId)) {
                System.out.println("The booking associated with ID " + nationalId + " has been successfully canceled.");
            } else {
                System.out.println("Failed to cancel booking.");
            }
        } else {
            System.out.println("Booking cancellation aborted.");
        }
    }
    
    private void listVacantRooms() {
        System.out.println("\n=== List Vacant Rooms ===");
        roomManagement.listVacantRooms();
    }
    
    private void monthlyRevenueReport() {
        System.out.println("\n=== Monthly Revenue Report ===");
        
        int month = Utils.getInt("Enter month (1-12): ");
        int year = Utils.getInt("Enter year: ");
        
        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Please enter a value between 1 and 12.");
            return;
        }
        
        guestManagement.displayMonthlyRevenue(month, year);
    }
    
    private void revenueReportByRoomType() {
        System.out.println("\n=== Revenue Report by Room Type ===");
        
        String roomType = Utils.getString("Enter room type (or press Enter for all types): ");
        
        if (roomType.isEmpty()) {
            guestManagement.displayRevenueByRoomType(null);
        } else {
            // Validate room type exists
            boolean validType = false;
            for (Room room : roomManagement.getAvailableRooms()) {
                if (room.getRoomType().equalsIgnoreCase(roomType)) {
                    validType = true;
                    break;
                }
            }
            
            if (validType) {
                guestManagement.displayRevenueByRoomType(roomType);
            } else {
                System.out.println("Invalid room type!");
            }
        }
    }
    
    private void saveGuestInformation() {
        System.out.println("\n=== Save Guest Information ===");
        
        String filePath = Utils.getString("Enter file path (or press Enter for 'guestInfo.dat'): ");
        if (filePath.isEmpty()) {
            filePath = "guestInfo.dat";
        }
        
        guestManagement.saveGuestInfo(filePath);
    }
    
    public static void main(String[] args) {
        Main system = new Main();
        system.run();
    }
}
