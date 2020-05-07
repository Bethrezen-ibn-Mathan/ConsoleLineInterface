package AbstractCLI.Commands.Handling.Templates.CommandHandlers;

import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.CommandHandler;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Parsing.CommandSettings;
import AbstractCLI.Commands.Parsing.KeysParser;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Command as Option Handler
 * All parameters of command converts to parameters of option
 * Name of option == "CaO"+name of command
 */
public class CaO_Handler implements CommandHandler {
    OptionHandler<String> handler;

    @Override
    public int execute(CommandData data, KeysParser.ParsingResult<String> parsed, CommandSettings<String> settings) throws Exception {
        String[] args = data.getArgs();
        Option option = Option.create
                (0, Arrays.copyOfRange(args, parsed.getParamOffset(), args.length));
        return handler.handle("CaO_"+settings.getOwner(), option, settings, data);
    }
}
