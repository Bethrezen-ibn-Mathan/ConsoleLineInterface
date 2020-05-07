package AbstractCLI.Commands;

import AbstractCLI.Commands.Handling.CommandData;
import AbstractCLI.Commands.Handling.CommandHandler;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Databases.OptionsDB;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Options.Databases.Interfaces.OptionsDatabase;
import AbstractCLI.Commands.Parsing.CommandSettings;
import AbstractCLI.Commands.Parsing.KeysParser;

import java.util.*;

public class GenericCommand extends ParsableCommand {
    public interface PreOperator{
        int preOperateKeys(String[] args, int offset,
            KeysParser.ParsingResult<String> parsed,
            CommandSettings<String> settings) throws Exception;
    }

    PreOperator preOperator = GenericCommand::PreOperate;
    CommandHandler handler;

    //--------------------------------------------------------------
    //CONSTRUCTORS

    public GenericCommand(String name, CommandHandler handler) {
        super(name);
        this.handler = handler;
    }

    public GenericCommand(String name, OptionsDatabase<String> database, CommandHandler handler) {
        super(name, database);
        this.handler = handler;
    }

    public GenericCommand(String name, String databaseOptions, CommandHandler handler) {
        super(name, databaseOptions);
        this.handler = handler;
    }

    public GenericCommand(String name, String databaseOptions, HashMap<String, OptionHandler<String>> preHandlers, CommandHandler handler) {
        super(name, databaseOptions, preHandlers);
        this.handler = handler;
    }

    public GenericCommand(String name, KeysParser<String> parser, OptionsDatabase<String> database, CommandHandler handler) {
        super(name, parser, database);
        this.handler = handler;
    }

    public GenericCommand(String name, KeysParser<String> parser, String databaseOptions, CommandHandler handler) {
        super(name, parser, databaseOptions);
        this.handler = handler;
    }

    public GenericCommand(String name, KeysParser<String> parser, String databaseOptions, HashMap<String, OptionHandler<String>> preHandlers, CommandHandler handler) {
        super(name, parser, databaseOptions, preHandlers);
        this.handler = handler;
    }

    public GenericCommand(String name, PreOperator preOperator, CommandHandler handler) {
        super(name);
        this.preOperator = preOperator;
        this.handler = handler;
    }

    public GenericCommand(String name, OptionsDatabase<String> database, PreOperator preOperator, CommandHandler handler) {
        super(name, database);
        this.preOperator = preOperator;
        this.handler = handler;
    }

    public GenericCommand(String name, String databaseOptions, PreOperator preOperator, CommandHandler handler) {
        super(name, databaseOptions);
        this.preOperator = preOperator;
        this.handler = handler;
    }

    public GenericCommand(String name, String databaseOptions, HashMap<String, OptionHandler<String>> preHandlers, PreOperator preOperator, CommandHandler handler) {
        super(name, databaseOptions, preHandlers);
        this.preOperator = preOperator;
        this.handler = handler;
    }

    public GenericCommand(String name, KeysParser<String> parser, OptionsDatabase<String> database, PreOperator preOperator, CommandHandler handler) {
        super(name, parser, database);
        this.preOperator = preOperator;
        this.handler = handler;
    }

    public GenericCommand(String name, KeysParser<String> parser, String databaseOptions, PreOperator preOperator, CommandHandler handler) {
        super(name, parser, databaseOptions);
        this.preOperator = preOperator;
        this.handler = handler;
    }

    public GenericCommand(String name, KeysParser<String> parser, String databaseOptions, HashMap<String, OptionHandler<String>> preHandlers, PreOperator preOperator, CommandHandler handler) {
        super(name, parser, databaseOptions, preHandlers);
        this.preOperator = preOperator;
        this.handler = handler;
    }


    //--------------------------------------------------------------
    //METHODS

    @Override
    protected int preOperateKeys(
            String[] args, int offset,
            KeysParser.ParsingResult<String> parsed,
            CommandSettings<String> settings) throws Exception {
        return preOperator.preOperateKeys(args, offset, parsed, settings);
    }

    @Override
    protected int execute(
            String[] args, int offset,
            KeysParser.ParsingResult<String> parsed,
            CommandSettings<String> settings) throws Exception
    {
        return handler.execute(new CommandData(args, offset), parsed, settings);
    }

    //-----------------------------------------------------------------
    //STATIC METHODS

    //просто идёт по опциям и вызывает обработчики.
    //порядок - произвольный
    public static int PreOperate(
            String[] args, int offset,
            KeysParser.ParsingResult<String> parsed,
            CommandSettings<String> settings) throws Exception {
        HashMap<String, Option> options = parsed.getOptions();
        OptionsDatabase<String> db = settings.getDatabase();
        int result = Command.ANS_OK;
        CommandData input = new CommandData(args, offset);
        Set<String> keys = options.keySet();
        for (String option:keys) {
            OptionHandler<String> handler = db.getHandler(option);
            result |= handler.handle(option, options.get(option), settings, input);
        }
        return result;
    }

    //просматривает обработчики в порядке приоритета.
    //Всё что не содержится в списке "приоритет" обрабатывается в произвольном порядке
    public class ExtPreOp implements PreOperator{
        List<String> priority = new LinkedList<>();
        public ExtPreOp() { }
        public ExtPreOp(List<String> priority) { this.priority = priority; }
        public ExtPreOp(String... priority){this.priority.addAll(Arrays.asList(priority));}
        @Override
        public int preOperateKeys(String[] args, int offset,
                                  KeysParser.ParsingResult<String> parsed,
                                  CommandSettings<String> settings) throws Exception {
            HashMap<String, Option> options = parsed.getOptions();
            OptionsDatabase<String> db = settings.getDatabase();
            int result = Command.ANS_OK;
            CommandData input = new CommandData(args, offset);
            Set<String> keys = options.keySet();
            for (String option:priority) {
                if (!keys.contains(option)) continue;
                OptionHandler<String> handler = db.getHandler(option);
                result &= handler.handle(option, options.get(option), settings, input);
                keys.remove(option);
            }
            for (String option:keys) {
                OptionHandler<String> handler = db.getHandler(option);
                result &= handler.handle(option, options.get(option), settings, input);
            }
            return result;
        }
    }
}
