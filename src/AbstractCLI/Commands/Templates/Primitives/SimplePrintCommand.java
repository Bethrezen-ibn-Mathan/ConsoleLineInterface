package AbstractCLI.Commands.Templates.Primitives;

import AbstractCLI.Commands.AbstractCommand;
import AbstractCLI.Commands.Command;
import AbstractCLI.IOE;

import java.util.HashMap;

/**
 * Команда, используемая для вывода состояния некоего объекта в поток вывода.
 * Принимает ключи, по ним ищет в карте объект и выводит, используя toString().
 * Имеет сообщение "по умолчанию", которое выводит, если объекта не найдено.
 * Имеет сообщение на случай, если смещение будет больше чем число аргументов
 */
public class SimplePrintCommand extends AbstractCommand {
    HashMap<String, Object> messages = new HashMap<>();
    String byDefault = "[INF] "+getName()+": Data not found for token: ";
    String outOfArgs = "[INF] "+getName()+": Argument expectedm but not found";

    public SimplePrintCommand(String name, HashMap<String, Object> messages) {
        super(name);
        this.messages = messages;
    }

    public SimplePrintCommand(String name, HashMap<String, Object> messages, String byDefault) {
        super(name);
        this.messages = messages;
        this.byDefault = byDefault;
    }

    public SimplePrintCommand(String name, HashMap<String, Object> messages, String byDefault, String outOfArgs) {
        super(name);
        this.messages = messages;
        this.byDefault = byDefault;
        this.outOfArgs = outOfArgs;
    }

    /**
     * При выходе смещения за границу массива аргументов команда делает попытку обращения к пулу
     * и ищет там своё имя. если нашла - печатает данные о себе. Если нет - печатает стандартное
     * сообщение о том, что ожидался аргумент.
     * Иначе
     * @param args - аргументы
     * @param offset - индекс начала команды
     * @param ioe - потоки для ввода, вывода и ошибки
     * @return сообщение
     */
    @Override
    protected int handle(String[] args, int offset, IOE ioe) {
        Object printed;
        if (offset>=args.length){
            printed = messages.getOrDefault(name, outOfArgs);
        }else {
            printed = messages.getOrDefault(args[offset], byDefault + args[offset]);
        }
        ioe.printMessage(printed.toString());
        return Command.ANS_OK;
    }
}
