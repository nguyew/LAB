package dispatcher;

import business.*;
import tools.Utils;

public class Main {
    private final TopicManager topicManager = new TopicManager();
    private final CourseManager courseManager = new CourseManager();
    private final LearnerManager learnerManager = new LearnerManager(courseManager);
    
    public void run () {
        topicManager.loadFromFile();
        courseManager.loadFromFile();
        learnerManager.loadFromFile();
        int choice;
        do {            
            showMainMenu();
            choice = Utils.inputInt("Your choice: ", 0, 6);
            switch (choice) {
                case 1:
                    topicMenu();
                    break;
                case 2: 
                    courseMenu();
                    break;
                case 3: 
                    learnerMenu();
                    break;
                case 4:
                    searchMenu();
                    break;
                case 5:
                    saveToFile(); // placeholder
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    break;
                
            }
        } while (choice != 6);
    }

    private void showMainMenu() {
        System.out.println("\n====== COURSE MANAGEMENT SYSTEM ======");
        System.out.println("1. Manage Topics");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Learners");
        System.out.println("4. Search");
        System.out.println("5. Save Data to File");
        System.out.println("6. Quit");
    }

    private void topicMenu() {
        int choice;
        do {            
            System.out.println("\n-- Topic Management --");
            System.out.println("1. Add Topic");
            System.out.println("2. Update Topic");
            System.out.println("3. Delete Topic");
            System.out.println("4. Display All Topics");
            System.out.println("0. Back to Main Menu");
            choice = Utils.inputInt("Your choice: ", 0, 4);
            switch (choice) {
                case 1:
                    topicManager.add();
                    break;
                case 2:
                    topicManager.update();
                    break;
                case 3:
                    topicManager.delete();
                    break;
                case 4:
                    topicManager.display();
            }
        } while (choice != 0);
    }

    private void courseMenu() {
         int choice;
        do {
            System.out.println("\n-- Course Management --");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Delete Course");
            System.out.println("4. Display All Courses");
            System.out.println("0. Back to Main Menu");
            choice = Utils.inputInt("Your choice: ", 0, 4);
            switch (choice) {
                case 1:
                    courseManager.add();
                    break;
                case 2:
                    courseManager.update();
                    break;
                case 3:
                    courseManager.delete();
                    break;
                case 4:
                    courseManager.display();
                    break;
            }
        } while (choice != 0);
    }

    private void learnerMenu() {
        int choice;
        do {
            System.out.println("\n-- Learner Management --");
            System.out.println("1. Add Learner");
            System.out.println("2. Enter Score");
            System.out.println("3. Delete Learner");
            System.out.println("4. Display All Learners");
            System.out.println("0. Back to Main Menu");
            choice = Utils.inputInt("Your choice: ", 0, 4);
            switch (choice) {
                case 1:
                    learnerManager.add();
                    break;
                case 2:
                    learnerManager.update();
                    break;
                case 3:
                    learnerManager.delete();
                    break;
                case 4:
                    learnerManager.display();
                    break;
            }
        } while (choice != 0);
    }
    
    private void searchMenu () {
        int choice;
        do {
            System.out.println("\n-- Search Menu --");
            System.out.println("1. Search Topic by Name");
            System.out.println("2. Search Course by Topic ID");
            System.out.println("3. Search Course by Name");
            System.out.println("0. Back to Main Menu");
            choice = Utils.inputInt("Your choice: ", 0, 3);
            switch (choice) {
                case 1:
                    topicManager.searchByName();
                    break;
                case 2:
                    courseManager.searchByTopic();
                    break;
                case 3:
                    courseManager.searchByName();
                    break;
            }
        } while (choice != 0);
    }
    
    private void saveToFile() {
        topicManager.saveToFile();
        courseManager.saveToFile();
        learnerManager.saveToFile();
    }
    
    public static void main(String[] args) {
        new Main().run();
    }
}
