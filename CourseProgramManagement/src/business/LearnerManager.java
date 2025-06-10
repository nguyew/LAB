package business;

import model.Learner;
import model.Course;
import tools.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.I_Manager;

public class LearnerManager implements I_Manager {
    private final ArrayList<Learner> learners = new ArrayList<>();
    private final CourseManager courseManager;

    public LearnerManager(CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    @Override
    public void add() {
        while (true) {
            String id = Utils.inputString("Enter Learner ID: ");
            if (findById(id) != null) {
                System.out.println("ID already exists.");
                continue;
            }
            String name = Utils.inputString("Enter Learner Name: ");
            String dob = Utils.inputString("Enter Date of Birth (dd-mm-yyyy): ");
            String courseId = Utils.inputString("Enter Course ID: ");
            Course course = courseManager.getCourses()
                .stream().filter(c -> c.getId().equalsIgnoreCase(courseId)).findFirst().orElse(null);
            if (course == null) {
                System.out.println("Course ID not found.");
                continue;
            }
            learners.add(new Learner(id, name, dob, -1, courseId)); // -1 = chưa có điểm
            if (!Utils.confirm("Add more?")) break;
        }
    }

    @Override
    public void update() {
        String id = Utils.inputString("Enter Learner ID to enter score: ");
        Learner learner = findById(id);
        if (learner == null) {
            System.out.println("The learner does not exist.");
            return;
        }
        double score = Utils.inputDouble("Enter score (0-10): ", 0, 10);
        learner.setScore(score);
        System.out.println("Score updated.");
    }

    @Override
    public void delete() {
        String id = Utils.inputString("Enter Learner ID to delete: ");
        Learner learner = findById(id);
        if (learner == null) {
            System.out.println("The learner does not exist.");
            return;
        }
        if (Utils.confirm("Are you sure to delete this learner?")) {
            learners.remove(learner);
            System.out.println("Deleted successfully.");
        }
    }

    @Override
    public void display() {
        learners.stream()
                .sorted(Comparator.comparing(Learner::getName))
                .forEach(l -> {
                    String status = (l.getScore() >= 5) ? "Pass" : "Fail";
                    System.out.printf("ID: %s | Name: %s | DOB: %s | Score: %.2f | Status: %s\n",
                            l.getId(), l.getName(), l.getDob(), l.getScore(), (l.getScore() >= 0 ? status : "N/A"));
                });
    }

    private Learner findById(String id) {
        for (Learner l : learners) {
            if (l.getId().equalsIgnoreCase(id)) return l;
        }
        return null;
    }
    
    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Learner l : learners) {
            lines.add(String.join("|", l.getId(), l.getName(), l.getDob(),
                    String.valueOf(l.getScore()), l.getCourseId()));
        }
        Utils.saveToFile("learners.txt", lines);
    }

    public void loadFromFile() {
        List<String> lines = Utils.loadFromFile("learners.txt");
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                learners.add(new Learner(parts[0], parts[1], parts[2],
                        Double.parseDouble(parts[3]), parts[4]));
            }
        }
}

}

