package AbstractCLI.Commands;


import AbstractCLI.Commands.Templates.Primitives.SimpleChainCommand;
import AbstractCLI.Commands.Templates.Primitives.SimplePrintCommand;
import AbstractCLI.Commands.Templates.Primitives.SimpleReturnCommand;
import AbstractCLI.IOE;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;

/**
 * Команда: обработчик команды
 *
 * Существует такое понятие как подкоманда
 * Подкоманда - "команда в команде"
 * Она обрабатывается своим экземпляром класса Command
 *
 * У команды может быть аргумент. Он не содержит пробелов
 *
 *
 * Масив аргументов включает в себя аргументы, ключи, опции и подкоманды
 * Ключи:
 *      - бывают короткими (-h) и длинными (--help)
 *      -
 *
 * Основная идея для команд:
 *      - подкоманды и аргументы идут вперемешку
 *      - подкоманды идут без обозначений,
 *      - первичный масив аргументов передаётся полностью
 *      -
 *
 * Обработка делится на такие этапы:
 *      - разобрать строку с аргументами
 *      - передать управление нужному обработчику
 *      - вернуть код возврата от него (если он есть) / перехватить исключения
 *
 * Данный класс НЕ ВЫПОЛНЯЕТ команды, лишь вызывает обработчики на уровне API
 *
 */
public interface Command {

    /**
     * Зарезервированные значения
     */
    int     ANS_OK = 0,                     //нормальное завершение команды
            FLAG_EXIT = 1,                  //выход из программы
            FLAG_EXCEPTION = 2,             //команда прервана исключением
            FLAG_OPT_FINISH = 4             //команда завершена опцией на стадии преобработки
            ;

    /**@return имя команды (тот токен, который её описывает)*/
    String getName();

    /** Основной исполняемый метод команды. Занимается анализом команды, её разбором и
     * передачей управления функциям програмного интерфейса приложения.
     * Сама команда НИЧЕГО не выполняет на уровне ниже.
     * @param args - аргументы
     * @param offset - индекс начала команды
     * @param ioe - потоки для ввода, вывода и ошибки
     * @return 0 при успешном выполнении,
     *      число - при ошибке (коды ошибок определяются самой командой) или установке флагов,
     *      Зарезервированные биты (флаги)
     *          0х1 - флаг завершения работы*/
    int main(@NotNull String[] args, int offset, @NotNull IOE ioe);


    //----------------------------------------------------------
    //STATIC SECTION
    //----------------------------------------------------------

    static Command createMainCommand(@NotNull Command unknownTokenFound, Command... commands){
        HashMap<String, Command> pool = new HashMap<>();
        if (commands != null){
            for (Command c:commands) { pool.put(c.getName(), c); }
        }
        return new SimpleChainCommand("/", pool, unknownTokenFound);
    }
    static Command createMainCommand(boolean printNF, @NotNull Command unknownTokenFound, Command... commands){
        HashMap<String, Command> pool = new HashMap<>();
        if (commands != null){
            for (Command c:commands) { pool.put(c.getName(), c); }
        }
        return new SimpleChainCommand("/", pool, unknownTokenFound, printNF);
    }
    static Command createExitCommand(){ return new SimpleReturnCommand("exit", FLAG_EXIT); }
    static Command createExitCommand(String name){ return new SimpleReturnCommand(name, FLAG_EXIT); }
    static Command createHelpCommand(String name, HashMap<String, Object> manuals){
        return new SimplePrintCommand(name, manuals);
    }


}
