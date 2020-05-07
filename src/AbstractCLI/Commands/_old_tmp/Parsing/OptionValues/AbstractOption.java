package AbstractCLI.Commands._old_tmp.Parsing.OptionValues;

public abstract class AbstractOption<V> {
    int offset = 0;
    public AbstractOption(int offset) { this.offset = offset; }

    public int offset() { return offset; }

    public AbstractOption create(int offset){
        return new NoArgOption(offset);
    }
}
