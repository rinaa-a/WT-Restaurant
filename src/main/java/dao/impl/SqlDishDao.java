package dao.impl;

import bean.Dish;
import bean.enums.Category;
import dao.DishDao;
import dao.connection_pool.ConnectionPool;
import dao.exception.DaoException;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlDishDao implements DishDao {
    @Override
    public void addDish(Dish dish) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "INSERT INTO dishes (name, cost, category, count) VALUES (?, ?, ?, ?)";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, dish.getName());
            statement.setInt(2, dish.getCost());
            statement.setInt(3, dish.getCategory().ordinal());
            statement.setInt(4, dish.getCount());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public void editDish(Dish dish) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "UPDATE dishes SET name=?, cost=?, category=?, count=? WHERE id=?";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, dish.getName());
            statement.setInt(2, dish.getCost());
            statement.setInt(3, dish.getCategory().ordinal());
            statement.setInt(4, dish.getCount());
            statement.setInt(5, dish.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public void deleteDish(Dish dish) throws DaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "DELETE FROM dishes WHERE id=?";
            var statement = connection.prepareStatement(sql);
            statement.setInt(1, dish.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public Dish getDishById(int id) throws DaoException {
        var dish = new Dish();
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "SELECT * FROM dishes WHERE id=?";
            var statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setCost(rs.getInt("cost"));
                dish.setCategory(Category.values()[rs.getInt("category")]);
                dish.setCount(rs.getInt("count"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
        return dish;
    }

    @Override
    public ArrayList<Dish> getDishes() throws DaoException {
        ArrayList<Dish> dishes = new ArrayList<>();
        ConnectionPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();

            var sql = "SELECT * FROM dishes";
            var statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                var dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setCost(rs.getInt("cost"));
                dish.setCategory(Category.values()[rs.getInt("category")]);
                dish.setCount(rs.getInt("count"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
        return dishes;
    }
}
