package AbstractCLI.Commands._old_tmp.Handling.Std;

import AbstractCLI.Commands.Command;
import AbstractCLI.Commands._old_tmp.Handling.CommandHandler;
import AbstractCLI.Commands._old_tmp.Parsing.OptionValues.OptionState;
import AbstractCLI.IOE;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Обработчик, который печатает свои опции и параметры.
 * @param <ID>
 */
public class ParsedEchoHandler<ID> implements CommandHandler<ID> {

    @Override
    public int handle(HashMap<ID, OptionState> options, List<String> params, IOE ioe) {
        StringBuilder sb = new StringBuilder("Options:\n");
        Set<ID> opts = options.keySet();
        for (ID opt:opts) {
            sb.append("\t").append(opt).append(" ").append(options.get(opt)).append("\n");
        }
        sb.append("Arguments:\n");
        for (String arg:params) {
            sb.append("\t").append(arg).append("\n");
        }
        ioe.printMessage(sb.toString());
        return Command.ANS_OK;
    }
}
