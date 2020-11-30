package dao;

import bean.Order;
import bean.User;
import dao.exception.DaoException;

import java.util.List;

public interface OrderDao {
    void addOrder(Order order) throws DaoException;
    void deleteOrder(Order order) throws DaoException;
    List<Order> getOrders() throws DaoException;
    List<Order> getOrdersByUser(User user) throws DaoException;
}
