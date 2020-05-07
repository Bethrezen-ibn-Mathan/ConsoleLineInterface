package AbstractCLI.Commands.Handling;

import AbstractCLI.Commands.Parsing.CommandSettings;
import AbstractCLI.Commands.Parsing.KeysParser;

public interface CommandHandler {
    int execute(CommandData data,                           //входные данные
                KeysParser.ParsingResult<String> parsed,    //результат парсинга
                CommandSettings<String> settings)           //настройки команды
            throws Exception;
}
