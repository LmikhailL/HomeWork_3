package com.db.databaseapp.dao;

import com.db.databaseapp.connectionsingleton.ConnectionSingleton;
import com.db.databaseapp.beans.User;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

public class UserDao implements Dao<User> {
    protected Set<User> users = new TreeSet<>();

    @Override
    public Set<User> getAllUsers() {
        Connection connection = null;
        String sql = "select * from public.users where isdeleted = false";
        try {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            connection = connectionSingleton.getConnection();
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    int userAge = resultSet.getInt(3);
                    String firstName = resultSet.getString(4);
                    String lastName = resultSet.getString(5);
                    User user = new User(userId, userAge, firstName, lastName);
                    users.add(user);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Exception while printing users: " + sqlException.getMessage());
        }
        return users;
    }

    @Override
    public void deleteUserById(int id) {
        Connection connection = null;
        String sql = "update public.users " +
                "set isDeleted = ? " +
                "where public.users.user_id = ?";
        try {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            connection = connectionSingleton.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.out.println("Exception while deleting user by id: " + sqlException.getMessage());
        }
    }

    @Override
    public void addUser(User user) {
        if (!contains(user)) {
            Connection connection = null;
            String sql = "insert into public.users(user_age, first_name, last_name) values (?, ?, ?)";
            try {
                ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
                connection = connectionSingleton.getConnection();
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, user.getUserAge());
                    preparedStatement.setString(2, user.getFirstName());
                    preparedStatement.setString(3, user.getLastName());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException sqlException) {
                System.out.println("Exception while adding user: " + sqlException.getMessage());
            }
        }
    }

    @Override
    public User selectById(int id) {
        Connection connection = null;
        User user = null;
        String sql = String.format("select * from public.users where user_id = %d", id);
        try {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            connection = connectionSingleton.getConnection();
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    int userAge = resultSet.getInt(3);
                    String firstName = resultSet.getString(4);
                    String lastName = resultSet.getString(5);
                    user = new User(userId, userAge, firstName, lastName);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Exception while selecting users by id: " + sqlException.getMessage());
        }
        return user;
    }

    @Override
    public int updateUser(User user) {
        Connection connection = null;
        String sql = "update public.users " +
                "set user_age = ?, " +
                "first_name = ?, " +
                "last_name = ? " +
                "where user_id = ?";
        try {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            connection = connectionSingleton.getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, user.getUserAge());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setInt(4, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.out.println("Exception while updating users: " + sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public boolean contains(User user) {
        if (users.contains(user)) {
            System.out.println("User has already added!");
            return true;
        }
        return false;
    }
}
