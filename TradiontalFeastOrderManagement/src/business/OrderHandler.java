package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Customer;
import model.Order;
import model.SetMenu;
import tools.Acceptable;
import tools.Utils;


public class OrderHandler {
    public void placeOrder (Orders orders, Customers customers, SetMenus menus, Utils utils) {
        String customerId;
        while (true) {
            customerId = utils.inputAndLoop("Enter customer ID: ", Acceptable.CUS_ID_VALID);
            if (customers.searchById(customerId) != null) break;
            System.out.println("Customer ID not found. Please register first.");
            if (!Utils.confirmYesNo("Try again? [Y/N]: ")) return;
        }
        
        String menuId;
        while (true) {
            menuId = utils.inputAndLoop("Enter Set Menu ID: ", Acceptable.MENU_ID_VALID);
            if (menus.isValidMenuID(menuId)) break;
            System.out.println("Invalid Set Menu ID.");
            if (!Utils.confirmYesNo("Try again? [Y/N]: ")) return;
        }
        
        int numOfTables = utils.getInt("Enter number of tables: ");
        if (numOfTables <= 0) {
            System.out.println("Number of tables must be greater than 0.");
            return;
        }
        
        Date eventDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        while (true) {
            String dateStr = Utils.getString("Enter event date (dd/MM/yyyy):");
            try {
                eventDate = sdf.parse(dateStr);
                if (eventDate.after(new Date())) break;
                else System.out.println("Event date must be in the future.");
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
            }
        }
        
        Order newOrder = new Order(customerId, menuId, numOfTables, eventDate);
        if (orders.isDuplicate(newOrder)) {
            System.out.println("Duplicate order: same customer, menu and date.");
            return;
        }
        orders.addNew(newOrder);
        
        SetMenu menu = menus.searchById(menuId);
        Customer customer = customers.searchById(customerId);
        double total = menu.getPrice() * numOfTables;
        
        System.out.println("\n------ ORDER SUMMARY ------");
        System.out.println("Order ID      : " + newOrder.getOrderCode());
        System.out.println("Customer Name : " + customer.getName());
        System.out.println("Phone         : " + customer.getPhone());
        System.out.println("Email         : " + customer.getEmail());
        System.out.println("Set Menu Name : " + menu.getMenuName());
        System.out.println("Event Date    : " + sdf.format(eventDate));
        System.out.println("No. of Tables : " + numOfTables);
        System.out.printf("Price / Table : %,d VND\n", (int) menu.getPrice());
        System.out.printf("Total Cost    : %,d VND\n", (int) total);
        System.out.println("---------------------------");
        
        orders.saveToFile();
    }
}
