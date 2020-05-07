package AbstractCLI;

import AbstractCLI.Commands.Command;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.Executor;


/**Вся идея класса: заменить функции лямбдами (т.е. вынести эту часть программы в другое место)
 * Зачем это надо:
 *      - уменьшить объем кода в классе
 *      - перенести логику разбора команд на внешний класс
 *      - сделать класс более универсальным
 * Причём выполнение можно сразу переложить на команду.
 * Это - т.наз. "корневая команда": цепная команда, которой передаётся управление.
 * В задачи этой команды входит передача управления другим командам.
 */
public class GenericCLI extends AbstractCLI {
    
    public interface LambdaParser{
        String[] parse(String inputLine);
    }

    //-----------------------------------------
    //Fields
    
    private LambdaParser parser = AbstractCLI::defaultParser;   //стандартный разделитель
    private Command main;
    
    //------------------------------------------
    //Constructors

    public GenericCLI(Command main){
        this.main = main;
    }

    public GenericCLI(String invitation, Command main) {
        super(invitation);
        this.main = main;
    }

    public GenericCLI(Scanner input, PrintStream output, Command main) {
        super(input, output);
        this.main = main;
    }

    public GenericCLI(Scanner input, PrintStream output, PrintStream error, Command main) {
        super(input, output, error);
        this.main = main;
    }

    public GenericCLI(Scanner input, PrintStream output, PrintStream error, String invitation, Command main) {
        super(input, output, error, invitation);
        this.main = main;
    }

    public GenericCLI(LambdaParser parser, Command main) {
        this.parser = parser;
        this.main = main;
    }

    public GenericCLI(String invitation, LambdaParser parser, Command main) {
        super(invitation);
        this.parser = parser;
        this.main = main;
    }

    public GenericCLI(Scanner input, PrintStream output, LambdaParser parser, Command main) {
        super(input, output);
        this.parser = parser;
        this.main = main;
    }

    public GenericCLI(Scanner input, PrintStream output, PrintStream error, LambdaParser parser, Command main) {
        super(input, output, error);
        this.parser = parser;
        this.main = main;
    }

    public GenericCLI(Scanner input, PrintStream output, PrintStream error, String invitation, LambdaParser parser, Command main) {
        super(input, output, error, invitation);
        this.parser = parser;
        this.main = main;
    }

    //---------------------------------------
    //Getters/setters

    public LambdaParser getParser() { return parser; }
    public void setParser(LambdaParser parser) { this.parser = parser; }
    public Command getMain() { return main; }
    public void setMain(Command main) { this.main = main; }
    //----------------------------------------


    @Override
    public boolean executeCommand(String[] args) {
        return (main.main(args, 0,  ioe) & Command.FLAG_EXIT) != 0;
    }
    @Override
    String[] parse(String inputLine) { return parser.parse(inputLine); }
}
