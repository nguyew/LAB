package dispatcher;

import business.Customers;
import business.OrderHandler;
import business.Orders;
import business.SetMenus;
import java.util.List;
import model.Customer;
import model.Order;
import tools.Acceptable;
import tools.Utils;
//thiết kế menu 9: thực hiện của thằng order đầu tiên có trong danh sách
// Hàm tên: getFirstOrder();




public class Main {
    public static void main(String[] args) {
        Customers customers = new Customers();
        Orders orders = new Orders();
        SetMenus menus = new SetMenus();
        Utils utils = new Utils();
        
        int choice;
        do {            
            System.out.println("====== TRADITIONAL FEAST ORDER MANAGEMENT ======");
            System.out.println("1. Register customer");
            System.out.println("2. Update customer");
            System.out.println("3. Search customer by name");
            System.out.println("4. Show feast menu list");
            System.out.println("5. Place a feast order");
            System.out.println("6. Update feast order");
            System.out.println("7. Show customer or order list");
            System.out.println("8. Save data to file");
            System.out.println("9. Display first order");
            System.out.println("0. Exit");
            System.out.println("===============================================");
            choice = utils.getInt("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    registerCustomer(customers, utils);
                    break;
                case 2:
                    updateCustomer(customers, utils);
                    break;
                case 3:
                    searchCustomerByName(customers, utils);
                    break;
                case 4:
                    menus.showAll();
                    break;
                case 5:
                    placeOrder(orders, customers, menus, utils);
                    break;
                case 6:
                    updateOrder(orders, menus, utils);
                    break;
                case 7:
                    showData(customers, orders, utils);
                    break;
                case 8:
                    customers.saveToFile();
                    orders.saveToFile();
                    System.out.println("Data saved successfullly.");
                    break;
                case 9:
                    
                    break;
                case 0:
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }
    
    private static void registerCustomer (Customers customers, Utils utils) {
        System.out.println("\n--- REGISTER CUSTOMER ---");
        String id = utils.inputAndLoop("Enter ID (C/G/Kxxxx): ", Acceptable.CUS_ID_VALID);
        if (customers.isDuplicate(id)) {
            System.out.println("This ID already exists.");
            return;
        }
        
        String name = utils.inputAndLoop("Enter name: ", Acceptable.NAME_VALID);
        String phone = utils.inputAndLoop("Enter phoneL ", Acceptable.PHONE_VALID);
        String email = utils.inputAndLoop("Enter email: ", Acceptable.EMAIL_VALID);
        
        customers.addNew(new Customer(id, name, phone, email));
    }
    
    private static void updateCustomer (Customers customers, Utils utils) {
        System.out.println("\n--- UPDATE CUSTOMER ---");
        String id = utils.inputAndLoop("Enter customer ID to update: ", Acceptable.CUS_ID_VALID);
        Customer c = customers.searchById(id);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        customers.update(c);
        
    }
    
    private static void searchCustomerByName (Customers customers, Utils utils) {
        System.out.println("\n--- SEARCH CUSTOMER BY NAME ---");
        String name = Utils.getString("Enter full or partial name: ");
        List<Customer> result = customers.searchByName(name);
        if (result.isEmpty()) {
            System.out.println("No one matches the search criterial!");
        } else {
            System.out.println("Matching Customers:");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-6s | %-20s | %-10s | %-25s\n", "Code", "Customer Name", "Phone", "Email");
            System.out.println("----------------------------------------------------------------");
            for (Customer c : result) {
                System.out.printf("%-6s | %-20s | %-10s | %-25s\n",
                        c.getId(), c.getName(), c.getPhone(), c.getEmail());
            }
        }
    }
    
    private static void placeOrder (Orders orders, Customers customers, SetMenus menus, Utils utils) {
        System.out.println("\n--- PLACE ORDER ---");
        new OrderHandler().placeOrder(orders, customers, menus, utils);
    }
    
    private static void updateOrder (Orders orders, SetMenus menus, Utils utils) {
        System.out.println("\n --- UPDATE ORDER ---");
        String orderId = Utils.getString("Enter Order ID: ");
        Order existing = orders.searchById(orderId);
        if (existing == null) {
            System.out.println("This Order does not exist.");
            return;
        }
        
        if (existing.getEventDate().before(new java.util.Date())) {
            System.out.println("Cannot update order that already occurred.");
            return;
        }
        
        String newMenuId = utils.inputAndLoop("Enter new Menu ID (or press ENTER to keep [" + existing.getMenuId() + "]): ", 
                Acceptable.MENU_ID_VALID);
        if (!newMenuId.isEmpty() && menus.isValidMenuID(newMenuId)) {
            existing.setMenuId(newMenuId);
        }
        
        int newTables = Utils.updateInt("Enter new table count (or press ENTER to keep [" + existing.getNumOfTables() + "]): ",
                1, 100, existing.getNumOfTables());
        existing.setNumOfTables(newTables);
        
        java.util.Date newDate;
        while (true) {
            String newDateStr = Utils.updateString("Enter new event date (dd/MM/yyyy): ",
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(existing.getEventDate()));
            try {
                newDate = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(newDateStr);
                if (newDate.after(new java.util.Date())) {
                    existing.setEventDate(newDate);
                    break;
                } else {
                    System.out.println("Date must be in the future.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date.");
            }
        }
        orders.update(existing);
    }
    
    private static void showData (Customers customers, Orders orders, Utils utils) {
        System.out.println("\n--- DISPLAY DATA ---");
        System.out.println("1. Show customer list");
        System.out.println("2. Show order list");
        int sub = utils.getInt("Enter choice: ");
        if (sub == 1) {
            customers.showAll();
        } else if (sub == 2) {
            orders.showAll();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
