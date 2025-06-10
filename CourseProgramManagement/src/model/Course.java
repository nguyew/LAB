package model;


public class Course {
    private String id;
    private String name;
    private String type;
    private String title;
    private String beginDate;
    private String endDate;
    private double tuitionFee;
    private String topicId;

    public Course(String id, String name, String type, String title, String beginDate, String endDate, double tuitionFee, String topicId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.tuitionFee = tuitionFee;
        this.topicId = topicId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
    
    public String toString () {
         return String.format(
            "ID: %s | Name: %s | Type: %s | Title: %s | Begin: %s | End: %s | Fee: %.2f | TopicID: %s",
            id, name, type, title, beginDate, endDate, tuitionFee, topicId
        );
    }
}
