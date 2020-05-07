package AbstractCLI.Commands._old_tmp;

import AbstractCLI.Commands.AbstractCommand;
import AbstractCLI.Commands._old_tmp.Handling.CommandHandler;
import AbstractCLI.Commands._old_tmp.Parsing.ArgsParser;
import AbstractCLI.Commands._old_tmp.Parsing.OptionValues.OptionState;
import AbstractCLI.Commands._old_tmp.Parsing.Std.NoKeysParser;
import AbstractCLI.IOE;

import java.util.HashMap;
import java.util.List;

public class GenericCommand<OID> extends AbstractCommand {
    String name;
    IOE ioe = new IOE();
    ArgsParser<OID> parser;
    CommandHandler<OID> handler;

    public GenericCommand(String name, ArgsParser<OID> parser, CommandHandler<OID> handler) {
        super(name);
        this.parser = parser;
        this.handler = handler;
    }

    public GenericCommand(String name, CommandHandler<OID> handler) {
        super(name);
        this.parser = new NoKeysParser<>(name);
        this.handler = handler;
    }

    @Override
    public int handle(String[] args, int offset, IOE ioe) {
        this.ioe = ioe;
        parser.parse(args, offset, ioe);
        HashMap<OID, OptionState> options = parser.getOptions();
        List<String> params = parser.getParameters();
        return handler.handle(options, params, ioe);
    }
}


