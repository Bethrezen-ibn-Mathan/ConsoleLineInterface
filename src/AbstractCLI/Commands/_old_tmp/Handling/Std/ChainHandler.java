package AbstractCLI.Commands._old_tmp.Handling.Std;

import AbstractCLI.Commands.Command;

import java.util.HashMap;

/**
 * Обработчик, который просто передаёт управление другому обработчику команды
 * Нужен для субкоммандинга по опциям.
 */
public class ChainHandler<ID>  {
    HashMap<ID, Command> subCommands = new HashMap<>();
    Command byDefault;

    public ChainHandler(HashMap<ID, Command> subCommands, Command byDefault) {
        this.subCommands = subCommands;
        this.byDefault = byDefault;
    }


}
