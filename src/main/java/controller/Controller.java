package controller;

import controller.command.ICommand;
import exception.CommandException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/controller")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo
        System.out.println("hello ");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(RequestParameterName.COMMAND_NAME);
        ICommand command = CommandProvider.getInstance().getCommand(commandName);
        String page = null;

        try {
            page = command.execute(request);
        } catch (CommandException e) {
            page = JspPageName.ERROR_PAGE;
        } catch (Exception e) {
            page = JspPageName.ERROR_PAGE;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);

        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            errorMessageDirectlyFromResponse(response);
        }
    }

    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("ERROR");
    }

//    private final CommandProvider provider = new CommandProvider();
//
//    private final char paramDelimiter = ' ';
//
//    public String executeTask(String request) {
//        String commandName;
//        Command executionCommand;
//
//        commandName = request.substring(0, request.indexOf(paramDelimiter));
//        executionCommand = provider.getCommand(commandName);
//
//        String response;
//        response = executionCommand.execute(request);
//
//        return response;
//    }
}
