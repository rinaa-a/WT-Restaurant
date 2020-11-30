package dao;

import bean.Dish;
import dao.exception.DaoException;

import java.util.ArrayList;

public interface DishDao {
    void addDish(Dish dish) throws DaoException;
    void editDish(Dish dish) throws DaoException;
    void deleteDish(Dish dish) throws DaoException;
    Dish getDishById(int id) throws DaoException;
    ArrayList<Dish> getDishes() throws DaoException;
}
