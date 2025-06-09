package com.example.demo.interfaces;
import java.util.List;

public interface CRUD<T> {
    void create( T entity);
    List<T> findAll();
    T find(int id);
    void update( T entity, int id);
    void delete( int id);
}
