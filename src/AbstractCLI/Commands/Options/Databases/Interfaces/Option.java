package AbstractCLI.Commands.Options.Databases.Interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Состояние опции описывает параметры опции или её значение
 * Когда команда парсится происходит инициализация
 * Приоритетность опций и разрешение конфликтов полагается на обработчик команды
 * Анализ опции и формирование состояний полагается на парсер
 * Все аргументы опций хранятся как строки. Задача преобразования типов полностью полагается
 * на обработчик команды.
 */
public interface Option {
    List<String> getValues();   //аргументы опции (если есть)
    int getPosition();          //позиция (смещение) опции
    int getLength();

    abstract class AbstractOpt implements Option {
        int pos = 0;
        public AbstractOpt(int pos) { this.pos = pos; }
        @Override
        public int getPosition() { return pos; }
    }

    //для булевых опций любой длины ключа
    class BoolOpt extends AbstractOpt{
        int subPos = 0;     //для булевых ключей, которые идут по одному смещению (cmd -abcde)
        public BoolOpt(int pos) { super(pos); }
        public BoolOpt(int pos, int subPos) { super(pos);this.subPos = subPos; }
        @Override
        public List<String> getValues() { return new ArrayList<>(); }
        public int getSubPosition() { return subPos; }
        @Override
        public int getLength() { return 0; }
    }

    //аргументная опция для длинного ключа
    class ArgumentableOpt extends AbstractOpt{
        List<String> values = new ArrayList<>();

        public ArgumentableOpt(int pos) { super(pos); }
        public ArgumentableOpt(int pos, List<String> values) { super(pos);this.values = values; }
        public ArgumentableOpt(int pos, String... values){
            super(pos);
            this.values.addAll(Arrays.asList(values));
        }
        @Override
        public List<String> getValues() { return values; }
        @Override
        public int getLength() { return values.size(); }
    }

    //аргументная опция для короткого ключа
    class ShortOption extends AbstractOpt{
        String[] args;  //ссылка на массив аргументов
        int length;     //кол-во аргументов опции

        public ShortOption(int pos, String[] args, int length) {
            super(pos);
            this.args = args;
            this.length = length;
        }
        @Override
        public List<String> getValues() {
            return Arrays.asList(Arrays.copyOfRange(args, pos+1, pos+length+1));
            //+1 потому что аргументы начинаются не сразу, а offset - начало самой опции
        }
        @Override
        public int getLength() { return length; }
    }




    static Option create(int pos){return new BoolOpt(pos);}
    static Option create(int pos, int subpos){ return new BoolOpt(pos, subpos); }
    static Option create(int pos, String... args){return new ArgumentableOpt(pos, args);}
    static Option create(int pos, String[] args, int length){
        return new ShortOption(pos, args, length);
    }
}
