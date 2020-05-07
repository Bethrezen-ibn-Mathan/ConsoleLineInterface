package AbstractCLI;

import java.io.PrintStream;
import java.util.Scanner;

public abstract class AbstractCLI implements CLI {
    IOE ioe = new IOE();
    String invitation = "AbstractCLI > ";               //приглашение

    //----------------------------------------------------
    //Constructors

    public AbstractCLI() { }
    public AbstractCLI(String invitation) { this.invitation = invitation;}
    public AbstractCLI(Scanner input, PrintStream output) {
        this.ioe = new IOE(input, output);
    }
    public AbstractCLI(Scanner input, PrintStream output, PrintStream error) {
        this.ioe = new IOE(input, output, error);
    }
    public AbstractCLI(Scanner input, PrintStream output, PrintStream error, String invitation) {
        this.ioe = new IOE(input, output, error);
        this.invitation = invitation;
    }

    //--------------------------------------------
    //getters/setters

    public Scanner getInput() { return ioe.getIn(); }
    public void setInput(Scanner input) { this.ioe.setIn(input); }
    public PrintStream getOutput() { return ioe.getOut(); }
    public void setOutput(PrintStream output) { this.ioe.setOut(output); }
    public PrintStream getError() { return ioe.getErr(); }
    public void setError(PrintStream error) { this.ioe.setErr(error); }
    public PrintStream getLog() { return ioe.getLog(); }
    public void setLog(PrintStream log) { this.ioe.setLog(log); }
    public String getInvitation() { return invitation; }
    public void setInvitation(String invitation) { this.invitation = invitation; }

    //---------------------------------------------
    //AbstractCLI


    @Override
    public void start() {
        boolean exitFlag;
        do {
            String[] args = nextCommand();
            exitFlag = executeCommand(args);
        }while (!exitFlag);
    }

    /**
     * Алгоритм:
     *      * вывести приглашение
     *      * получить строку с командой от ввода
     *      * провести разбор (разбить строку на массив)
     *      * вернуть результат
     * @return разбитая строка ввода
     */
    @Override
    public String[] nextCommand() {
        ioe.out.print(invitation);
        String inputLine = ioe.in.nextLine();
        return parse(inputLine);
    }

    @Override
    public abstract boolean executeCommand(String[] args);

    //-------------------------------------------------
    //commands parsing

    abstract String[] parse(String inputLine);

    /**
     * Парсит строку по разделителю
     * @param inputLine - ввод
     * @param delimiter - разделитель (по умолчанию - "\\s++", т.е. 1 или больше знаков табуляции)
     * @return разбитую строку
     */
    public static String[] defaultParser(String inputLine, String delimiter){
        return inputLine.split(delimiter);
    }

    public static String[] defaultParser(String inputLine){
        return defaultParser(inputLine, "\\s++");
    }
}
