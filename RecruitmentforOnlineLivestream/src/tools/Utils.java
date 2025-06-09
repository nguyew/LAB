package tools;

import java.util.*;
import java.util.regex.Pattern;

public class Utils {
    // --- PLATFORM UTILS ---
    private static final Map<String, String> PLATFORM_NAMES = new HashMap<>();
    static {
        PLATFORM_NAMES.put("TK01", "TikTok");
        PLATFORM_NAMES.put("FB01", "Facebook");
        PLATFORM_NAMES.put("IG01", "Instagram");
        PLATFORM_NAMES.put("YT01", "YouTube");
    }

    public static String getPlatformName(String code) {
        return PLATFORM_NAMES.getOrDefault(code, "Unknown");
    }

    public static Map<String, String> getAllPlatforms() {
        return new HashMap<>(PLATFORM_NAMES);
    }

    // --- CATEGORY UTILS ---
    private static final Map<String, String> CATEGORY_NAMES = new HashMap<>();
    static {
        CATEGORY_NAMES.put("BT", "Beauty");
        CATEGORY_NAMES.put("FS", "Fashion");
        CATEGORY_NAMES.put("BC", "Broadcasting");
        CATEGORY_NAMES.put("GM", "Gaming");
        CATEGORY_NAMES.put("TL", "Travel");
    }

    public static String getCategoryName(String code) {
        return CATEGORY_NAMES.getOrDefault(code, "Unknown");
    }

    public static Map<String, String> getAllCategories() {
        return new HashMap<>(CATEGORY_NAMES);
    }

    // --- CONSOLE UTILS ---
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static String readNonEmptyString(String prompt) {
        String input;
        do {
            input = readString(prompt);
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static boolean readYesNo(String prompt) {
        String input;
        do {
            input = readString(prompt + " (Y/N): ").toUpperCase();
            if (input.equals("Y") || input.equals("YES")) return true;
            if (input.equals("N") || input.equals("NO")) return false;
            System.out.println("Please enter Y or N.");
        } while (true);
    }

    public static void printLine(int length) {
        System.out.println(repeatChar('-', length));
    }

    public static void printHeader(String title) {
        System.out.println();
        System.out.println(repeatChar('=', 50));
        System.out.println(title.toUpperCase());
        System.out.println(repeatChar('=', 50));
    }

    public static void pressEnterToContinue() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) System.out.println();
    }

    // --- SUPPORT METHOD FOR JAVA 8 ---
    public static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
