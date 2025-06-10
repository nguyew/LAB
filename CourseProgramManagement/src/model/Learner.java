package model;


public class Learner {
   private String id;
   private String name;
   private String dob;
   private double score;
   private String courseId;

    public Learner(String id, String name, String dob, double score, String courseId) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.score = score;
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
   
   public String toString () {
        String status = (score >= 0) ? ((score >= 5) ? "Pass" : "Fail") : "Not scored";
        return String.format("ID: %s | Name: %s | DOB: %s | Score: %.2f | Status: %s | CourseID: %s",
                id, name, dob, score, status, courseId);
   }
}
