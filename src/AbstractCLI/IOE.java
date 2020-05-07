package AbstractCLI;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Input/output/error container
 */
public class IOE {
    public Scanner in = new Scanner(System.in);
    public PrintStream
            out = new PrintStream(System.out),
            err = new PrintStream(System.err),
            log = new PrintStream(System.out);


    public IOE() { }

    public IOE(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public IOE(Scanner in, PrintStream out, PrintStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
    }

    public IOE(Scanner in, PrintStream out, PrintStream err, PrintStream log) {
        this.in = in;
        this.out = out;
        this.err = err;
        this.log = log;
    }

    public Scanner getIn() { return in; }
    public void setIn(Scanner in) { this.in = in; }
    public PrintStream getOut() { return out; }
    public void setOut(PrintStream out) { this.out = out; }
    public PrintStream getErr() { return err; }
    public void setErr(PrintStream err) { this.err = err; }
    public PrintStream getLog() { return log; }
    public void setLog(PrintStream log) { this.log = log; }

    public void printMessage(String message){ out.println(message); }
    public void printWarning(String message){
        String msg = "[WARNING] "+message;
        err.println(msg); log.println(msg);
    }
    public void printError(String message){
        String msg = "[ERROR] "+message;
        err.println(msg); log.println(msg);
    }
    public void printDebuggingInfo(String message){ log.println(message); }
}
