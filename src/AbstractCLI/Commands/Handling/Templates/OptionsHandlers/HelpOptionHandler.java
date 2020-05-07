package AbstractCLI.Commands.Handling.Templates.OptionsHandlers;

import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Parsing.CommandSettings;

import java.io.PrintStream;

/**
 * Print determined information to stream. Return determined code. Do nothing
 */
public class HelpOptionHandler extends ReturnOptionHandler implements OptionHandler {
    String man = "Help option enabled!";
    PrintStream out = System.out;

    public HelpOptionHandler() { }
    public HelpOptionHandler(String man) { this.man = man; }
    public HelpOptionHandler(String man, PrintStream out) {
        this.man = man;
        this.out = out;
    }
    public HelpOptionHandler(String man, int returnCode) {
        super(returnCode);
        this.man = man;
    }

    public HelpOptionHandler(String man, PrintStream out, int returnCode) {
        super(returnCode);
        this.man = man;
        this.out = out;
        this.returnCode = returnCode;
    }

    @Override
    public int handle(Object o, Option option, CommandSettings settings, CommandData data) {
        out.println(man);
        return returnCode;
    }
}


