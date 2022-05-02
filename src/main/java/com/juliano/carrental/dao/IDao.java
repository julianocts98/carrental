package com.juliano.carrental.dao;

import java.util.ArrayList;

public interface IDao<T> {
    public T get(int id);

    public ArrayList<T> getAll();

    public boolean save(T obj);

    public int update(T obj);

    public void delete(T obj);

    public void deleteAll();

}
