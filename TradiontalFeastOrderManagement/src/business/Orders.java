package business;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Order;
import tools.Utils;

public class Orders implements Workable<Order>, Serializable {

    private final Set<Order> orderSet = new HashSet<>();
    private final String filePath = "feast_order_service.dat";

    public Orders() {
        loadFromFile(); 
    }

    public boolean isDuplicate(Order o) {
        return orderSet.contains(o);
    }

    
    @Override
    public boolean addNew(Order obj) {
        if (isDuplicate(obj)) {
            System.out.println("Duplicate order! Not added.");
            return false;
        }
        orderSet.add(obj);
        System.out.println("Order added successfully.");
        return true;
    }
    
    @Override
    public boolean update(Order updatedOrder) {
        Order existing = searchById(updatedOrder.getOrderCode());
        if (existing == null) {
            System.out.println("❌ Order not found.");
            return false;
        }
        
        if (existing.getEventDate().before(new Date())) {
            System.out.println("Cannot update past event orders.");
            return false;
        }

        orderSet.remove(existing);
        orderSet.add(updatedOrder);
        System.out.println("Order updated.");
        return true;
    }

    @Override
    public Order searchById(String id) {
        for (Order o : orderSet) {
            if (o.getOrderCode().equalsIgnoreCase(id)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public void showAll() {
        if (orderSet.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        List<Order> sortedList = new ArrayList<>(orderSet);
        sortedList.sort((o1, o2) -> o1.getEventDate().compareTo(o2.getEventDate()));

        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-14s | %-10s | %-8s | %-5s | %-12s\n",
                "Order ID", "Customer", "Menu ID", "Tables", "Event Date");
        System.out.println("----------------------------------------------------------------------------------");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Order o : sortedList) {
            System.out.printf("%-14s | %-10s | %-8s | %-6d | %-12s\n",
                    o.getOrderCode(), o.getCustomerId(), o.getMenuId(),
                    o.getNumOfTables(), sdf.format(o.getEventDate()));
        }

        System.out.println("----------------------------------------------------------------------------------");
    }

    @Override
    public void saveToFile() {
        Utils.saveToFile(new ArrayList<>(orderSet), filePath);
    }

    @Override
    public void loadFromFile() {
        try {
            List<Order> list = Utils.readFromFile(filePath);
            orderSet.clear();
            orderSet.addAll(list);
        } catch (Exception e) {
            System.out.println("⚠️ Error loading orders: " + e.getMessage());
        }
    }

}

