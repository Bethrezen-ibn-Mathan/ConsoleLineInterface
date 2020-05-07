package AdditionalClasses;

public class Box<T> implements InterfaceBox<T> {
    T data = null;

    public Box() { }
    public Box(T data) { this.data = data; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public static <T> Box<T> box(T value){ return new Box<T>(value); }
    public static <T> T unbox(Box<T> box){return box!=null ? box.getData() : null;}
}
