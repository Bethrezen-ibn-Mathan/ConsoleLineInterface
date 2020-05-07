package AbstractCLI.Commands.Handling.Templates.OptionsHandlers;

import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Parsing.CommandSettings;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Call some other handlers in determined order
 */
public class StackedOptionHandler implements OptionHandler {
    List<OptionHandler> handlersStack = new LinkedList<>();
    public StackedOptionHandler() { }
    public StackedOptionHandler(List<OptionHandler> handlersStack) {
        this.handlersStack = handlersStack;
    }
    public StackedOptionHandler(OptionHandler... handlers) {
        this.handlersStack.addAll(Arrays.asList(handlers));
    }

    @Override
    public int handle(Object o, Option option, CommandSettings settings, CommandData data) {
        int retCode = 0;
        for (OptionHandler handler:handlersStack) {
            retCode |= handler.handle(o, option, settings, data);
        }
        return retCode;
    }
}
