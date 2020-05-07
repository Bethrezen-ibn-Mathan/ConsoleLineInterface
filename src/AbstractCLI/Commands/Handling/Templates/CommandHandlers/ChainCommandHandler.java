package AbstractCLI.Commands.Handling.Templates.CommandHandlers;

import AbstractCLI.Commands.Command;
import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.CommandHandler;
import AbstractCLI.Commands.Parsing.CommandSettings;
import AbstractCLI.Commands.Parsing.KeysParser;
import AbstractCLI.IOE;

import java.util.HashMap;

public class ChainCommandHandler implements CommandHandler {
    HashMap<String, Command> pool = new HashMap<>();
    Command byDefault;
    boolean printErr = false;

    public ChainCommandHandler(Command byDefault) {
        this.byDefault = byDefault;
    }

    public ChainCommandHandler(HashMap<String, Command> pool, Command byDefault) {
        this.pool = pool;
        this.byDefault = byDefault;
    }

    public ChainCommandHandler(HashMap<String, Command> pool, Command byDefault, boolean printErr) {
        this.pool = pool;
        this.byDefault = byDefault;
        this.printErr = printErr;
    }

    @Override
    public int execute(CommandData data, KeysParser.ParsingResult<String> parsed, CommandSettings<String> settings) throws Exception {
        String[] args = data.getArgs();             //args
        int paramOffset = parsed.getParamOffset();  //first arg
        IOE ioe = settings.getIoe();
        String key;
        try {
            key = args[paramOffset];
        }catch (IndexOutOfBoundsException e){
            ioe.printError(settings.getOwner()+": 1 or more parameters expected, but not found");
            return Command.FLAG_EXCEPTION;
        }
        if (!printErr){
            return pool.getOrDefault(key, byDefault).main(args, paramOffset, ioe);
        }else{
            if (!pool.containsKey(key)){
                ioe.printWarning(createWarn(settings.getOwner(), paramOffset, key));
                return byDefault.main(args, paramOffset, ioe);
            }
            return pool.get(key).main(args, paramOffset, ioe);
        }
    }

    private static String createWarn(String cname, int offset, String token){
        return cname+": Arg #"+offset+". Token not found in map: "+token;
    }
}
