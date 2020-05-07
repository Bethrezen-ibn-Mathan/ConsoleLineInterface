package AbstractCLI.Commands.Handling;

import AbstractCLI.AbstractCLI;
import com.sun.istack.internal.NotNull;

public class CommandData {
    String[] args;
    int offset;

    public CommandData(@NotNull String[] args, int offset) {
        this.args = args;
        this.offset = offset;
    }

    public String[] getArgs() { return args; }
    public int getOffset() { return offset; }
}
