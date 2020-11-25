package dao.interfaces;

import entities.Dish;
import exception.DAOException;

public interface DishDAO {
    void addDish(Dish dish) throws DAOException;
    void removeDishById(int id) throws DAOException;
    void editDish(Dish dish) throws DAOException;
}
