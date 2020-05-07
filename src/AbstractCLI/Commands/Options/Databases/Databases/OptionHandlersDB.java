package AbstractCLI.Commands.Options.Databases.Databases;

import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Options.Databases.Interfaces.OptionHandlersDatabase;
import AbstractCLI.Commands.Parsing.CommandSettings;

import java.util.HashMap;
import java.util.Set;

public class OptionHandlersDB<ID> implements OptionHandlersDatabase<ID> {
    HashMap<ID, OptionHandler<ID>> handlers = new HashMap<>();

    public OptionHandlersDB() { }

    public OptionHandlersDB(HashMap<ID, OptionHandler<ID>> handlers) {
        this.handlers = handlers;
    }

    public static final OptionHandler NO_HANDLER = null;

    @Override
    public OptionHandler<ID> getHandler(ID id) {
        return handlers.getOrDefault(id, NO_HANDLER);
    }

    public boolean hasHandler(ID id){ return handlers.containsKey(id); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<ID> keys = handlers.keySet();
        for (ID id: keys) {
            sb.append(id).append(" : ").append("handled").append(System.lineSeparator());
        }
        return sb.toString();
    }
}
