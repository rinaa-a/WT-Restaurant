package controller.command;

import exception.CommandException;
import exception.ServiceException;
import service.ServiceFactory;
import service.interfaces.ClientService;

import javax.servlet.http.HttpServletRequest;

public class SignIn implements ICommand {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = null;
        String password = null;

        String response = null;

        //get params from request and init login and password

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();

        try {
            clientService.signIn(login, password);
            response = "Welcome";
        } catch (ServiceException e ) {
            // write log
            response = "Error during login procedure";
        }
        return response;
    }
}
