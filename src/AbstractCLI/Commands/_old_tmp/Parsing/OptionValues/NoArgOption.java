package AbstractCLI.Commands._old_tmp.Parsing.OptionValues;

import java.util.List;

public class NoArgOption extends AbstractOption<Boolean> {
    public NoArgOption(int offset) { super(offset); }
    public Boolean getValue() { return true; }
    public Class getValueClass() { return Boolean.class; }
    public int length() { return 0; }


    public List<String> getValues() {
        return null;
    }


    public int getPosition() {
        return 0;
    }
}
