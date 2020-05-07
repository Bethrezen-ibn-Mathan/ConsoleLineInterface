package AbstractCLI.Commands;

import AbstractCLI.IOE;
import com.sun.istack.internal.NotNull;

public abstract class AbstractCommand implements Command {
    protected String name;

    public AbstractCommand(String name) { this.name = name; }
    @Override
    public String getName() { return name; }

    /**
     * Перехватывает исключение, переписывает суть ошибки в поток ошибки и выходит.
     * Бросаться исключениями стоит ТОЛЬКО в случае, если ошибка - фатальная, и команда
     * должна быть прервана немедленно.
     * В противном случае целесообразнее писать ошибки в самом обработчике, предпринимая
     * попутно попытки их исправить.
     * При этом сама программа НЕ прерывается, прерыванию подлежит только команда.
     * @param args - аргументы
     * @param offset - индекс начала команды
     * @param ioe - потоки для ввода, вывода и ошибки
     * @return 0 при успешном выполнении,
     *      число - при ошибке (коды ошибок определяются самой командой) или установке флагов,
     *      Зарезервированные биты (флаги)
     *          0х1 - флаг завершения работы
     */
    @Override
    public int main(String[] args, int offset, IOE ioe) {
        try {
            return handle(args, offset, ioe);
        }catch (Exception e){
            ioe.printError(e.getMessage());
            ioe.printDebuggingInfo("Stack trace:");
            e.printStackTrace(ioe.log);
            return Command.FLAG_EXCEPTION;
        }
    }

    /** Метод, определяющий непосредственную работу команды
     * @param args - аргументы
     * @param offset - индекс начала команды
     * @param ioe - потоки для ввода, вывода и ошибки
     * @return 0 при успешном выполнении,
     *      число - при ошибке (коды ошибок определяются самой командой) или установке флагов,
     *      Зарезервированные биты (флаги)
     *          0х1 - флаг завершения работы*/
    protected abstract int handle(@NotNull String[] args, int offset, @NotNull IOE ioe) throws Exception;
}
