package AbstractCLI.Commands.Handling.Templates.CommandHandlers;

import AbstractCLI.Commands.Handling.CommandHandler;

import java.io.PrintStream;

public abstract class PrintableCommandHandler extends ReturnCommandHandler implements CommandHandler {
    protected PrintStream out = System.out;

    public PrintableCommandHandler() { }
    public PrintableCommandHandler(int returnCode) { super(returnCode); }
    public PrintableCommandHandler(PrintStream out) { this.out = out; }
    public PrintableCommandHandler(int returnCode, PrintStream out) {
        super(returnCode);
        this.out = out;
    }
}
