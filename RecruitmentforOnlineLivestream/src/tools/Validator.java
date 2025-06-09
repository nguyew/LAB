package tools;

import business.KOLManagement;
import java.util.*;
import java.util.regex.Pattern;
import tools.I_Validator;
import tools.Utils;


public class Validator implements I_Validator{
    private static Validator validator = new Validator();
    
    // --- VALIDATION CONSTANTS ---
    private static final List<String> VALID_CATEGORIES = Arrays.asList("BT", "FS", "BC", "GM", "TL");
    private static final List<String> VALID_PLATFORMS = Arrays.asList("TK01", "FB01", "IG01", "YT01");
    private static final List<String> VIETNAMESE_OPERATORS = Arrays.asList(
        "032", "033", "034", "035", "036", "037", "038", "039", // Viettel
        "070", "079", "077", "076", "078", // Mobifone
        "083", "084", "085", "081", "082", // Vinaphone
        "056", "058", // Vietnamobile
        "059" // Gmobile
    );

    @Override
    public boolean isValidKolId(String kolId) {
        if (kolId == null || kolId.length() != 8) return false;
        String categoryCode = kolId.substring(0, 2);
        String numericPart = kolId.substring(2);
        return VALID_CATEGORIES.contains(categoryCode) && Pattern.matches("\\d{6}", numericPart);
    }

    @Override
    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.trim().length() >= 5 && name.trim().length() <= 30;
    }

    @Override
    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !Pattern.matches("\\d{10}", phoneNumber)) return false;
        String prefix = phoneNumber.substring(0, 3);
        return VIETNAMESE_OPERATORS.contains(prefix);
    }

    @Override
    public boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    @Override
    public boolean isValidPlatformCode(String platformCode) {
        return VALID_PLATFORMS.contains(platformCode);
    }

    @Override
    public boolean isValidFollowerCount(String followerCount) {
        try {
            long count = Long.parseLong(followerCount);
            return count > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static String getValidKolId(KOLManagement kolManagement) {
        String kolId;
        do {
            kolId = Utils.readNonEmptyString("Enter KOL ID (e.g., BT123456): ").toUpperCase();
            if (!validator.isValidKolId(kolId)) {
                System.out.println("❌ Invalid KOL ID. Must start with BT/FS/BC/GM/TL followed by 6 digits.");
            } else if (kolManagement.findById(kolId) != null) {
                System.out.println("⚠️ KOL ID already exists. Please use a different ID.");
                kolId = ""; // Đặt lại để lặp tiếp
            }
        } while (!validator.isValidKolId(kolId) || kolManagement.findById(kolId) != null);
        return kolId;
    }

    public static String getValidName() {
        String name;
        do {
            name = Utils.readNonEmptyString("Enter Name (5-30 characters): ");
            if (!validator.isValidName(name)) {
                System.out.println("❌ Invalid name. Must be 5-30 characters long.");
            }
        } while (!validator.isValidName(name));
        return name;
    }

    public static String getValidPhoneNumber() {
        String phoneNumber;
        do {
            phoneNumber = Utils.readNonEmptyString("Enter Phone Number (10 digits): ");
            if (!validator.isValidPhoneNumber(phoneNumber)) {
                System.out.println("❌ Invalid phone number. Must be 10 digits and Vietnamese operator.");
            }
        } while (!validator.isValidPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    public static String getValidEmail() {
        String email;
        do {
            email = Utils.readNonEmptyString("Enter Email: ");
            if (!validator.isValidEmail(email)) {
                System.out.println("❌ Invalid email format.");
            }
        } while (!validator.isValidEmail(email));
        return email;
    }

    public static String getValidPlatformCode() {
        System.out.println("Available Platforms:");
        for (Map.Entry<String, String> entry : Utils.getAllPlatforms().entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }

        String platformCode;
        do {
            platformCode = Utils.readNonEmptyString("Enter Platform Code: ").toUpperCase();
            if (!validator.isValidPlatformCode(platformCode)) {
                System.out.println("❌ Invalid platform code. Choose from: TK01, FB01, IG01, YT01");
            }
        } while (!validator.isValidPlatformCode(platformCode));
        return platformCode;
    }

    public static long getValidFollowerCount() {
        String followerCountStr;
        do {
            followerCountStr = Utils.readNonEmptyString("Enter Follower Count: ");
            if (!validator.isValidFollowerCount(followerCountStr)) {
                System.out.println("❌ Invalid follower count. Must be a positive number.");
            }
        } while (!validator.isValidFollowerCount(followerCountStr));
        return Long.parseLong(followerCountStr);
    }

    public static String getValidInput(String prompt, String fieldName, String currentValue, Validator validator) {
        String input = Utils.readString(prompt + " [" + currentValue + "]: ");
        if (!input.isEmpty()) {
            switch (fieldName.toLowerCase()) {
                case "name":
                    if (!validator.isValidName(input)) {
                        System.out.println("❌ Invalid name. Must be 5-30 characters long.");
                        return null;
                    }
                    break;
                case "phone":
                    if (!validator.isValidPhoneNumber(input)) {
                        System.out.println("❌ Invalid phone number. Must be 10 digits and Vietnamese operator.");
                        return null;
                    }
                    break;
                case "email":
                    if (!validator.isValidEmail(input)) {
                        System.out.println("❌ Invalid email format.");
                        return null;
                    }
                    break;
                case "platform":
                    if (!validator.isValidPlatformCode(input.toUpperCase())) {
                        System.out.println("❌ Invalid platform code. Choose from: TK01, FB01, IG01, YT01");
                        return null;
                    }
                    return input.toUpperCase(); 
                case "followers":
                    if (!validator.isValidFollowerCount(input)) {
                        System.out.println("❌ Invalid follower count. Must be a positive number.");
                        return null;
                    }
                    break;
                default:
                    System.out.println("⚠️ Unknown field: " + fieldName);
                    return null;
            }
            return input;
        }
        return currentValue; 
    }
}
