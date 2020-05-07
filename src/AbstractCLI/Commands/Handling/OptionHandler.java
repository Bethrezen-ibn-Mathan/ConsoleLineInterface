package AbstractCLI.Commands.Handling;

import AbstractCLI.Commands.Parsing.CommandSettings;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;

public interface OptionHandler<ID> {
    int handle(ID id, Option option, CommandSettings<ID> settings, CommandData data);
}
