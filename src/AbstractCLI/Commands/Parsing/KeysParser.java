package AbstractCLI.Commands.Parsing;


import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;

/**
 * Идея: парсер описывает лишь формат парсинга.
 * Всё остальное подаётся в него как аргументы.
 */
public interface KeysParser<OPTID> {

    /**
     * Класс-оболочка
     * Содержит:
     *  - опции
     *  - смещение начала параметров
     * Почему такой формат:
     *  - опции нам нужны для модификации функционала
     *  - параметры
     * @param <OPTID> -
     */
    class ParsingResult<OPTID>{
        HashMap<OPTID, Option> options;
        //List<String> parameters;
        int paramOffset = 0;
        public ParsingResult(HashMap<OPTID, Option> options, int paramOffset) {
            this.options = options;
            //this.parameters = parameters;
            this.paramOffset = paramOffset;
        }
        public HashMap<OPTID, Option> getOptions() { return options; }
        public int getParamOffset() { return paramOffset; }
    }

    ParsingResult<OPTID> parse
            (@NotNull String[] args, int offset, CommandSettings<OPTID> settings)
            throws Exception;
}


