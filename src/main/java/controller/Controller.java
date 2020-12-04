package controller;

import bean.Dish;
import bean.Order;
import bean.User;
import bean.enums.Category;
import bean.enums.Role;
import service.RestaurantService;
import service.UserService;
import service.exception.ServiceException;
import service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

@WebServlet(value = "/restaurant/*")
public class Controller extends HttpServlet {
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final RestaurantService restaurantService = ServiceFactory.getInstance().getRestaurantService();
    private final Logger logger = (Logger) LogManager.getLogger();
    private ResourceBundle bundle = ResourceBundle.getBundle("text");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setLanguage(request);
        String[] array = request.getRequestURI().split("/");
        String action = array[array.length - 1];

        switch (action) {
            case "log-in" -> request.getRequestDispatcher("/WEB-INF/jsp/log-in.jsp").forward(request, response);
            case "sign-up" -> request.getRequestDispatcher("/WEB-INF/jsp/sign-up.jsp").forward(request, response);
            case "log-out" -> logOut(request, response);
            case "menu" -> handleMenuPage(request, response);
            case "orders" -> handleOrdersPage(request, response);
            case "addDish" -> addDish(request, response);
            case "editDish" -> editDish(request, response);
            default -> request.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] array = request.getRequestURI().split("/");
        String action = array[array.length - 1];

        switch (action) {
            case "log-in" -> logIn(request, response);
            case "sign-up" -> signUp(request, response);
            default -> request.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(request, response);
        }
    }

    private void setLanguage(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        if (lang != null) {
            request.getSession().setAttribute("lang", lang);
            bundle = ResourceBundle.getBundle("text", new Locale(lang));
            String query = request.getQueryString();
            request.setAttribute("queryWithLang", query.substring(0, query.lastIndexOf("=")) + "=");
        }
    }

    private void logIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        var user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        try {
            user = userService.signIn(user);
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect(dispatch(request.getRequestURL().toString(), "catalog"));
            } else {
                request.setAttribute("errorMessage", bundle.getString("incorrect_username_password"));
                request.getRequestDispatcher("/WEB-INF/jsp/sign-in.jsp").forward(request, response);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            request.setAttribute("errorMessage", bundle.getString("empty_fields"));
            request.getRequestDispatcher("/WEB-INF/jsp/sign-in.jsp").forward(request, response);
        }
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        var user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setRole(Role.USER);
        try {
            user = userService.signUp(user);
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect(dispatch(request.getRequestURL().toString(), "menu"));
            } else {
                request.setAttribute("errorMessage", bundle.getString("username_is_busy"));
                request.getRequestDispatcher("/WEB-INF/jsp/sign-up.jsp").forward(request, response);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            request.setAttribute("errorMessage", bundle.getString("empty_fields"));
            request.getRequestDispatcher("/WEB-INF/jsp/sign-up.jsp").forward(request, response);
        }
    }

    private void logOut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/library/log-in");
    }

    private void handleMenuPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchRequest = request.getParameter("search");
        if (searchRequest == null) {
            try {
                request.setAttribute("dishes", restaurantService.getDishesList());
            } catch (ServiceException e) {
                logger.error(e.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_dishes_list"));
            }
        } else {
            try {
                List<Dish> dishList = restaurantService.searchDishes(searchRequest);
                request.setAttribute("dishes", dishList);
                if (dishList.size() == 0) {
                    request.setAttribute("noElements", bundle.getString("nothing_found"));
                }
            } catch (ServiceException e) {
                logger.error(e.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_searching"));
            }
        }
        request.getRequestDispatcher("/WEB-INF/jsp/menu.jsp").forward(request, response);
    }

    private void handleOrdersPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            String action = request.getParameter("action");
            String userName = request.getParameter("userName");
            String dishId = request.getParameter("dishId");
            boolean langIsSet = request.getParameter("lang") != null;
            List<Order> orders;
            if (user.getRole() == Role.USER) {
                orders = getUserOrders(dishId, user, action, langIsSet);
            } else {
                orders = getOrders(dishId, userName, action, langIsSet);
            }
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/jsp/orders.jsp").forward(request, response);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            request.setAttribute("errorMessage", bundle.getString("error_order_list"));
        }
    }

    private void addDish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String cost = request.getParameter("cost");
        String category = request.getParameter("category");
        String count = request.getParameter("count");
        if (name != null && cost != null && category != null && count != null) {
            try {
                var dish = new Dish();
                dish.setName(name);
                dish.setCount(Integer.parseInt(count));
                dish.setCost(Integer.parseInt(cost));
                dish.setCategory(Category.values()[Integer.parseInt(category)]);
                if (!restaurantService.addNewDish(dish)) {
                    request.setAttribute("errorMessage", bundle.getString("error_add_dish"));
                    request.setAttribute("type", "add");
                    request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(dispatch(request.getRequestURL().toString(), "menu"));
                }
            } catch (ServiceException e) {
                logger.error(e.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_add_dish"));
                request.setAttribute("type", "add");
                request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_count"));
                request.setAttribute("type", "add");
                request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
            }
        }
    }

    private void editDish(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String cost = request.getParameter("cost");
        String category = request.getParameter("category");
        String count = request.getParameter("count");
        if (id != null && name != null && cost != null && category != null && count != null) {
            try {
                var dish = new Dish();
                dish.setName(name);
                dish.setCount(Integer.parseInt(count));
                dish.setCost(Integer.parseInt(cost));
                dish.setCategory(Category.values()[Integer.parseInt(category)]);
                if (!restaurantService.addEditedDish(dish)) {
                    request.setAttribute("errorMessage", bundle.getString("error_edit_dish"));
                    request.setAttribute("dish", dish);
                    request.setAttribute("type", "edit");
                    request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(dispatch(request.getRequestURL().toString(), "catalog"));
                }
            } catch (ServiceException e) {
                logger.error(e.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_edit_book"));
                request.setAttribute("type", "edit");
                request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                request.setAttribute("errorMessage", bundle.getString("error_count"));
                request.setAttribute("type", "edit");
                request.getRequestDispatcher("/WEB-INF/jsp/add-edit.jsp").forward(request, response);
            }
        }
    }

    private List<Order> getUserOrders(String dishId, User user, String action, boolean langIsSet) throws ServiceException {
        if (dishId != null && !langIsSet) {
            Dish dish = restaurantService.getDishById(dishId);
            var order = new Order();
            order.setUser(user);
            var dishes = new ArrayList<Dish>();
            dishes.add(dish);
            order.setDishes(dishes);
            if (action == null) {
                restaurantService.addNewOrder(order);
            } else {
                restaurantService.deleteOrder(order);
            }
        }
        return restaurantService.getOrdersByUser(user);
    }

    private List<Order> getOrders(String dishId, String userName, String action, boolean langIsSet)
            throws ServiceException {
        if (dishId != null && !langIsSet) {
            User user = userService.getUserByUsername(userName);
            Dish dish = restaurantService.getDishById(dishId);
            var order = new Order();
            order.setUser(user);
            var dishes = new ArrayList<Dish>();
            dishes.add(dish);
            order.setDishes(dishes);
            if ("cancel".equals(action)) {
                restaurantService.deleteOrder(order);
            }
        }
        return restaurantService.getOrders();
    }

    private String dispatch(String url, String destination) {
        return url.substring(0, url.lastIndexOf("/")) + "/" + destination;
    }

}
