package AbstractCLI.Commands._old_tmp.Handling;

import AbstractCLI.Commands._old_tmp.Parsing.OptionValues.OptionState;
import AbstractCLI.IOE;

import java.util.HashMap;
import java.util.List;

/**
 * Обработчик некой команды
 * @param <ID> - идентификатор опции. Как идентифицировать опции - решается в реализации.
 *            Главное - что у каждой опции конкретной команды есть свой идентификатор
 */
public interface CommandHandler<ID> {
    int handle(HashMap<ID, OptionState> options, List<String> params, IOE ioe);
}
