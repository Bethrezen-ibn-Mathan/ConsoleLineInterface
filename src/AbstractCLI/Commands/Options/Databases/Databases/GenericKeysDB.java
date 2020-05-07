package AbstractCLI.Commands.Options.Databases.Databases;


import AbstractCLI.Commands.Options.Databases.Interfaces.KeysDatabase;

import java.io.PrintStream;
import java.util.*;

public class GenericKeysDB<OPTION> implements KeysDatabase<OPTION> {
    public static class Record<OPTION>{
        public OPTION option;
        public char shortName = 0;
        public String longName = null;
        public int argc = 0;
        public Record(OPTION option, char shortName, String longName, int argc) {
            this.option = option;
            this.shortName = shortName;
            this.longName = longName;
            this.argc = argc;
        }
        @Override
        public String toString() { return option+" : "+longName+" "+shortName+" "+argc; }
    }

    HashMap<Character, Record<OPTION>> shortKeys = new HashMap<>();
    HashMap<String, Record<OPTION>> longKeys = new HashMap<>();
    HashMap<OPTION, Record<OPTION>> options = new HashMap<>();

    //defaults
    public static final char NO_S_KEY = KeysDatabase.NO_S_KEY;
    public static final String NO_L_KEY = KeysDatabase.NO_L_KEY;
    public static final int NO_ARGS = KeysDatabase.NO_ARGS;
    public final Record<OPTION> EMPTY = new Record(null, NO_S_KEY, NO_L_KEY, NO_ARGS);

    //пустая база данных
    public GenericKeysDB() { }

    //заполненная база данных. Повторяющиеся/некорректные ключи игнорируются
    public GenericKeysDB(Record<OPTION>[] records) { for (Record rec:records) insert(rec); }

    //--------------------------------------
    //DATABASE METHODS

    /**
     * @param option - ID
     * @param shortName - short key
     * @param longName - long key
     * @param argc - count of arguments for option
     * @return record for database
     */
    public Record createRecord(OPTION option, char shortName, String longName, int argc){
        if (options.containsKey(option)) return null;
        return new Record<>(option, shortName, longName, argc);
    }

    public boolean insert(OPTION option, char shortName, String longName, int argc){
        if (options.containsKey(option)) return false;
        Record<OPTION> rec = new Record<>(option, shortName, longName, argc);
        options.put(option, rec);
        if (!Objects.equals(longName, NO_L_KEY)) longKeys.put(longName, rec);
        if (shortName != NO_S_KEY) shortKeys.put(shortName, rec);
        return true;
    }

    private boolean insert(Record<OPTION> record){
        if (options.containsKey(record.option)) return false;
        if (record.option==null) return false;
        options.put(record.option, record);
        if (!Objects.equals(record.longName, NO_L_KEY)) longKeys.put(record.longName, record);
        if (record.shortName != NO_S_KEY) shortKeys.put(record.shortName, record);
        return true;
    }

    public Record set(OPTION option, char shortName, String longName, int argc){
        Record<OPTION> record = null;
        if (options.containsKey(option)) record = remove(option);
        boolean success = insert(option, shortName, longName, argc);
        if (!success&&record!=null) insert(record);
        return record;
    }

    public Record<OPTION> select(OPTION option){
        if (!options.containsKey(option)) return null;
        return options.get(option);
    }

    public List<Record> selectAll(){
        List<Record> list = new ArrayList<>(options.size());
        Set<OPTION> keys = options.keySet();
        for (OPTION id:keys) {
            list.add(options.get(id));
        }
        return list;
    }

    public Record<OPTION> remove(OPTION option){
        if (!options.containsKey(option)) return null;
        Record<OPTION> record = select(option);
        options.remove(option);
        if (!Objects.equals(record.longName, NO_L_KEY)) longKeys.remove(record.longName);
        if (record.shortName != NO_S_KEY) shortKeys.remove(record.shortName);
        return record;
    }


    @Override
    public String toString() {
        List<Record> data = selectAll();
        StringBuilder sb = new StringBuilder();
        for (Record rec:data) {
            sb.append(rec).append(System.lineSeparator());
        }
        return sb.toString();
    }

    //--------------------------------------
    //IMPLEMENTED METHODS

    @Override
    public OPTION getOption(char shortName) { return shortKeys.getOrDefault(shortName, EMPTY).option; }

    @Override
    public OPTION getOption(String longName) {
        return longKeys.getOrDefault(longName, EMPTY).option;
    }

    @Override
    public char getShortName(OPTION option) {
        return options.getOrDefault(option, EMPTY).shortName;
    }

    @Override
    public String getLongName(OPTION option) {
        return options.getOrDefault(option, EMPTY).longName;
    }

    @Override
    public int getArgsCount(OPTION option) {
        return options.getOrDefault(option, EMPTY).argc;
    }

    //---------------------------------------
    //Special methods and classes

    public class StringOptionsDatabase extends GenericKeysDB<String> {}

    /**@param insertion - строка одного из следующих форматов:
     *                  <longName:String> <shortName:char> <argc:int>
     *                  <longName:String> - <argc:int>
     *                  <longName:String> <shortName:char>
     *                  <longName:String> -
     *                  <longName:String>
     *                  Вместо пробела - любое число любых символов табуляции
     *                  (/n - служебный символ, разделяющий разные опции,
     *                  но данного метода это не касается)
     *                  МЕТОД НЕ ВЫВОДИТ СИНТАКСИЧЕСКИХ ОШИБОК, ОСТОРОЖНО!
     * @return record with string key
     */
    private static Record<String> parseStringRecord(String insertion){
        String[] split = insertion.split("\\s++");
        if (split.length==1)
            return new Record<>(split[0], NO_S_KEY, split[0], NO_ARGS);
        char skey = (split[1].charAt(0)!='-')? split[1].charAt(0) : NO_S_KEY;
        try{
            int argc = Integer.parseInt(split[2]);
            return new Record<>(split[0], skey, split[0], argc);
        }catch (Exception e){
            return new Record<>(split[0], skey, split[0], NO_ARGS);
        }
    }

    public static GenericKeysDB<String> createStringKeyDB
            (String options, PrintStream logStream, final String ownerCommand){
        StringBuilder log = new StringBuilder("Creates keys database for command "+ownerCommand+"\n");
        String[] split = options.split("\n");
        GenericKeysDB<String> database = new GenericKeysDB<String>();
        for (String opt:split) {
            log.append("\tRow taken:").append(opt).append("\n");
            Record<String> record = parseStringRecord(opt);
            log.append("\t\tParsed as:").append(record).append("\n");
            boolean flag = database.insert(record);
            if (flag) log.append("\t\tSuccessfully added on database").append("\n");
            else log.append("\t\tERROR: UNABLE TO ADD. Skipped").append("\n");
        }
        logStream.println(log.toString());
        return database;
    }


}
