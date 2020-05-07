package AbstractCLI.Commands.Parsing;

import AbstractCLI.Commands.Options.Databases.Interfaces.KeysDatabase;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Options.Databases.Interfaces.OptionHandlersDatabase;
import AbstractCLI.Commands.Options.Databases.Interfaces.OptionsDatabase;
import AbstractCLI.IOE;
import com.sun.istack.internal.NotNull;

public class CommandSettings<OPTID>{
    @NotNull
    String owner;
    @NotNull
    OptionsDatabase<OPTID> database;
    OptionHandlersDatabase<OPTID> handlersDatabase;
    IOE ioe;

    public CommandSettings(String owner, OptionsDatabase<OPTID> database, IOE ioe) {
        this.owner = owner;
        this.database = database;
        this.ioe = ioe;
    }

    public String getOwner() { return owner; }
    public OptionsDatabase<OPTID> getDatabase() { return database; }
    public IOE getIoe() { return ioe; }

    public void setOwner(String owner) { this.owner = owner; }
    public void setDatabase(OptionsDatabase<OPTID> database) {
        this.database = database;
    }
    public void setIoe(IOE ioe) { this.ioe = ioe; }
}
