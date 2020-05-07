package AbstractCLI.Commands.Templates.Primitives;

import AbstractCLI.Commands.AbstractCommand;
import AbstractCLI.Commands.Command;
import AbstractCLI.IOE;

/**
 * Команда, которая ничего не делает, а только возвращает одно и то же значение.
 * Не имеет ключей и аргументов.
 * Команда "exit" - цепная.
 */
public class SimpleReturnCommand extends AbstractCommand {
    int returned = Command.ANS_OK; // nop

    public SimpleReturnCommand(String name) { super(name); }

    public SimpleReturnCommand(String name, int returned) {
        super(name);
        this.returned = returned;
    }

    @Override
    protected int handle(String[] args, int offset, IOE ioe) { return returned; }
}
