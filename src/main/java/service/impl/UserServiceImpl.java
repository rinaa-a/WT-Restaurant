package service.impl;

import bean.User;
import dao.UserDao;
import dao.exception.DaoException;
import dao.factory.DaoFactory;
import service.UserService;
import service.exception.ServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = DaoFactory.getInstance().getUserDao();

    @Override
    public User signIn(User user) throws ServiceException {
        if (user.getUsername().equals("")) {
            throw new ServiceException("Username cannot be empty");
        } else {
            try {
                if (CheckPassword(user)) {
                    return userDao.signIn(user);
                } else {
                    return null;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public User signUp(User user) throws ServiceException {
        if (user.getUsername().equals("") || user.getPassword().equals("")) {
            throw new ServiceException("Username and password cannot be empty");
        } else {
            try {
                if (CheckUsername(user)) {
                    return userDao.signUp(user);
                } else {
                    return null;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public User getUserByUsername(String username) throws ServiceException {
        try {
            User user = userDao.getUserByUsername(username);
            if (user.getId() == -1) {
                throw new ServiceException("No such user found");
            } else {
                return user;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private boolean CheckPassword(User user) throws ServiceException {
        try {
            List<User> users = userDao.getUsers();
            User findUser = users.stream()
                    .filter(u -> u.getUsername().equals(user.getUsername()))
                    .findFirst().orElse(null);
            return (findUser != null) && findUser.getPassword().equals(user.getPassword());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private boolean CheckUsername(User user) throws ServiceException {
        try {
            List<User> users = userDao.getUsers();
            return users.stream().noneMatch(u -> u.getUsername().equals(user.getUsername()));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
