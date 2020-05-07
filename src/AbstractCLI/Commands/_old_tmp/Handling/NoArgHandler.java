package AbstractCLI.Commands._old_tmp.Handling;

import AbstractCLI.Commands._old_tmp.Parsing.OptionValues.OptionState;
import AbstractCLI.IOE;

import java.util.HashMap;
import java.util.List;

/**
 * Обработчик команды, для которой не предусмотрено опций
 */
public interface NoArgHandler extends CommandHandler{
    @Override
    default int handle(HashMap options, List params, IOE ioe) { return handle(); }
    int handle();

    interface NoOptHandler extends CommandHandler{
        @Override
        default int handle(HashMap options, List params, IOE ioe) { return handle(params, ioe); }
        int handle(List<String> params, IOE ioe);
    }

    interface NoParamHandler<ID> extends CommandHandler<ID>{
        @Override
        default int handle(HashMap<ID, OptionState> options, List<String> params, IOE ioe) {
            return handle(options, ioe);
        }
        int handle(HashMap<ID, OptionState> options, IOE ioe);
    }
}
