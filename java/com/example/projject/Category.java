package com.example.projject;


public class Category {
    public static final int COMPUTER_SCIENCE = 1;
    public static final int GENERAL_KNOWLEDGE= 2;
    public static final int Pakistan_currentAffairs=3;
    public static final int Entertainment=4;
    public static final int Math=5;


    private int id;
    private String name;
    public Category() {
    }
    public Category(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return getName();
    }
}
