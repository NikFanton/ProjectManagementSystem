package dao;

import java.util.List;

public interface DAOBase<E, Key> {
    void add(E e);
    E getById(Key id);
    List<E> getAll();
    void update(E e);
    void delete(Key id);
}
