package com.db.databaseapp.dao;

import com.db.databaseapp.connectionsingleton.DatabaseConnection;
import com.db.databaseapp.beans.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

public class UserDao implements Dao<User> {
    protected Set<User> users = new TreeSet<>();
    Logger rootLogger = LogManager.getRootLogger();

    @Override
    public Set<User> getAllUsers() {
        String sql = "select * from public.users where isdeleted = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, false);
                ResultSet resultSet = preparedStatement.executeQuery();
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
            rootLogger.log(Level.WARN, "Exception while printing users: " + sqlException.getMessage());
        }
        return users;
    }

    @Override
    public void deleteUserById(int id) {
        String sql = "update public.users " +
                "set isDeleted = ? " +
                "where public.users.user_id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException sqlException) {
            rootLogger.log(Level.WARN, "Exception while deleting users by id: " + sqlException.getMessage());
        }
    }

    @Override
    public void addUser(User user) {
        if (!contains(user)) {
            String sql = "insert into public.users(user_age, first_name, last_name) values (?, ?, ?)";
            try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, user.getUserAge());
                    preparedStatement.setString(2, user.getFirstName());
                    preparedStatement.setString(3, user.getLastName());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException sqlException) {
                rootLogger.log(Level.WARN, "Exception while adding users: " + sqlException.getMessage());
            }
        }
    }

    @Override
    public User selectById(int id) {
        User user = null;
        String sql = "select * from public.users where user_id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    int userAge = resultSet.getInt(3);
                    String firstName = resultSet.getString(4);
                    String lastName = resultSet.getString(5);
                    user = new User(userId, userAge, firstName, lastName);
                }
            }
        } catch (SQLException sqlException) {
            rootLogger.log(Level.WARN, "Exception while selecting users by id: " + sqlException.getMessage());
        }
        return user;
    }

    @Override
    public int updateUser(User user) {
        String sql = "update public.users " +
                "set user_age = ?, " +
                "first_name = ?, " +
                "last_name = ? " +
                "where user_id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()){
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, user.getUserAge());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setInt(4, user.getUserId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException sqlException) {
            rootLogger.log(Level.WARN, "Exception while updating users: " + sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public boolean contains(User user) {
        if (users.contains(user)) {
            rootLogger.log(Level.WARN, "Attention! Can not insert, user is already exists");
            return true;
        }
        return false;
    }
}
