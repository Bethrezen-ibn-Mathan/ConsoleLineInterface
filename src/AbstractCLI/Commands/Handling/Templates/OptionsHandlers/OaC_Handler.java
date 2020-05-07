package AbstractCLI.Commands.Handling.Templates.OptionsHandlers;

import AbstractCLI.Commands.Command;
import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.CommandHandler;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Parsing.CommandSettings;

import java.util.List;

/**
 * Option as Command: all parameters for option transforms to command's parameters
 */
public class OaC_Handler implements OptionHandler {
    Command command;

    public OaC_Handler(Command command) { this.command = command; }

    @Override
    public int handle(Object o, Option option, CommandSettings settings, CommandData data) {
        List<String> params = option.getValues();
        String[] args = new String[params.size()];
        args = params.toArray(args);
        return command.main(args, 0, settings.getIoe());
    }
}
