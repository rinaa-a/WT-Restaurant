package dao;

import dao.interfaces.DishDAO;
import dao.interfaces.UserDAO;

public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final DishDAO sqlDishImpl = new SQLDishDAO();
    private final UserDAO sqlUserImpl = new SQLUserDAO();

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return instance;
    }

    public DishDAO getDishDAO(){
        return sqlDishImpl;
    }

    public UserDAO getUserDAO(){
        return sqlUserImpl;
    }
}
