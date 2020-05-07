package AbstractCLI.Commands.Handling.Templates.CommandHandlers;

import AbstractCLI.Commands.Command;
import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.CommandHandler;
import AbstractCLI.Commands.Parsing.CommandSettings;
import AbstractCLI.Commands.Parsing.KeysParser;

public class ReturnCommandHandler implements CommandHandler {
    int returnCode = Command.ANS_OK;

    public ReturnCommandHandler() { }
    public ReturnCommandHandler(int returnCode) { this.returnCode = returnCode; }

    @Override
    public int execute(CommandData data, KeysParser.ParsingResult<String> parsed, CommandSettings<String> settings) throws Exception {
        return returnCode;
    }
}
