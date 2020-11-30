package dao.factory;

import dao.DishDao;
import dao.OrderDao;
import dao.UserDao;
import dao.impl.SqlDishDao;
import dao.impl.SqlOrderDao;
import dao.impl.SqlUserDao;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();

    private final DishDao sqlDishImpl = new SqlDishDao();
    private final UserDao sqlUserImpl = new SqlUserDao();
    private final OrderDao sqlOrderImpl = new SqlOrderDao();

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return instance;
    }

    public DishDao getDishDao() {
        return sqlDishImpl;
    }

    public UserDao getUserDao() {
        return sqlUserImpl;
    }

    public OrderDao getOrderDao() {
        return sqlOrderImpl;
    }
}
