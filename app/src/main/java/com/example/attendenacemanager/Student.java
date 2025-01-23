package com.example.attendenacemanager;

public class Student {
    private int ID;
    private String firstName;
    private String lastName;
    private String email;
    private String tel;

    // Constructor
    public Student(int ID, String firstName, String lastName, String email, String tel) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
    }

    // Default constructor
    public Student() {
    }

    // Getter and Setter for ID
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for tel
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

//    // toString method for better representation of Student object
//    @Override
//    public String toString() {
//        return "Student{" +
//                "ID=" + ID +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", tel='" + tel + '\'' +
//                '}';
//    }
}
