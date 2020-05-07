package AbstractCLI.Commands;


import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Options.Databases.Databases.OptionsDB;
import AbstractCLI.Commands.Options.Databases.Databases.OptionsDB;
import AbstractCLI.Commands.Options.Databases.Interfaces.KeysDatabase;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Options.Databases.Interfaces.OptionsDatabase;
import AbstractCLI.Commands.Parsing.Format1Parser;
import AbstractCLI.Commands.Parsing.KeysParser;
import AbstractCLI.Commands.Parsing.KeysParser.*;
import AbstractCLI.Commands.Parsing.CommandSettings;
import AbstractCLI.IOE;

import java.util.HashMap;

/**
 * Какая идея:
 *  Сперва мы парсим входную строку.
 *  Парсер знает:
 *      1. Входной массив строк
 *      2. Смещение в массиве
 *      3. Базу данных ключей команды
 *      4. Потоки ввода-вывода
 *      5. Имя команды-вызывающего
 *  Это делается автоматным методом
 *  (алгоритм знает какие ключи есть и сколько у них аргументов).
 *  Вывод парсинга -
 *      1. Таблица опций
 *      2. Смещение начала аргументов команды
 *
 *  Потом выполняем.
 *
 *  Выполнение делим на такие этапы:
 *      1. Предварительная обработка опций
 *      2. Обработка самой команды
 *
 *  <DELETE>
 *      Парсинг параметров
 *      Нужен, поскольку опции могут менять параметры, их семантику и/или результат.
 *      Ввод:
 *          - Аргументы (массив строк)
 *          - Смещение начала параметров команды
 *          - База данных примитивов, которая определяет семантику параметров
 *          - Потоки ввода-вывода
 *          - Имя вызывавющей команды
 *
 *      Вывод: Планируется в формате "имя (String) - тип (Class) - значение (Object)"
 *              Список допустимых имён и типов их значений определён в базе примитивов.
 *              База примитивов доступна для всех процедур этапа выполнения
 *  <DELETE/>
 *
 *  Предварительная обработка опций:
 *      Нужна для таких опций как --help, которые делают действия, не предусматривающие
 *      применения основного функционала команды, и штатно прерывают этап основного выполнения.
 *
 *      Ввод:
 *          - Опции
 *          - Базу данных ключей команды
 *          - Потоки ввода-вывода
 *          - Имя команды-вызывающего
 *
 *
 */
public abstract class ParsableCommand extends AbstractCommand {
    KeysParser<String> parser = new Format1Parser<>();
    OptionsDatabase<String> database = new OptionsDB<>();

    public ParsableCommand(String name) { super(name); init(); }

    public ParsableCommand(String name, OptionsDatabase<String> database) {
        super(name);
        this.database = database;
        init();
    }

    public ParsableCommand(String name, String databaseOptions) {
        super(name);
        this.database = OptionsDB.createDB(databaseOptions, new HashMap<>(), System.out, name);
        init();
    }

    public ParsableCommand(String name, String databaseOptions,
                           HashMap<String, OptionHandler<String>> preHandlers) {
        super(name);
        this.database = OptionsDB.createDB(databaseOptions, preHandlers, System.out, name);
        init();
    }

    public ParsableCommand
            (String name, KeysParser<String> parser, OptionsDatabase<String> database) {
        super(name);
        this.parser = parser;
        this.database = database;
        init();
    }

    public ParsableCommand
            (String name, KeysParser<String> parser, String databaseOptions) {
        super(name);
        this.parser = parser;
        this.database = OptionsDB.createDB(databaseOptions, new HashMap<>(), System.out, name);
        init();
    }

    public ParsableCommand
            (String name, KeysParser<String> parser, String databaseOptions,
             HashMap<String, OptionHandler<String>> preHandlers) {
        super(name);
        this.parser = parser;
        this.database = OptionsDB.createDB(databaseOptions, preHandlers, System.out, name);
        init();
    }

    private void init(){
        settings = new CommandSettings<>(name, database, new IOE());
    }

    protected CommandSettings<String> settings;

    @Override
    protected int handle(String[] args, int offset, IOE ioe) throws Exception {
        //Задаём настройки парсера
        this.settings.setIoe(ioe);
        //запускаем парсинг
        KeysParser.ParsingResult<String> result = parser.parse(args, offset, settings);
        int ack = preOperateKeys(args, offset, result, settings);
        if ((ack&Command.FLAG_OPT_FINISH) != 0) return ack;
        else return execute(args, offset, result, settings);
    }

    /**Pre operation acknowledgement*/
    protected class PreOperationAck{
        public boolean interruptCommand = false;
        public int exitCode = Command.ANS_OK;
    }

    protected abstract int preOperateKeys
            (String[] args, int offset,
             ParsingResult<String> parsed,
             CommandSettings<String> settings)
            throws Exception;

    protected abstract int execute(String[] args, int offset,
                                   ParsingResult<String> parsed, CommandSettings<String> settings)
            throws Exception;

}
