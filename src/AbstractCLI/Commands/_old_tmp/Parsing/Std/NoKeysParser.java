package AbstractCLI.Commands._old_tmp.Parsing.Std;

import AbstractCLI.Commands._old_tmp.Parsing.ArgsParser;
import AbstractCLI.Commands._old_tmp.Parsing.OptionsCreator;
import AbstractCLI.Commands._old_tmp.Parsing.OptionsTable;
import AbstractCLI.IOE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NoKeysParser<OID> extends OptionsCreator<OID> implements ArgsParser<OID>{


    public NoKeysParser(String callerName) {
        super(callerName, new OptionsTable());
    }

    /**
     * Для такой команды ключи не предусмотрены в принципе
     * А вот аргументы могут быть
     * @param args - аргументы
     * @param offset - смещение
     */
    @Override
    public void parse(String[] args, int offset, IOE ioe) {
        ArrayList<String> params = new ArrayList<>();
        if (args.length - offset > 1)
            params.addAll(Arrays.asList(args).subList(offset + 1, args.length));
        this.options = new HashMap();
    }
}
