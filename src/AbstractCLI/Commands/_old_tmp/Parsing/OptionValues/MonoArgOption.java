package AbstractCLI.Commands._old_tmp.Parsing.OptionValues;

import java.util.Arrays;

public class MonoArgOption<V> extends AbstractOption<V> {
    V arg;
    int length = 0;

    //for non-parametrized options
    public MonoArgOption(V arg, int offset) {
        super(offset);
        this.arg = arg;
    }

    //for parametrized options
    public MonoArgOption(V arg, int length, int offset) {
        super(offset);
        this.arg = arg;
        this.length = length;
    }


    public V getValue() { return arg; }

    public Class getValueClass() { return arg.getClass(); }

    public int length() { return length; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{offs="+offset+"} => ");
        if (arg.getClass().isArray()){
            sb.append(Arrays.deepToString((Object[]) arg));
        }else sb.append(arg.toString());
        return sb.toString();
    }
}
