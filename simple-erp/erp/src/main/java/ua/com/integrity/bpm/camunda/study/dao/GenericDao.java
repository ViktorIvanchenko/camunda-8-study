package ua.com.integrity.bpm.camunda.study.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T,I> {

    Optional<T> find(I id);
    List<T> findAll();
    T create(T t);
    void update(T t);
    void delete(I id);

}
