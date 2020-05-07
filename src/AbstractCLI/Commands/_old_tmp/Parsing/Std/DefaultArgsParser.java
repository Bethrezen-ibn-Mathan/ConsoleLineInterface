package AbstractCLI.Commands._old_tmp.Parsing.Std;

import AdditionalClasses.Box;
import AbstractCLI.IOE;
import AbstractCLI.Commands._old_tmp.Parsing.OptionsCreator;
import AbstractCLI.Commands._old_tmp.Parsing.OptionsTable;
import AbstractCLI.Commands._old_tmp.Parsing.OptionValues.OptionState;

import java.util.ArrayList;
import java.util.HashMap;

public class DefaultArgsParser<ID> extends OptionsCreator<ID> {

    public DefaultArgsParser(String callerName, OptionsTable<ID> table) {
        super(callerName, table);
    }

    /**
     * Формат, который описывается:
     *      1. Короткие ключи начинаются с "-", их аргументы следуюти через пробел. В силу
     *      допустимости аргументов сжатие безаргументных ключей (т.е. вместо -h -m писать -hm)
     *      НЕ допускается.
     *      2. Длинные ключи начинаются с "--", их аргументы следуют через знак "=",
     *      перечисляются через запятую
     *      3. Подкоманды начинаются с символа "/", все ключи, аргументы и опции после имени
     *      подкоманлы относятся к ней, а не к данной (нужно передать управление).
     *
     * Алгоритм: автомат
     *  1. Создаём карту
     *  2. Создаём счётчик смещения
     *  3. Пока счётчик меньше чем кол-во аргументов:
     *      1. Получаем очередной аргумент
     *      2. Сверяем его с форматом ключа. Т.е., в начале должны идти символы:
     *          -               : для коротких ключей
     *          --              : для длинных ключей
     *          /               : для явно-зарезервированной подкоманды
     *          всё остальное   : для аргумента
     *      3. Если нашли "ничейный" аргумент - закидываем в спец.массив и идём дальше.
     *      4. Если нашли длинный ключ - получаем часть до знака "="
     *      5. Ищем идентификатор ключа/команды в списке опций
     *          2.1. Нашли? Идём дальше...
     *          2.2. Не нашли? Ошибка!
     *      6. Получаем парсер
     *      7. Формируем опцию.
     *          При этом подкоманда идёт в карту опций как опция, её аргумент - смещение
     *          начала аргументов команды.
     *          Парсер подкоманды возвращает смещение, большее числа аргументов.
     *          Парсер длинного ключа, напротив, работает только с 1 элементом массива args
     *          и возвращает смещение i+1.
     *
     *      8. Добавляем опцию в карту. При этом если такая опция там уже есть - выводится
     *      предупреждение, опция перезаписывается.
     *      9. Ставим смещение на то, которое получили из парсера
     *  4. Возвращаем карту
     *
     * @param args - аргументы
     * @param offset - смещение
     */
    @Override
    public void parse(String[] args, int offset, IOE ioe) {
        this.ioe = ioe;
        HashMap<ID, OptionState> parsed = new HashMap<>();
        ArrayList<String> parameters = new ArrayList<>();
        int i = offset;
        while (i<args.length){
            String arg = args[i];   //1:
            switch (arg.charAt(0)){
                //sub command
                case '/':{
                    i = parseSubCmd(parsed, args, i);
                    break;
                }
                //key
                case '-':{
                    if (arg.length()>1 && arg.charAt(1)=='-'){
                        i = parseLong(parsed, args, i);
                        break;
                    }else{
                        i = parseShort(parsed, args, i);
                        break;
                    }
                }
                //parameter for command - end of keys part
                default:{
                    parameters.add(arg);
                    i++;
                }
            }
        }
        this.options = parsed;

    }

    private int parseLong(HashMap<ID, OptionState> dest, String[] args, int offset){
        String arg = args[offset].substring(2);
        if (arg.matches("=")){
            arg = args[offset].split("=")[0];
        }
        ID id = this.table.getId(arg);
        if (id==null){
            addWarning("Arg #"+offset+" : unknown long key "+arg, "Ignored");
            return offset+1;
        }
        OptionsTable.OptionParser parser = this.table.getParserLong(id);
        Box<Integer> offs = new Box<Integer>(offset+1); //default
        OptionState state = parser.parse(args, offset, offs);
        if (dest.containsKey(id)){
            addWarning("Arg #"+offset+" : non-unique key "+arg,
                    "Key state rewritten");
            dest.replace(id, state);
        } else dest.put(id, state);
        return offs.getData();
    }

    private int parseShort(HashMap<ID, OptionState> dest, String[] args, int offset){
        String arg = args[offset].substring(1);
        ID id = this.table.getId(arg);
        if (id==null){
            addWarning("Arg #"+offset+" : unknown short key "+arg, "Ignored");
            return offset+1;
        }
        OptionsTable.OptionParser parser = this.table.getParser(id);
        Box<Integer> offs = new Box<Integer>(offset+1); //default
        OptionState state = parser.parse(args, offset, offs);
        if (dest.containsKey(id)){
            addWarning("Arg #"+offset+" : non-unique key '"+arg,
                    "Key state rewritten");
            dest.replace(id, state);
        } else dest.put(id, state);
        return offs.getData();
    }

    private int parseSubCmd(HashMap<ID, OptionState> dest, String[] args, int offset){
        String arg = args[offset].substring(1);
        ID id = this.table.getId(arg);
        if (id==null){
            addWarning("Arg #"+offset+" : unknown sub command "+arg,
                    "Ignored with all following arguments");
            return args.length;
        }
        OptionState state = table.createOptionForSubCmd(id, offset+1);

        /*OptionsTable.OptionParser parser = this.table.getParser(id);
        Box<Integer> offs = new Box<Integer>(args.length); //default
        OptionState state = parser.parse(args, offset, offs);*/
        if (dest.containsKey(id)){
            addWarning("Arg #"+offset+" : non-unique sub command call "+arg,
                    "Key state rewritten");
            dest.replace(id, state);
        } else dest.put(id, state);
        return args.length;
    }
}
