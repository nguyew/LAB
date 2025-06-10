package tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Utils {
    private static final Scanner sc = new Scanner(System.in);
    
    public static String inputString (String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }
    
    public static int inputInt (String msg, int min, int max) {
        int value;
        while (true) {
            try {
                System.out.print(msg);
                value = Integer.parseInt(sc.nextLine());
                if (value < min || value > max) 
                    throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again!");
            }
        }
    }
    
    public static double inputDouble (String msg, double min, double max) {
        double value;
        while (true) {
            try {
                System.out.print(msg);
                value = Double.parseDouble(sc.nextLine());
                if (value < min || value > max)
                    throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again!");
            }
        }
    }
    
    public static boolean confirm (String msg) {
        System.out.print(msg + " (Y/N): ");
        return sc.nextLine().trim().equalsIgnoreCase("Y");
    }
    
    public static void saveToFile(String filename, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (String line : lines) pw.println(line);
            System.out.println("Saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + filename);
        }
    }

    public static List<String> loadFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return lines;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null)
                if (!line.trim().isEmpty())
                    lines.add(line);
        } catch (IOException e) {
            System.out.println("Error reading from file: " + filename);
        }
        return lines;
    }
}
