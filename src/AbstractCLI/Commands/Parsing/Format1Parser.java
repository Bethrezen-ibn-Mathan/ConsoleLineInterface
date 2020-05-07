package AbstractCLI.Commands.Parsing;

import AbstractCLI.Commands.Options.Databases.Interfaces.KeysDatabase;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;

import java.util.HashMap;

public class Format1Parser<ID> implements KeysParser<ID> {

    /**
     * Парсит по следующим правилам:
     *  1. Взять очередной аргумент
     *  2. Проанализировать первый (опционально - и второй) символы
     *      Если "-" - парсить короткий ключ
     *      Если "--" парсить длинный ключ
     *      Иначе: передать управление обработчику параметров
     *  3. От обработчика получить очередное смещение
     * @param args - строка аргументов
     * @param offset - текущее смещение
     * @param settings - настройки парсинга (ввод/вывод, база данных и т.п.)
     * @return карту опций, список параметров, смещение первого параметра в args
     */
    public ParsingResult<ID> parse(String[] args, int offset, CommandSettings<ID> settings)
            throws Exception{
        HashMap<ID, Option> options = new HashMap<>();
        int i = offset;
        while (i<args.length){
            String arg = args[i];   //1:
            switch (arg.charAt(0)){
                //key
                case '-':{
                    if (arg.length()>1 && arg.charAt(1)=='-'){
                        i = parseLong(options, args, i, settings);
                        break;
                    }else{
                        i = parseShort(options, args, i, settings);
                        break;
                    }
                }
                //parameter for command - end of keys part
                default: return new KeysParser.ParsingResult<ID>(options, i);
            }
        }
        return new KeysParser.ParsingResult<ID>(options, i);
    }

    /**
     * Анализирует короткие ключи
     *  1. Для безаргументных ключей допускается указание вместе
     *      > cmd -abc
     *  2. Для аргументных ключей такой формат недопустим
     *  3. Не допускаются ключи без имени (например, "cmd -")
     * @param options - пул опций (out)
     * @param args - аргументы
     * @param offset - смещение найденного ключа
     * @return следующее смещение после конца анализируемого отрезка
     */
    private int parseShort
    (HashMap<ID, Option> options, String[] args, int offset, CommandSettings<ID> settings)
            throws Exception{
        String arg = args[offset].substring(1); //вырезаем всё что после символа "-"
        if (arg.length() == 0) ERR_emptyKey(args, offset, settings);    //ошибка - пустой ключ

        KeysDatabase<ID> database = settings.getDatabase();  //получаем базу данных

        if (arg.length() == 1){ //задан 1 короткий ключ - допускаются опции
            ID id = requestToDB_SKey(arg, 0, args, offset, settings);
            int argc = database.getArgsCount(id);   //получаем число аргументов для ключа
            Option option = (argc==0)? Option.create(offset) : Option.create(offset, args, argc);
            addKeyToOptions(options, id, option, args, offset, settings);
            return offset+1+argc;   //возврат - позиция сразу же за последним аргументом ключа
        }else{
            for (int i=0; i<arg.length(); i++) {
                ID id = requestToDB_SKey(arg, i, args, offset, settings);
                int argc = database.getArgsCount(id);   //получаем число аргументов для ключа
                if (argc>0) 
                    ERR_argumentableShortKeyInCompactNotation(args, offset, settings, i, argc);
                Option option = Option.create(offset, i);  //булева опция
                addKeyToOptions(options, id, option, args, offset, settings);
            }
            return offset+1;
        }
    }
    
    private ID requestToDB_SKey(String arg, int charnum,
            String[] args, int offset, CommandSettings<ID> settings)
    throws Exception{
        KeysDatabase<ID> database = settings.database;  //получаем базу данных
        char key = arg.charAt(charnum);           //получаем сам ключ
        ID id = database.getOption(key);    //получаем ID опции
        //если нет в базе - ошибка
        if (id == null) ERR_unknownKey(String.valueOf(key), args, offset, settings);
        return id;
    }

    private ID requestToDB_Lkey(String key,
                                String[] args, int offset, CommandSettings<ID> settings)
    throws Exception{
        KeysDatabase<ID> database = settings.database;  //получаем базу данных
        ID id = database.getOption(key);    //получаем ID опции
        //если нет в базе - ошибка
        if (id == null) ERR_unknownKey(String.valueOf(key), args, offset, settings);
        return id;
    }
    
    private void addKeyToOptions(HashMap<ID, Option> options, ID id, Option option, 
                             String[] args, int offset, CommandSettings<ID> settings)
    {
        if (options.containsKey(id)){
            WARN_duplicateKey(settings.database.getLongName(id), args, offset, settings);
            options.replace(id, option);
        }else options.put(id, option);
    }

    /**
     * Анализирует длинные ключи
     *  1. Для безаргументных (булевых) ключей есть такие форматы:
     *      > cmd --key
     *      > cmd --key=<something>
     *          ВНИМАНИЕ! Даже если в ключе указано 0, false или ещё какой-либо аргумент,
     *          предполагающий его не-срабатывание, опция будет создана и передана. И
     *          обработчик, сопоставив требуемую длину и реальную, может как проигнорировать
     *          аргумент так и обработать его.
     *  2. Для аргументных ключей формат такой:
     *      > cmd --key=arg1,arg2,arg3
     *  3. Не допускаются ключи без имени (например, "cmd --")
     *  4. Допускается несовпадение числа аргументов.
     *  Все вопросы касательно дополнительных/недостающих аргументов полагаются на обработчик.
     *  По умолчанию доп.аргументы будут проигнорированы, недостающие - приведут к семантической
     *  ошибке на этапе выполнения.
     *  ПОЭТОМУ ВНИМАНИЕ! ДЛИННЫЙ КЛЮЧ СИНТАКСИЧЕСКИ НЕ ПРОВЕРЯЕТ СООТВЕТСТВИЕ ЧИСЛА АРГУМЕНТОВ!
     *  (не баг, а фича для реализации длинных ключей переменной длины)
     *  5. Аргументы для ключей НЕ ДОЛЖНЫ содержать символы табуляции, знаки "=", "-" или ","
     * @param options - пул опций (out)
     * @param args - аргументы
     * @param offset - смещение найденного ключа
     * @return следующее смещение после конца анализируемого отрезка
     */
    private int parseLong
            (HashMap<ID, Option> options, String[] args, int offset, CommandSettings<ID> settings)
            throws Exception
    {
        String arg = args[offset].substring(2);

        if (arg.contains("=")){ //проверка на то, есть ли фактически аргумент
            //есть фактические аргументы
            String[] split = arg.split("=");
            String key = split[0];
            ID id = requestToDB_Lkey(key, args, offset, settings);
            split = split[1].split(",");
            Option option = Option.create(offset, split);
            addKeyToOptions(options, id, option, args, offset, settings);
        }else{  //нет фактических аргументов - булева опция
            ID id = requestToDB_Lkey(arg, args, offset, settings);
            Option option = Option.create(offset);
            addKeyToOptions(options, id, option, args, offset, settings);
        }
        return offset+1;
    }

    private void ERR_emptyKey
            (String[] args, int offset, CommandSettings<ID> settings)
            throws Exception
    {
        throw new Exception("Arg # "+offset+": empty key detected: "+args[offset]
                + " for command "+settings.owner);
    }

    private void ERR_unknownKey
            (String key, String[] args, int offset, CommandSettings<ID> settings)
            throws Exception
    {
        throw new Exception("Arg # "+offset+": unknown key detected: "+key
                + ", token:"+args[offset]+" for command "+settings.owner);
    }

    private void WARN_duplicateKey
            (String key, String[] args, int offset, CommandSettings<ID> settings)
    {
        settings.ioe.printWarning("Arg # "+offset+": duplicated option detected: "+key
                +", token: "+args[offset]+ " for command "+settings.owner
                +"\nSolution: rewrite option state");
    }

    private void ERR_argumentableShortKeyInCompactNotation
            (String[] args, int offset, CommandSettings<ID> settings, int subpos, int argsCount)
            throws Exception
    {
        throw new Exception("Arg # "+offset+
                ": key with argument in compact notation. Argument: "+args[offset]+
                ", keypos: "+subpos+", keychar: "+args[offset].charAt(subpos)+
                ", arguments required: "+argsCount+", for command "+settings.owner);
    }
}

