package AbstractCLI.Commands._old_tmp.Parsing;

import AbstractCLI.Commands._old_tmp.Parsing.OptionValues.OptionState;
import AbstractCLI.IOE;

import java.util.HashMap;
import java.util.List;

public interface ArgsParser<ID> {
    void parse(String[] args, int offset, IOE ioe);
    HashMap<ID, OptionState> getOptions();
    List<String> getParameters();
}
