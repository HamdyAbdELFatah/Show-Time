package com.hamdy.showtime.ui.model;

public class Teacher {
    private String name,image,description,subject;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getSubject() {
        return subject;
    }

    public Teacher(String name, String image, String description, String subject) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.subject = subject;
    }
}
