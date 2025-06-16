package controllers;

import java.util.Scanner;
import java.util.ArrayList;
import dto.I_Menu;

public class Menu implements I_Menu {
    private ArrayList<String> menuItems;
    private Scanner scanner;
    
    public Menu() {
        menuItems = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    @Override
    public void addItem(String s) {
        menuItems.add(s);
    }
    
    @Override
    public int getChoice() {
        System.out.print("Enter your choice: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== ATZ Resort Room Management System ===");
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println((i + 1) + ". " + menuItems.get(i));
        }
        System.out.println("0. Quit");
        System.out.println("==========================================");
    }
    
    @Override
    public boolean confirmYesNo(String welcome) {
        System.out.print(welcome + " [Y/N]: ");
        String input = scanner.nextLine().trim().toUpperCase();
        return input.equals("Y") || input.equals("YES");
    }
}