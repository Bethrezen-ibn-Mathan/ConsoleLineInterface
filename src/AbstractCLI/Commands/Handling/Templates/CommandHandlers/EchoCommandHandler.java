package AbstractCLI.Commands.Handling.Templates.CommandHandlers;

import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.CommandHandler;
import AbstractCLI.Commands.Parsing.CommandSettings;
import AbstractCLI.Commands.Parsing.KeysParser;

import java.io.PrintStream;
import java.util.Arrays;

public class EchoCommandHandler extends PrintableCommandHandler implements CommandHandler {
    boolean printName = false;

    public EchoCommandHandler() { }
    public EchoCommandHandler(int returnCode) { super(returnCode); }
    public EchoCommandHandler(PrintStream out) { super(out); }
    public EchoCommandHandler(int returnCode, PrintStream out) { super(returnCode, out); }
    public EchoCommandHandler(boolean printName) { this.printName = printName; }
    public EchoCommandHandler(int returnCode, boolean printName) {
        super(returnCode);this.printName = printName;
    }
    public EchoCommandHandler(PrintStream out, boolean printName) {
        super(out);this.printName = printName;
    }
    public EchoCommandHandler(int returnCode, PrintStream out, boolean printName) {
        super(returnCode, out);this.printName = printName;
    }


    @Override
    public int execute(CommandData data, KeysParser.ParsingResult<String> parsed, CommandSettings<String> settings) throws Exception {
        String[] args = data.getArgs();
        String[] param = Arrays.copyOfRange(args, parsed.getParamOffset(), args.length);
        String s = Arrays.toString(param);
        if (printName) s = settings.getOwner()+" : "+s;
        out.println(s);
        return super.execute(data, parsed, settings);
    }
}
