package service.factory;

import service.RestaurantService;
import service.UserService;
import service.impl.RestaurantServiceImpl;
import service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final RestaurantService restaurantService = new RestaurantServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance(){
        return instance;
    }

    public UserService getUserService(){
        return userService;
    }

    public RestaurantService getRestaurantService(){
        return restaurantService;
    }
}
