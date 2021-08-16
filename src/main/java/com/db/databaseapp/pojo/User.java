package com.db.databaseapp.pojo;

import java.util.Objects;

public class User implements Comparable<User> {
    int userId;
    int userAge;
    String firstName;
    String lastName;


    public User(int userId, int userAge, String firstName, String lastName) {
        this.userId = userId;
        this.userAge = userAge;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(int userAge, String firstName, String lastName) {
        this.userAge = userAge;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserAge() {
        return userAge;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int compareTo(User user) {
        return user.getUserId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId
                && userAge == user.userAge
                && firstName.equals(user.firstName)
                && lastName.equals(user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userAge, firstName, lastName);
    }
}
