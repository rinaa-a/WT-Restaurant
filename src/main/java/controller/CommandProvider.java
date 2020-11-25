package controller;

import controller.command.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private final Map<CommandName, ICommand> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.SIGN_IN, new SignIn());
        commands.put(CommandName.REGISTRATION, new Register());
        commands.put(CommandName.ADD_DISH, new AddDish());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        //...

    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public ICommand getCommand(String commandName) {
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        ICommand command;

        if (null != name) {
            command = commands.get(name);
        } else {
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return command;
    }
}
