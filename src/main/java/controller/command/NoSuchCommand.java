package controller.command;

import controller.JspPageName;
import exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class NoSuchCommand implements ICommand{

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return JspPageName.ERROR_PAGE;
    }
}
