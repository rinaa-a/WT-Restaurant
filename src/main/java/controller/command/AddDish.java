package controller.command;

import exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class AddDish implements ICommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
