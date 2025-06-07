package business;

public interface Workable<T> {
    boolean addNew(T obj);
    boolean update(T obj);
    T searchById(String id);
    void showAll();
    void saveToFile();
    void loadFromFile();
}
