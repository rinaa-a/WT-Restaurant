package service.interfaces;

import entities.Dish;
import exception.ServiceException;

public interface DishService {
    void addNewDish(Dish dish) throws ServiceException;
    void addEditedDish(Dish dish) throws ServiceException;
}
