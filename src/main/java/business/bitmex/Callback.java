package business.bitmex;

public interface Callback<T> {
    void onDone(T data);
}
