package AbstractCLI.Commands._old_tmp.Handling.Std;

import AbstractCLI.Commands._old_tmp.Handling.NoArgHandler;

import java.io.PrintStream;

public class PrintHandler<T> implements NoArgHandler {
    PrintStream dest = System.out;
    T printed;

    public PrintHandler(T printed) { this.printed = printed; }
    public PrintHandler(PrintStream dest, T printed) {
        this.dest = dest;this.printed = printed;
    }

    @Override
    public int handle() {
        dest.println(printed);
        return 0;
    }
}
