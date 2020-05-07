package AbstractCLI.Commands._old_tmp.Parsing;

import AbstractCLI.Commands._old_tmp.Parsing.OptionValues.Option;
import AdditionalClasses.Box;
import AbstractCLI.Commands.Command;
import AbstractCLI.Commands._old_tmp.Parsing.OptionValues.OptionState;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.HashMap;

public class OptionsTable<ID> {
    public interface OptionParser {
        OptionState parse(String[] args, int offset, Box<Integer> newOffset);
    }

    protected HashMap<String, ID> names = new HashMap<>();
    protected HashMap<ID, OptionParser> parsers = new HashMap<>();
    protected HashMap<ID, OptionParser> parsersLong = new HashMap<>();
    protected HashMap<ID, Command> subCommands = new HashMap<>();
    //protected HashMap<ID, Command> handlers = new HashMap<>();


    public OptionsTable() { }

    public OptionsTable(
            HashMap<String, ID> names,
            HashMap<ID, OptionParser> parsers,
            HashMap<ID, OptionParser> parsersLong,
            HashMap<ID, Command> subCommands
    ) {
        this.names = names;
        this.parsers = parsers;
        this.parsersLong = parsersLong;
        this.subCommands = subCommands;
        //this.handlers = handlers;
    }

    //-------------------------------------------------------
    //getters/setters

    public HashMap<String, ID> getNames() { return names; }
    public HashMap<ID, OptionParser> getParsers() { return parsers; }
    public HashMap<ID, OptionParser> getParsersLong() { return parsersLong; }
    public HashMap<ID, Command> getSubCommands() { return subCommands; }
    //------------------------------------------------------

    @Nullable
    public ID getId(String option){ return names.getOrDefault(option, null); }

    public OptionParser getParser(@NotNull ID id){ return parsers.get(id); }

    public OptionParser getParserLong(@NotNull ID id){return parsersLong.get(id);}

    public Command getSubCommand(@NotNull ID id){return subCommands.get(id);}

    /**
     * Значение опции для подкоманды - команда-обработчик, которой должно передаваться управление
     * @param offset - смещение (индекс после имени команды среди аргументов)
     * @return опцию для команды
     */
    public OptionState<Command> createOptionForSubCmd(ID id, int offset){
        return new Option<Command>(getSubCommand(id), 1, offset);
    }
}
