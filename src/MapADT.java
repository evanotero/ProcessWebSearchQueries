import java.util.*;
/**
 * A simplified version of Java's Map interface.
 * 
 * @author John Donaldson
 */
interface MapADT<K,V> {
   
   public V get(K key);
   public V put(K key, V value);
   public Iterator<K> keys();
   public Iterator<Map.Entry<K,V>> entries();
   
}