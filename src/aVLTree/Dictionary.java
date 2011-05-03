package aVLTree;

/**
 * Represents a dictionary.
 *
 * @author Spellabbet
 * @param <Value> The type of value contained in the dictionary.
 */
public interface Dictionary<Value>
{
    /**
     * Add to the dictionary.
     *
     * @param key The key for finding the object.
     * @param value The value for this key.
     */
    public void add(String key, Value value);
    /**
     * Remove from the dictionary.
     * 
     * @param key The key to find the object to remove.
     * @return Item removed.
     */
    public Value remove(String key);
    /**
     * Finds a item.
     *
     * @param key Key to find.
     * @return Value of this belonging having the Key.
     */
    public Value find(String key);
}
