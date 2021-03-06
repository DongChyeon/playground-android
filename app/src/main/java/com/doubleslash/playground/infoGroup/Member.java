package com.doubleslash.playground.infoGroup;

public class Member {
    Long id;
    String image;
    String name;

    public Member(Long id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
