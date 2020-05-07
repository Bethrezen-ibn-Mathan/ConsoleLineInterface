package AbstractCLI;

/**
 * Класс для консольного интерфейса
 * Реализует управление приложением
 * Методы:
 *      start - запускает интерфейс
 *      nextCommand - получить следующую команду
 *      executeCommand - выполнить команду
 * Тут мы абстрагируемся от источника команд (консоль, файл, API, контроллер GUI и т.п.)
 */
public interface CLI {

    /**метод для запуска интерфейса (цикл)*/
    void start();

    /**Ввод данных от источника ввода
     * @return parsed input*/
    String[] nextCommand();

    /**Выполняет введённые команды
     * @param args - parsed input
     * @return exit flag (if set - interaction must be finished)*/
    boolean executeCommand(String[] args);
}
