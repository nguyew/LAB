package utils;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {
    public static final int MIN = 1;
    public static final int MAX = 365;
    private static Scanner scanner = new Scanner(System.in);
    
    // Get string input with validation
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    // Get integer input with range validation
    public static int getInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Value must be between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please try again.");
            }
        }
    }
    
    // Update string field (keep old value if empty input)
    public static String updateString(String prompt, String oldValue) {
        System.out.print(prompt + " (current: " + oldValue + "): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? oldValue : input;
    }
    
    // Update integer field (keep old value if empty input)
    public static int updateInt(String prompt, int min, int max, int oldValue) {
        System.out.print(prompt + " (current: " + oldValue + "): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return oldValue;
        }
        try {
            int value = Integer.parseInt(input);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Value must be between " + min + " and " + max);
            return oldValue;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Keeping old value.");
            return oldValue;
        }
    }
    
    // Validate National ID (12 digits)
    public static boolean isValidNationalID(String nationalID) {
        return nationalID != null && nationalID.matches("\\d{12}");
    }
    
    // Validate full name (2-25 chars, starts with letter)
    public static boolean isValidFullName(String name) {
        return name != null && name.length() >= 2 && name.length() <= 25 
               && Character.isLetter(name.charAt(0));
    }
    
    // Validate phone number (10 digits)
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }
    
    // Validate room ID (up to 5 chars, starts with letter)
    public static boolean isValidRoomID(String roomID) {
        return roomID != null && roomID.length() <= 5 && roomID.length() > 0
               && Character.isLetter(roomID.charAt(0));
    }
    
    // Validate date format and future date
    public static boolean isValidFutureDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date inputDate = sdf.parse(dateStr);
            Date today = new Date();
            return inputDate.after(today);
        } catch (Exception e) {
            return false;
        }
    }
    
    // Validate gender
    public static boolean isValidGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male") || 
                                 gender.equalsIgnoreCase("Female") ||
                                 gender.equalsIgnoreCase("M") || 
                                 gender.equalsIgnoreCase("F"));
    }
}