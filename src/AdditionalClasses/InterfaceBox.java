package AdditionalClasses;

public interface InterfaceBox<T> {
    public T getData();
    public void setData(T data);

    public static <T> T unbox(Box<T> box){return box!=null ? box.getData() : null;}
}
