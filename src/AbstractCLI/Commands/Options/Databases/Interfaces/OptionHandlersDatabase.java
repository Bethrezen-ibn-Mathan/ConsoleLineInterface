package AbstractCLI.Commands.Options.Databases.Interfaces;

import AbstractCLI.Commands.Handling.OptionHandler;

/**
 * Определяет базу данных для обработчика ключей
 * Обработчик не знает какой ключ за что отвечает.
 * Он только создаёт соответствие между ключём и функцией, которая его обрабатывает.
 * @param <OPTION>
 */
public interface OptionHandlersDatabase<OPTION> {
    OptionHandler<OPTION> getHandler(OPTION option);
}
