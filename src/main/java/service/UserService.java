package service;

import bean.User;
import service.exception.ServiceException;

public interface UserService {

        User signIn(User user) throws ServiceException;
        User signUp(User user) throws ServiceException;
        User getUserByUsername(String username) throws ServiceException;
}
