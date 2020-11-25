package service;

import service.interfaces.ClientService;
import service.interfaces.DishService;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final ClientService clientService = new ClientServiceImpl();
    private final DishService dishService = new DishServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public  DishService getDishService() {
        return dishService;
    }
}
