package com.juliano.carrental.dao;

import java.util.ArrayList;

public interface IDao<Model> {
    public Model get(int id);

    public ArrayList<Model> getAll();

    public boolean save(Model obj);

    public int update(Model obj);

    public void delete(Model obj);

    public void deleteAll();

}
