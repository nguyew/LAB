package view;

import controllers.Menu;
import controllers.ReservationList;
import utils.Utils;

/**
 * Main class for ATZ Resort Room Management System
 * @author hd
 */
public class Main {
    private static ReservationList management = new ReservationList();
    private static Menu menu = new Menu();
    
    public static void main(String[] args) {
        initializeMenu();
        runMainLoop();
    }
    
    private static void initializeMenu() {
        menu.addItem("Import Room Data from Text File");
        menu.addItem("Display Available Room List");
        menu.addItem("Enter Guest Information");
        menu.addItem("Update Guest Stay Information");
        menu.addItem("Search Guest by National ID");
        menu.addItem("Delete Guest Reservation Before Arrival");
        menu.addItem("List Vacant Rooms");
        menu.addItem("Monthly Revenue Report");
        menu.addItem("Revenue Report by Room Type");
        menu.addItem("Save Guest Information");
    }
    
    private static void runMainLoop() {
        int choice;
        
        do {
            menu.showMenu();
            choice = menu.getChoice();
            
            switch (choice) {
                case 1:
                    importRoomData();
                    break;
                case 2:
                    management.displayAvaiableRoom();
                    break;
                case 3:
                    management.enterGuest();
                    break;
                case 4:
                    updateGuestInformation();
                    break;
                case 5:
                    searchGuestByNationalID();
                    break;
                case 6:
                    deleteGuestReservation();
                    break;
                case 7:
                    management.listVacantRooms();
                    break;
                case 8:
                    management.monthlyRevenueReport();
                    break;
                case 9:
                    management.revenueReportByRoomType();
                    break;
                case 10:
                    management.saveGuestInformation();
                    break;
                case 0:
                    System.out.println("Thank you for using ATZ Resort Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            
            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                try {
                    System.in.read();
                } catch (Exception e) {
                    // Ignore
                }
            }
            
        } while (choice != 0);
    }
    
    private static void importRoomData() {
        String filePath = Utils.getString("Enter file path (or press Enter for default 'Active_Room_List.txt'): ");
        if (filePath.isEmpty()) {
            filePath = "Active_Room_List.txt";
        }
        management.loadFromFile(filePath);
    }
    
    private static void updateGuestInformation() {
        String nationalID = Utils.getString("Enter National ID to update: ");
        if (Utils.isValidNationalID(nationalID)) {
            management.updateGuest(nationalID);
        } else {
            System.out.println("Invalid National ID format. Must be 12 digits.");
        }
    }
    
    private static void searchGuestByNationalID() {
        String nationalID = Utils.getString("Enter National ID to search: ");
        if (Utils.isValidNationalID(nationalID)) {
            management.searchGuest(nationalID);
        } else {
            System.out.println("Invalid National ID format. Must be 12 digits.");
        }
    }
    
    private static void deleteGuestReservation() {
        String nationalID = Utils.getString("Enter National ID to cancel reservation: ");
        if (Utils.isValidNationalID(nationalID)) {
            management.deleteGuest(nationalID);
        } else {
            System.out.println("Invalid National ID format. Must be 12 digits.");
        }
    }
}