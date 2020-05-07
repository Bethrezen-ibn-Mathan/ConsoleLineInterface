package AbstractCLI.Commands._old_tmp.Parsing.OptionValues;

import java.util.Arrays;

public class Option<V> implements OptionState<V> {
    V arg;
    int length = 0;
    int offset = 0;

    //for non-parametrized options
    public Option(V arg, int offset) {
        this.arg = arg;
        this.offset = offset;
    }

    //for parametrized options
    public Option(V arg, int length, int offset) {
        this.arg = arg;
        this.length = length;
        this.offset = offset;
    }

    @Override
    public V getValue() { return arg; }
    @Override
    public Class getValueClass() { return arg.getClass(); }
    @Override
    public int length() { return length; }
    @Override
    public int offset() { return offset; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{offs="+offset+"} => ");
        if (arg.getClass().isArray()){
            sb.append(Arrays.deepToString((Object[]) arg));
        }else sb.append(arg.toString());
        return sb.toString();
    }
}
