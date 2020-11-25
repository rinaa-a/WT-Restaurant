package service.interfaces;

import entities.User;
import exception.ServiceException;

public interface ClientService {
    void signIn (String login, String password) throws ServiceException;
    void signOut (String login) throws ServiceException;
    void registration (User user) throws ServiceException;
}
