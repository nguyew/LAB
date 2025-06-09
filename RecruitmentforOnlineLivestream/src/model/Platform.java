package model;

import java.io.Serializable;

public class Platform implements Serializable {
    private String code;
    private String name;
    private String description;

    public Platform(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
