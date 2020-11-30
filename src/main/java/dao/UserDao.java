package dao;


import bean.User;
import dao.exception.DaoException;

import java.util.List;

public interface UserDao {
    User signIn(User user) throws DaoException;
    User signUp(User user) throws DaoException;
    User getUserByUsername(String username) throws DaoException;
    List<User> getUsers() throws DaoException;
}
