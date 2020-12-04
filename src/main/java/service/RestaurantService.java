package service;

import bean.Dish;
import bean.Order;
import bean.User;
import service.exception.ServiceException;

import java.util.List;

public interface RestaurantService {
    boolean addNewDish(Dish dish) throws ServiceException;
    boolean addEditedDish(Dish dish) throws ServiceException;
    void deleteDish(Dish dish) throws ServiceException;
    List<Dish> getDishesList() throws ServiceException;
    Dish getDishById(String id) throws ServiceException;
    void addNewOrder(Order order) throws ServiceException;
    List<Order> getOrders() throws ServiceException;
    List<Order> getOrdersByUser(User user) throws ServiceException;
    void deleteOrder(Order order) throws ServiceException;
}
