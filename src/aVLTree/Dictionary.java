package aVLTree;

public interface Dictionary<Value>
{
    public void add(String key, Value value);
    public Value remove(String key);
    public Value find(String key);
    //public int size();
    //public Value getFirst();
    //public Value getNext();
}
