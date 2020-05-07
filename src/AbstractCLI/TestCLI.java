package AbstractCLI;

import AbstractCLI.Commands.Command;
import AbstractCLI.Commands.GenericCommand;
import AbstractCLI.Commands.Handling.CommandHandler;
import AbstractCLI.Commands.Handling.OptionHandler;
import AbstractCLI.Commands.Handling.Templates.CommandHandlers.EchoCommandHandler;
import AbstractCLI.Commands.Handling.Templates.OptionsHandlers.EchoOptionHandler;
import AbstractCLI.Commands.Handling.Templates.OptionsHandlers.HelpOptionHandler;
import AbstractCLI.Commands.Handling.Templates.OptionsHandlers.ReturnOptionHandler;
import AbstractCLI.Commands.Handling.Templates.OptionsHandlers.StackedOptionHandler;
import AbstractCLI.Commands.Options.Databases.Interfaces.Option;
import AbstractCLI.Commands.Templates.Primitives.SimpleReturnCommand;

import java.util.HashMap;

public class TestCLI extends GenericCLI{

    private TestCLI(Command main) {
        super("Test CLI > ", main);
    }


    public static final String commandsList =
    "* nop\n" +
    "* exit\n"+
    "* help\n"+
    "* test [-t (--test)|-h (--help)|-n (--number=<value>)|-a (--array=<value>...)]\n";

    public static final String[][] mans = {
            {"nop", "This command do nothing. Have no arguments / keys"},
            {"exit", "This command closes interaction. Have no arguments / keys"},
            {"help", "this command prints manuals. There are next command you can use:\n"+
                    commandsList},
            {"test", "This command uses for test. Usage:\n\t" +
                    "test [-t (--test)|-h (--help)|-n (--number=<value>)|-a (--array=<value>...)]"}
    };

    public static Command
            nop = new SimpleReturnCommand("nop"),
            exit = Command.createExitCommand(),
            help,
            test;

    public static final String parseme_conf =
                    "aaa a 0\n" +
                    "bbb b 0\n" +
                    "ccc c 1\n" +
                    "ddd d 2\n" +
                    "eee e 3";
    public static final String test_conf =
                    "test t 0\n" +
                    "help h 0\n" +
                    "number n 1\n" +
                    "array a 1\n" +
                    "silence s 0\n" +
                    "exit e 0";



    static {
       HashMap<String, Object> manual = new HashMap<>();
        for (String[] man:mans) {
            manual.put(man[0], man[1]);
        }
        help = Command.createHelpCommand("help", manual);

        HashMap<String, OptionHandler<String>> testOHandlers = new HashMap<>();
        StackedOptionHandler testh = new StackedOptionHandler(
                new HelpOptionHandler("First handler works!"),
                new HelpOptionHandler("Second handler works!"),
                new HelpOptionHandler("Third handler works!")
        );
        testOHandlers.put("test", testh);
        testOHandlers.put("help", new HelpOptionHandler(mans[3][1], Command.FLAG_OPT_FINISH));
        testOHandlers.put("number", new EchoOptionHandler(true));
        testOHandlers.put("array", new EchoOptionHandler(true));
        testOHandlers.put("silence", new ReturnOptionHandler(Command.FLAG_OPT_FINISH));
        testOHandlers.put("exit", new ReturnOptionHandler(Command.FLAG_EXIT));
        CommandHandler handler = new EchoCommandHandler(true);
        test = new GenericCommand("test", test_conf, testOHandlers, handler);

    }

    public static TestCLI create(){
        Command main = Command.createMainCommand(true, nop, exit, help, test);
        return new TestCLI(main);
    }
}
