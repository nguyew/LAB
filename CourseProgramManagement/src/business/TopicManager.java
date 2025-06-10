package business;

import java.util.*;
import model.I_Manager;
import model.Topic;
import tools.Utils;

public class TopicManager implements I_Manager {
    private final ArrayList<Topic> topics = new ArrayList<>();

    public TopicManager() {
        
    }
    
    @Override
    public void add() {
        while (true) {
            String id = Utils.inputString("Enter Topic ID: ");
            if (findById(id) != null) {
                System.out.println("ID already exists.");
                continue;
            }
            
            String name = Utils.inputString("Enter Topic Name: ");
            String type = Utils.inputString("Enter Topic Type: ");
            String title = Utils.inputString("Enter Topic Type: ");
            int duration = Utils.inputInt("Enter Duration (hours): ", 1, 1000);
            topics.add(new Topic(id, name, type, title, duration));
            if (!Utils.confirm("Add more?")) break;
        }
    }

    @Override
    public void update() {
        String id = Utils.inputString("Enter Topic ID to update: ");
        Topic topic = (Topic) findById(id);
        if (topic == null) {
            System.out.println("The topic does not exist.");
            return;
        }
        
        String name = Utils.inputString("Enter new name (blank to skip): ");
        String type = Utils.inputString("Enter new type (blank to skip): ");
        String title = Utils.inputString("Enter new title (blank to skip): ");
        String durStr = Utils.inputString("Enter new duration (blank to skip): ");
        
        if (!name.isEmpty()) topic.setName(name);
        if (!type.isEmpty()) topic.setType(type);
        if (!title.isEmpty()) topic.setTitle(title);
        if (!durStr.isEmpty()) topic.setDuration(Integer.parseInt(durStr));
        System.out.println("Update successful.");
    }

    @Override
    public void delete() {
        String id = Utils.inputString("Enter Topic ID to delete: ");
        Topic topic = (Topic) findById(id);
        if (topic == null) {
            System.out.println("The topic does not exist.");
            return;
        }
        if (Utils.confirm("Are you sure to delete this topic?")) {
            topics.remove(topic);
            System.out.println("Deleted successfully!");
        }
    }

    @Override
    public void display() {
        Collections.sort(topics, Comparator.comparing(Topic::getName));
        topics.forEach(System.out::println);
    }

    private Object findById(String id) {
        for (Topic t : topics) {
            if (t.getId().equalsIgnoreCase(id)) return t;
        }
        return null;
    }

    public void searchByName() {
        String keyword = Utils.inputString("Enter keywords to search topic name: ");
        boolean found = false;
        for (Topic t : topics) {
            if (t.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) System.out.println("No topic found");
    }
    
    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Topic t : topics) {
            lines.add(String.join("|", t.getId(), t.getName(), t.getType(), t.getTitle(), String.valueOf(t.getDuration())));
        }
        Utils.saveToFile("topics.txt", lines);
    }

    public void loadFromFile() {
        List<String> lines = Utils.loadFromFile("topics.txt");
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                topics.add(new Topic(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4])));
            }
        }
}

    
}
