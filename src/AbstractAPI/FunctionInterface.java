package AbstractAPI;

import javafx.util.Pair;

import java.io.PrintStream;

public interface FunctionInterface {
    interface FunctionHead{
        Class[] getExpectedTypes();
        default int getExpectedArgsCount(){
            Class[] types = getExpectedTypes();
            return types != null ? types.length : 0;
        }
        Class getReturnType();
    }
    interface FunctionHandler{
        Object handle(Object... args);
    }

    String getName();
    FunctionHead getHeader();
    FunctionHandler getHandler();

    default Object handle(Object... args){ return getHandler().handle(args); }

    default boolean checkArgs(Object... args){
        FunctionHead head = getHeader();
        int eargc = head.getExpectedArgsCount();
        if (eargc == 0) return args == null;
        if (eargc != args.length) return false;
        Class[] types = getHeader().getExpectedTypes();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) continue;
            if (!args[i].getClass().isAssignableFrom(types[i])) return false;
        }
        return true;
    }

    default boolean checkArgs(PrintStream out, Object... args){
        FunctionHead head = getHeader();
        int eargc = head.getExpectedArgsCount();
        if (eargc == 0){
            if (args == null){
                return true;
            }else{
                out.println("API, function "+getName()+
                        ":\n\tArgs count mismatch: expected 0, found "+args.length);
                return false;
            }
        }
        if (args==null){
            out.println("API, function "+getName()+
                    ":\n\tArgs count mismatch: expected "+eargc+", found 0");
            return false;
        }
        if (eargc != args.length){
            out.println("API, function "+getName()+
                    ":\n\tArgs count mismatch: expected "+eargc+", found "+args.length);
            return false;
        }
        Class[] types = getHeader().getExpectedTypes();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) continue;
            if (!args[i].getClass().isAssignableFrom(types[i])){
                out.println("API, function "+getName()+
                        ":\n\tArg #"+i+" type mismatch: expected "+
                        types[i].getName()+", found "+args[i].getClass().getName());
                return false;
            }
        }
        return true;
    }
}
