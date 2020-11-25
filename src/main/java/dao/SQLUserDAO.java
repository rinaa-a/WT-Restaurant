package dao;

import dao.interfaces.UserDAO;
import entities.User;
import exception.DAOException;

public class SQLUserDAO implements UserDAO {
    @Override
    public void signIn(String login, String password) throws DAOException {
        //connect database and check login and password
    }

    @Override
    public void registration(User user) throws DAOException{

    }
}
