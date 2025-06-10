package model;


public class Topic {
    private String id, name, type, title;
    private int duration;

    public Topic(String id, String name, String type, String title, int duration) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.title = title;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public String toString () {
        return String.format("ID: %s | Name: %s | Type: %s | Title: %s | Duration: %dh", 
                            id, name, type, title, duration);
    }
}
