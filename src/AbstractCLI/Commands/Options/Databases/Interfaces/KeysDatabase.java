package AbstractCLI.Commands.Options.Databases.Interfaces;

/**
 * Данная база предоставляет описание ключей для парсинга.
 * В её задачи НЕ входит определять обработку этих ключей.
 * @param <OPTION> - Ключ, по которому можно однозначно идентифицировать конкретную опцию.
 *
 * Какие данные хранит база данных:
 *                - опция:OPTION (первичный ключ)
 *                - кол-во аргументов для опции:int
 *                - короткое имя:char
 *                - длинное имя:String
 */
public interface KeysDatabase<OPTION> {
    //Преобразования вида "строка - опция"
    OPTION getOption(char shortName);
    OPTION getOption(String longName);

    //Обратные преобразования
    char getShortName(OPTION option);
    String getLongName(OPTION option);

    //выдать кол-во аргументов
    int getArgsCount(OPTION option);

    //выдать информацию, касающуюся обработки
    //boolean isPreOperated(OPTION option);
    //
    //

    //defaults
    char NO_S_KEY = 0;
    String NO_L_KEY = null;
    int NO_ARGS = 0;
}
