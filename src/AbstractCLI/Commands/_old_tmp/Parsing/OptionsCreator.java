package AbstractCLI.Commands._old_tmp.Parsing;

import AbstractCLI.IOE;

import java.util.HashMap;
import java.util.List;

public abstract class OptionsCreator<ID> implements ArgsParser<ID> {
    protected String callerName;
    protected OptionsTable<ID> table;
    protected IOE ioe = new IOE();
    
    protected HashMap options;
    protected List<String> parameters;

    public OptionsCreator(String callerName, OptionsTable<ID> table) {
        this.callerName = callerName;
        this.table = table;
    }

    /**@param args - аргументы
     * @param offset - смещение
     */
    public abstract void parse(String[] args, int offset, IOE ioe);

    @Override
    public HashMap getOptions() { return options; }
    public List<String> getParameters() { return parameters; }

    protected void addWarning(String text, String solution){
        ioe.err.println("[WARN] "+text+" for command /"+callerName+". "+solution);
    }
}



