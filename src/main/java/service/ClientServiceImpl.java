package service;

import dao.DAOFactory;
import dao.interfaces.UserDAO;
import entities.User;
import exception.DAOException;
import exception.ServiceException;
import service.interfaces.ClientService;

public class ClientServiceImpl implements ClientService {
    @Override
    public void signIn(String login, String password) throws ServiceException {
        //check parameters
        if (login == null || login.isEmpty()) {
            throw new ServiceException("Incorrect login!");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            userDAO.signIn(login, password);
        } catch (DAOException e){
            throw new ServiceException(e);
        }


        //...
    }

    @Override
    public void signOut(String login) throws ServiceException{

    }

    @Override
    public void registration(User user) throws ServiceException{

    }
}
