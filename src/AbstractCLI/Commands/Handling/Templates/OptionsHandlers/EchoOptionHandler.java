package AbstractCLI.Commands.Handling.Templates.OptionsHandlers;


import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Parsing.CommandSettings;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

/**
 * Print arguments to print stream. Return determined exit code. Do nothing
 */
public class EchoOptionHandler extends ReturnOptionHandler implements OptionHandler {
    PrintStream out = System.out;
    boolean printOption = false;

    public EchoOptionHandler() { }
    public EchoOptionHandler(int returnCode) { super(returnCode); }
    public EchoOptionHandler(PrintStream out) { this.out = out; }
    public EchoOptionHandler(int returnCode, PrintStream out) {
        super(returnCode);
        this.out = out;
    }
    public EchoOptionHandler(boolean printOption) { this.printOption = printOption; }
    public EchoOptionHandler(int returnCode, boolean printOption) {
        super(returnCode);
        this.printOption = printOption;
    }
    public EchoOptionHandler(PrintStream out, boolean printOption) {
        this.out = out;
        this.printOption = printOption;
    }
    public EchoOptionHandler(int returnCode, PrintStream out, boolean printOption) {
        super(returnCode);
        this.out = out;
        this.printOption = printOption;
    }

    @Override
    public int handle(Object o, Option option, CommandSettings settings, CommandData data) {
        String s = (printOption)?o.toString()+" : " : "";
        if (option.getLength()==0){ out.println(s); return returnCode;}
        if (option.getLength()==1){
            out.println(s+option.getValues().get(0));
            return returnCode;
        }
        List<String> arguments = option.getValues();
        s += Arrays.toString(arguments.toArray());
        out.println(s);
        return returnCode;
    }
}
