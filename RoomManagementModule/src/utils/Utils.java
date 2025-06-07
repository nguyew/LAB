package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utils {
    private static Scanner scanner = new Scanner(System.in);
    
    public static String getString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
    
    public static int getInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please try again.");
            }
        }
    }
    
    public static boolean confirmYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " [Y/N]: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            } else if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        }
    }
    
    public static double getDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please try again.");
            }
        }
    }
    
    public static Date getDate(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        while (true) {
            try {
                System.out.print(message);
                return sdf.parse(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy format.");
            }
        }
    }
    
    public static boolean isValidNationalID(String id) {
        return Pattern.matches("\\d{12}", id);
    }
    
    public static boolean isValidPhoneNumber(String phone) {
        return Pattern.matches("0\\d{9}", phone);
    }
    
    public static boolean isValidRoomID(String roomID) {
        return Pattern.matches("[A-Za-z]\\d{1,4}", roomID);
    }
    
    public static boolean isValidName(String name) {
        return name.length() >= 2 && name.length() <= 25 && 
               Pattern.matches("[A-Za-z].*", name);
    }
    
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    
    public static boolean isFutureDate(Date date) {
        return date.after(new Date());
    }
}
