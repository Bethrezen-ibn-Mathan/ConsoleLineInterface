package AbstractCLI.Commands.Options.Databases.Databases;

import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.KeysDatabase;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Options.Databases.Interfaces.OptionHandlersDatabase;
import AbstractCLI.Commands.Options.Databases.Interfaces.OptionsDatabase;

import java.io.PrintStream;
import java.util.HashMap;

public class OptionsDB<OPTION> implements OptionsDatabase<OPTION> {
    GenericKeysDB<OPTION> keysDatabase = new GenericKeysDB<>();
    OptionHandlersDB<OPTION> handlersDatabase = new OptionHandlersDB<>();

    public OptionsDB() {
    }

    public OptionsDB(
            GenericKeysDB<OPTION> keysDatabase, OptionHandlersDB<OPTION> handlersDatabase
    ) {
        this.keysDatabase = keysDatabase;
        this.handlersDatabase = handlersDatabase;
    }

    //Keys Database - for Parser
    @Override
    public OPTION getOption(char shortName) { return keysDatabase.getOption(shortName); }
    @Override
    public OPTION getOption(String longName) { return keysDatabase.getOption(longName); }
    @Override
    public char getShortName(OPTION option) { return keysDatabase.getShortName(option); }
    @Override
    public String getLongName(OPTION option) { return keysDatabase.getLongName(option); }
    @Override
    public int getArgsCount(OPTION option) { return keysDatabase.getArgsCount(option); }

    //Handlers Database - for Execution
    @Override
    public OptionHandler<OPTION> getHandler(OPTION option) {
        return handlersDatabase.getHandler(option);
    }

    public GenericKeysDB<OPTION> getKeysDatabase() { return keysDatabase; }
    public OptionHandlersDB<OPTION> getHandlersDatabase() { return handlersDatabase; }

    public static OptionsDB<String> createDB(
            String keysConf, HashMap<String, OptionHandler<String>> knowingHandlers,
            PrintStream logStream, String caller){
        GenericKeysDB<String> keysDB = GenericKeysDB.createStringKeyDB(keysConf, logStream, caller);
        OptionHandlersDB<String> handlersDB = new OptionHandlersDB<>(knowingHandlers);
        return new OptionsDB<>(keysDB, handlersDB);
    }

}
