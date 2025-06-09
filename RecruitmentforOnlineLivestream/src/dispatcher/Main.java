package dispatcher;

import tools.Validator;
import business.*;
import java.util.List;
import java.util.Map;
import model.KOL;
import tools.Utils;

public class Main {
    private KOLManagement kolManagement;
    private Validator validator;
    private static final String DATA_FILE = "kol_registration.dat";
    
    public Main () {
        this.kolManagement = new KOLManagement();
        this.validator = new Validator();
        loadDataFromFile();
    }
    
    public void run () {
        Utils.printHeader("KOL Recruitment Management System");
        System.out.println("Welcome to Digital Influencers Drive Sales Campaign!");
        
        while (true) {
            displayMenu();
            String choice = Utils.readString("Enter your choice: ");
            
            switch (choice) {
                case "1": newRegistration(); break;
                case "2": updateRegistration(); break;
                case "3": displayRegisteredList(); break;
                case "4": deleteRegistration(); break;
                case "5": searchKOLsByName(); break;
                case "6": filterDataByCategory(); break;
                case "7": showPlatformStatistics(); break;
                case "8": saveDataToFile(); break;
                case "9": exitProgram(); return;
                default:
                    System.out.println("This function is not available.");
                    Utils.pressEnterToContinue();
            }
        }
    }

    private void displayMenu() {
        Utils.printHeader("Main Menu");
        System.out.println("1. New Registration");
        System.out.println("2. Update Registration Information");
        System.out.println("3. Display Registered List");
        System.out.println("4. Delete Registration Information");
        System.out.println("5. Search KOLs by Name");
        System.out.println("6. Filter Data by Category");
        System.out.println("7. Statistics of Registration Numbers by Platform");
        System.out.println("8. Save Data to File");
        System.out.println("9. Exit the Program");
        Utils.printLine(50);
    }

    private void newRegistration() {
        Utils.printHeader("New KOL Resgistration");
        
        try {
            String kolId = Validator.getValidKolId(kolManagement);
            String name = Validator.getValidName();
            String phoneNumber = Validator.getValidPhoneNumber();
            String email = Validator.getValidEmail();
            String platformCode = Validator.getValidPlatformCode();
            long followerCount = Validator.getValidFollowerCount();

            KOL kol = new KOL(kolId, name, phoneNumber, email, platformCode, followerCount);
            
            if (kolManagement.addKOL(kol)) {
                System.out.println("KOL registration successful!");
                System.out.println("Commission rate: " + kol.getCommissionRate() + "%");
            } else {
                System.out.println("Registration failed: KOL ID already exists.");
            }
        } catch (Exception e) {
            System.out.println("Registration cancelled.");
        }
        
        Utils.pressEnterToContinue();
    }

    private void displayRegisteredList() {
        Utils.printHeader("Registered KOLs List");
        
        List<KOL> kols = kolManagement.getAllKOLs();
        if (kols.isEmpty()) {
            System.out.println("No KOLs have registered yet.");
        } else {
            displayKolTable(kols);
        }
        
        Utils.pressEnterToContinue();
    }

    private void updateRegistration() {
        Utils.printHeader("Update KOL Registration");
        
        String kolId = Utils.readNonEmptyString("Enter KOL ID: ");
        KOL existingKol = kolManagement.findById(kolId);
        
        if (existingKol == null) {
            System.out.println("This KOL has not registered yet.");
            Utils.pressEnterToContinue();
            return;
        }
        
        System.out.println("\nCurrent KOL Information:");
        displayKolDetails(existingKol);
        
        System.out.println("\nEnter new information (press Enter to keep current value): ");
        
        String name = Validator.getValidInput("Name", "name", existingKol.getName(), validator);
        if (name == null) {
            System.out.println("Updated cancelled.");
            Utils.pressEnterToContinue();
            return;
        }
        
        String phoneNumber = Validator.getValidInput("Phone Number", "phone", existingKol.getPhoneNumber(), validator);
        if (phoneNumber == null) {
            System.out.println("Update cancelled.");
            Utils.pressEnterToContinue();
            return;
        }
        
        String email = Validator.getValidInput("Email", "email", existingKol.getEmail(), validator);
        if (email == null) {
            System.out.println("Update cancelled.");
            Utils.pressEnterToContinue();
            return;
        }
        
        String platformCode = Validator.getValidInput("Platform Code", "platform", existingKol.getPlatformCode(), validator);
        if (platformCode == null) {
            System.out.println("Update cancelled.");
            Utils.pressEnterToContinue();
            return;
        }
        
        String followerCountStr = Validator.getValidInput("Follower Count", "followers", String.valueOf(existingKol.getFollowerCount()), validator);
        if (followerCountStr == null) {
            System.out.println("Update cancelled.");
            Utils.pressEnterToContinue();
            return;
        }
        
        long followerCount = Long.parseLong(followerCountStr);
        
        // Create updated KOL
        KOL updatedKol = new KOL(kolId, name, phoneNumber, email, platformCode, followerCount);
        if (kolManagement.updateKOL(kolId, updatedKol)) {
            System.out.println("KOL information updated successfully!");
            if (followerCount != existingKol.getFollowerCount()) {
                System.out.println("Commission rate updated to: " + updatedKol.getCommissionRate());
            }
        } else {
            System.out.println("Updated failed.");
        }
        
        Utils.pressEnterToContinue();
    }

    private void deleteRegistration() {
        Utils.printHeader("Delete KOL Registration");
        
        String kolId = Utils.readNonEmptyString("Enter KOL ID: ");
        KOL kol = kolManagement.findById(kolId);
        
        if (kol == null) {
            System.out.println("This KOL has not registered yet.");
        } else {
            System.out.println("\nKOL Details:");
            displayKolDetails(kol);
            
            if (Utils.readYesNo("\nAre you sure you want to delete this registration?")) {
                if (kolManagement.deleteKOL(kolId)) {
                    System.out.println("The registration has been successfully deleted.");
                } else {
                    System.out.println("Deletion failed.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
        
        Utils.pressEnterToContinue();
    }

    private void searchKOLsByName() {
        Utils.printHeader("Search KOLs by Name");
        
        String searchName = Utils.readNonEmptyString("Enter name to search: ");
        List<KOL> matchingKOLs = kolManagement.searchByName(searchName);
        
        if (matchingKOLs.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            System.out.println("Matching KOLs:");
            displayKolTable(matchingKOLs);
        }
        
        Utils.pressEnterToContinue();
    }

    private void filterDataByCategory() {
        Utils.printHeader("Filter KOLs by Category");
        
        System.out.println("Available Categories:");
        for (Map.Entry<String, String> entry : Utils.getAllCategories().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        String categoryCode = Utils.readNonEmptyString("\nEnter category code: ").toUpperCase();
        List<KOL> filteredKOLs = kolManagement.filterByCategory(categoryCode);
        
        if (filteredKOLs.isEmpty()) {
            System.out.println("No KOLs have registered under this category.");
        } else {
            String categoryName = Utils.getCategoryName(categoryCode);
            System.out.println("\nRegistered KOLs Under " + categoryName + " Category (" + categoryCode + "):");
            displayKolTable(filteredKOLs);
        }
        
        Utils.pressEnterToContinue();
    }

    private void showPlatformStatistics() {
        Utils.printHeader("Platform Registration Statistics");
        
        KOLManagement management = (KOLManagement) kolManagement;
        Map<String, Integer> platformCounts = management.getPlatformCounts();
        Map<String, Double> platformStats = management.getPlatformStatistics();
        
        if (platformCounts.isEmpty()) {
            System.out.println("No registration data available for statistics.");
        } else {
            System.out.println("Statistics of Registration by Platform:");
            Utils.printLine(65);
            System.out.printf("%-20s | %-15s | %-20s%n", "Platform", "Number of KOLs", "Avg. Commission Rate");
            Utils.printLine(65);
            
            for (Map.Entry<String, Integer> entry : platformCounts.entrySet()) {
                String platformCode = entry.getKey();
                String platformName = Utils.getPlatformName(platformCode);
                int count = entry.getValue();
                double avgCommission = platformStats.get(platformCode);
                
                System.out.printf("%-20s | %-15d | %-18.1f%%%n", 
                    platformName + " (" + platformCode + ")", count, avgCommission);
            }
            Utils.printLine(65);
        }
        
        Utils.pressEnterToContinue();
    }

    private void saveDataToFile() {
        Utils.printHeader("Save Data to File");
        
        if (kolManagement.saveToFile(DATA_FILE)) {
            System.out.println("Registration data has been successfully saved to `" + DATA_FILE + "`.");
        } else {
            System.out.println("Failed to save data to file.");
        }
        
        Utils.pressEnterToContinue();
    }

    private void exitProgram() {
        Utils.printHeader("Exit Program");
        
        KOLManagement service = (KOLManagement) kolManagement;
        if (service.hasUnsavedChanges()) {
            if (Utils.readYesNo("Do you want to save the changes before exiting?")) {
                saveDataToFile();
            } else {
                if (!Utils.readYesNo("You have unsaved changes. Are you sure you want to exit without saving?")) {
                    return; 
                }
            }
        }
        
        System.out.println("Thank you for using KOL Recruitment Management System!");
        System.out.println("Goodbye!");
    }

    private void loadDataFromFile() {
        kolManagement.loadFromFile(DATA_FILE);
    }

    private void displayKolTable(List<KOL> kols) {
        Utils.printLine(90);
        System.out.printf("%-10s | %-20s | %-12s | %-15s | %-10s | %-10s%n",
            "KOL ID", "Name", "Phone", "Platform", "Followers", "Commission");
        Utils.printLine(90);
        
        for (KOL kol : kols) {
            System.out.println(kol.toString());
        }
        Utils.printLine(90);
    }
    
    private void displayKolDetails(KOL kol) {
        Utils.printLine(50);
        System.out.println("KOL ID     : " + kol.getKolId());
        System.out.println("Name       : " + kol.getName());
        System.out.println("Phone      : " + kol.getPhoneNumber());
        System.out.println("Email      : " + kol.getEmail());
        System.out.println("Platform   : " + Utils.getPlatformName(kol.getPlatformCode()));
        System.out.println("Followers  : " + String.format("%,d", kol.getFollowerCount()));
        System.out.println("Commission : " + kol.getCommissionRate() + "%");
        Utils.printLine(50);
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    
}
