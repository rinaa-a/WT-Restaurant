package service.impl;

import bean.Dish;
import bean.Order;
import bean.User;
import bean.enums.Category;
import dao.DishDao;
import dao.OrderDao;
import dao.exception.DaoException;
import dao.factory.DaoFactory;
import service.RestaurantService;
import service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {
    private final DishDao dishDao = DaoFactory.getInstance().getDishDao();
    private final OrderDao orderDao = DaoFactory.getInstance().getOrderDao();

    @Override
    public boolean addNewDish(Dish dish) throws ServiceException {
        if (dish.getName().equals("") || dish.getCategory().equals(Category.NONE) || dish.getCount() < 0 || dish.getCost() < 0) {
            return false;
        } else {
            try {
                dishDao.addDish(dish);
                return true;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public boolean addEditedDish(Dish dish) throws ServiceException {
        if (dish.getName().equals("") || dish.getCategory().equals(Category.NONE) || dish.getCount() < 0 || dish.getCost() < 0 || dish.getId() < 0) {
            return false;
        } else {
            try {
                dishDao.editDish(dish);
                return true;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void deleteDish(Dish dish) throws ServiceException {
        try {
            dishDao.deleteDish(dish);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Dish> getDishesList() throws ServiceException {
        try {
            return dishDao.getDishes();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Dish getDishById(String id) throws ServiceException {
        try {
            Dish dish = dishDao.getDishById(Integer.parseInt(id));
            if (dish.getId() == -1) {
                throw new ServiceException("No such dish found");
            } else {
                return dish;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addNewOrder(Order order) throws ServiceException {
        try {
            orderDao.addOrder(order);
            ArrayList<Dish> dishes = order.getDishes();
            for (Dish d : dishes) {
                d.setCount(d.getCount() - 1);
                dishDao.editDish(d);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrders() throws ServiceException {
        try {
            return orderDao.getOrders();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersByUser(User user) throws ServiceException {
        try {
            return orderDao.getOrdersByUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteOrder(Order order) throws ServiceException {
        try {
            orderDao.deleteOrder(order);
            ArrayList<Dish> dishes = order.getDishes();
            for (Dish d : dishes) {
                d.setCount(d.getCount() + 1);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
