package business;

import java.util.*;
import model.Course;
import model.I_Manager;
import tools.Utils;

public class CourseManager implements I_Manager {
    private final ArrayList<Course> courses = new ArrayList<>();

    @Override
    public void add() {
        while (true) {
            String id = Utils.inputString("Enter Course ID: ");
            if (findById(id) != null) {
                System.out.println("ID already exists.");
                continue;
            }
            
            String name = Utils.inputString("Enter Course Name: ");
            String type = Utils.inputString("Enter Type (online/offline): ");
            String title = Utils.inputString("Enter Title: ");
            String begin = Utils.inputString("Enter Begin Date (dd-mm-yyyy): ");
            String end = Utils.inputString("Enter End Date (dd-mm-yyyy): ");
            double fee = Utils.inputDouble("Enter Tuition Fee: ", 0, 10000000);
            String topicId = Utils.inputString("Enter Topic ID: ");
            courses.add(new Course(id, name, type, title, begin, end, fee, topicId));
            if (!Utils.confirm("Add more?")) break;
        }
    }

    @Override
    public void update() {
        String id = Utils.inputString("Enter Course ID to update: ");
        Course course = (Course) findById(id);
        if (course == null) {
            System.out.println("The course does not exist.");
            return;
        }
        String name = Utils.inputString("New name (blank to skip): ");
        String type = Utils.inputString("New type (blank to skip): ");
        String title = Utils.inputString("New title (blank to skip): ");
        String begin = Utils.inputString("New begin date (blank to skip): ");
        String end = Utils.inputString("New end date (blank to skip): ");
        String feeStr = Utils.inputString("New tuition fee (blank to skip): ");
        String topicId = Utils.inputString("New topic ID (blank to skip): ");

        if (!name.isEmpty()) course.setName(name);
        if (!type.isEmpty()) course.setType(type);
        if (!title.isEmpty()) course.setTitle(title);
        if (!begin.isEmpty()) course.setBeginDate(begin);
        if (!end.isEmpty()) course.setEndDate(end);
        if (!feeStr.isEmpty()) course.setTuitionFee(Double.parseDouble(feeStr));
        if (!topicId.isEmpty()) course.setTopicId(topicId);
        System.out.println("Update successful.");
    }

    @Override
    public void delete() {
        String id = Utils.inputString("Enter Course ID to delete: ");
        Course course = (Course) findById(id);
        if (course == null) {
            System.out.println("The course does not exist.");
            return;
        }
        if (Utils.confirm("Are your sure to delete this course?")) {
            courses.remove(course);
            System.out.println("Deleted successfully!");
        }
    }

    @Override
    public void display() {
        Collections.sort(courses, Comparator.comparing(Course::getBeginDate));
        courses.forEach(System.out::println);
    }

    private Object findById(String id) {
        for (Course c : courses) {
            if (c.getId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }
    
    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void searchByTopic() {
        String topicId = Utils.inputString("Enter Topic ID to search course: ");
        boolean found = false;
        for (Course c : courses) {
            if (c.getTopicId().equalsIgnoreCase(topicId)) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) System.out.println("No course found with that topic ID.");
    }

    public void searchByName() {
        String keyword = Utils.inputString("Enter keyword to search course name: ");
        boolean found = false;
        for (Course c : courses) {
            if (c.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) System.out.println("No course found with that name.");
    }
    
    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Course c : courses) {
            lines.add(String.join("|", c.getId(), c.getName(), c.getType(), c.getTitle(),
                    c.getBeginDate(), c.getEndDate(),
                    String.valueOf(c.getTuitionFee()), c.getTopicId()));
        }
        Utils.saveToFile("courses.txt", lines);
    }

    public void loadFromFile() {
        List<String> lines = Utils.loadFromFile("courses.txt");
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 8) {
                courses.add(new Course(parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], Double.parseDouble(parts[6]), parts[7]));
            }
        }
    }

}
