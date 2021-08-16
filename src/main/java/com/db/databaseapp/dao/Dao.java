package com.db.databaseapp.dao;

import java.util.Set;

public interface Dao<T> {

    Set<T> getAllUsers();

    void deleteUserById(int id);

    void addUser(T object);

    boolean contains(T object);

    T selectById(int id);

    int updateUser(T object);
}
