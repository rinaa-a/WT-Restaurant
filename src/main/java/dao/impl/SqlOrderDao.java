package dao.impl;

import bean.Order;
import bean.User;
import bean.Dish;
import dao.OrderDao;
import dao.connection_pool.ConnectionPool;
import dao.exception.DaoException;
import dao.factory.DaoFactory;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlOrderDao implements OrderDao {
    @Override
    public void addOrder(Order order) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "INSERT INTO orders (user_name, dishes, cost) VALUES (?, ?, ?)";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, order.getUser().getUsername());
            var dishes = connection.createArrayOf("dish", order.getDishes().toArray());
            statement.setArray(2, dishes);
            statement.setInt(3, order.getCost());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public void deleteOrder(Order order) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "DELETE FROM orders WHERE user_name=? AND order_id=?";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, order.getUser().getUsername());
            statement.setInt(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public List<Order> getOrders() throws DaoException {
        List<Order> orders = new ArrayList<>();
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "SELECT * FROM orders";
            var statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                var order = new Order();
                var dishes = DaoFactory.getInstance().getDishDao().getDishes();
                var user = DaoFactory.getInstance().getUserDao().getUserByUsername(rs.getString("user_name"));
                order.setDishes(dishes);
                order.setUser(user);
                order.setCost(rs.getInt("cost"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByUser(User user) throws DaoException {
        List<Order> orders = new ArrayList<>();
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "SELECT * FROM orders WHERE user_name=?";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                var order = new Order();
                var dishes = DaoFactory.getInstance().getDishDao().getDishes();
                user = DaoFactory.getInstance().getUserDao().getUserByUsername(rs.getString("user_name"));
                order.setDishes(dishes);
                order.setUser(user);
                order.setCost(rs.getInt("cost"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
        return orders;
    }
}
