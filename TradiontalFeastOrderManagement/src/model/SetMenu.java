package model;

import java.io.Serializable;


public class SetMenu implements Serializable{
    private String menuId;
    private String menuName;
    private double price;
    private String ingredients;
    
    public SetMenu () {
        
    }

    public SetMenu(String menuId, String menuName, double price, String ingredients) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    
    public String toString () {
         return String.format("%-6s | %-30s | %,10.0f VND | %s", menuId, menuName, price, ingredients);
    }
}
