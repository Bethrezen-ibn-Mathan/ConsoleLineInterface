package AbstractCLI.Commands.Handling.Templates.OptionsHandlers;

import AbstractCLI.Commands.Command;
import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Parsing.CommandSettings;


/**
 * Return code. Do nothing.
 */
public class ReturnOptionHandler implements OptionHandler {
    int returnCode = Command.ANS_OK;
    public ReturnOptionHandler() { }
    public ReturnOptionHandler(int returnCode) { this.returnCode = returnCode; }

    @Override
    public int handle(Object o, Option option, CommandSettings settings, CommandData data) {
        return returnCode;
    }
}
