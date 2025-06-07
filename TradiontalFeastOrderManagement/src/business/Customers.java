package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Customer;
import tools.Utils;

public class Customers extends ArrayList<Customer> implements Workable<Customer>, Serializable{
    private final String filePath = "customer.dat";
    
    public Customers () {
        loadFromFile();
    }
    
    public boolean isDuplicate (String customerId) {
        return this.stream().anyMatch(c -> c.getId().equalsIgnoreCase(customerId));
    }
    
    @Override
    public boolean addNew(Customer c) {
        if (isDuplicate(c.getId())) {
            System.out.println("Duplicate customer ID ! Not addded.");
            return false;
        }
        this.add(c);
        System.out.println("Customer added successfully.");
        return true;
    }

    @Override
    public boolean update(Customer updated) {
        Customer existing = searchById(updated.getId());
        if (existing == null) {
            System.out.println("Customer not found,");
            return false;
        }
        
        existing.setName(Utils.updateString("Enter new name [" + existing.getName() + "]: ", existing.getName()));
        existing.setPhone(Utils.updateString("Enter new phone [" + existing.getPhone() + "]: ", existing.getPhone()));
        existing.setEmail(Utils.updateString("Enter new email [" + existing.getEmail() + "]: ", existing.getEmail()));
        System.out.println("✅ Customer updated.");
        return true;
    }

    @Override
    public Customer searchById(String id) {
        for (Customer c : this) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }
    
    public List<Customer> searchByName (String namePart) {
        List<Customer> result = new ArrayList<>();
        for (Customer c : this) {
            if (c.getName().toLowerCase().contains(namePart.toLowerCase())) {
                result.add(c);
            }
        }
        result.sort(Comparator.comparing(Customer::getName));
        return result;
    }

    @Override
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        
        Collections.sort(this, Comparator.comparing(Customer::getName));
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-6s | %-20s | %-10s | %-25s\n", "Code", "Customer Name", "Phone", "Email");
        System.out.println("----------------------------------------------------------------");
        for (Customer c : this) {
            System.out.printf("%-6s | %-20s | %-10s | %-25s\n",
                    c.getId(), c.getName(), c.getPhone(), c.getEmail());
        }
        System.out.println("----------------------------------------------------------------");
    }

    @Override
    public void saveToFile() {
        Utils.saveToFile(this, filePath);
    }

    @Override
    public void loadFromFile() {
        try {
            List<Customer> list = Utils.readFromFile(filePath);
            this.clear();
            this.addAll(list);
        } catch (Exception e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }
}
