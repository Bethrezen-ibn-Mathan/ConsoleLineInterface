package AbstractCLI.Commands.Templates.Primitives;

import AbstractCLI.Commands.AbstractCommand;
import AbstractCLI.Commands.Command;
import AbstractCLI.IOE;
import com.sun.istack.internal.NotNull;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Handler;

/**
 * Цепная команда - такая, что передаёт управление другим командам.
 * Имеет пул команд, каждой соответствует своё имя.
 * Если команда в пуле имеет синонимы то необходимо все синонимы вносить в пул тоже.
 * Есть команда "по умолчанию". Ей передаётся управление если в пуле соответствия не найдено.
 * Команду "по умолчанию" можно использовать как для обработки ключей самой "цепной" команда
 * так и для любых других целей
 *
 * "Корневая команда" интерфейса - цепная.
 */
public class SimpleChainCommand extends AbstractCommand {
    HashMap<String, Command> pool = new HashMap<>();
    Command byDefault;
    boolean printErr = false;


    public SimpleChainCommand(String name) {
        super(name);
    }

    public SimpleChainCommand(String name, HashMap<String, Command> pool, Command byDefault) {
        super(name);
        this.pool = pool;
        this.byDefault = byDefault;
    }

    public SimpleChainCommand(String name, HashMap<String, Command> pool, Command byDefault, boolean printErr) {
        super(name);
        this.pool = pool;
        this.byDefault = byDefault;
        this.printErr = printErr;
    }

    @Override
    public int handle(@NotNull String[] args, int offset, @NotNull IOE ioe) {
        String key = args[offset];
        Command handler = byDefault;
        if (!printErr){
            handler = pool.getOrDefault(key, byDefault);
        }else{
            if (!pool.containsKey(key)) ioe.printWarning(createWarn(name, offset, key));
            else handler = pool.get(key);
        }
        return handler.main(args, offset+1, ioe);
    }



    private static String createWarn(String cname, int offset, String token){
        return cname+": Arg #"+offset+". Token not found in map: "+token;
    }
}
