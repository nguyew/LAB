package business;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.SetMenu;


public class SetMenus implements Serializable {
    private final List<SetMenu> menuList = new ArrayList<>();
    private final String csvFile = "FeastMenu.csv";

    public SetMenus() {
        loadFromCSV();
    }
    
    public void loadFromCSV() {
        menuList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.toLowerCase().startsWith("code")) continue;
                
                String [] parts = line.split(",");
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    String ingredients = parts[3].trim();
                    menuList.add(new SetMenu(id, name, price, ingredients));
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot read data from FeastMenu.csv. Please check the file.");
        }
    }
    
    public void showAll () {
        if (menuList.isEmpty()) {
            System.out.println("No menu data found.");
            return;
        }
        
        menuList.sort(Comparator.comparingDouble(SetMenu::getPrice));
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-6s | %-30s | %-10s | %s\n", "ID", "Menu Name", "Price", "Ingredients");
        System.out.println("------------------------------------------------------------------------------------");
        for (SetMenu m : menuList) {
            System.out.printf("%-6s | %-30s | %,10.0f VND | %s\n",
                    m.getMenuId(), m.getMenuName(), m.getPrice(), m.getIngredients());
        }
        System.out.println("------------------------------------------------------------------------------------");
    }
    
    public boolean isValidMenuID (String id) {
        return menuList.stream().anyMatch(m -> m.getMenuId().equalsIgnoreCase(id));
    }
    
    public SetMenu searchById (String id) {
        for (SetMenu m : menuList) {
            if (m.getMenuId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;    
    }
    
    public List<SetMenu> getAll() {
        return new ArrayList<>(menuList);
    }
}
