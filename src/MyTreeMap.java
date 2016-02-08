/**
 * An implementation of the MapADT interface using an AVL tree.
 * Empty trees are used to represent children that aren't present.
 * 
 * @author John Donaldson
 * @author Evan Otero
 * Date: November 16, 2015
 */

import java.util.*;

public class MyTreeMap<K extends Comparable<? super K>,V> 
implements MapADT<K,V> {

   // instance variables
   /** The index by which information is stored */
   K key;
   /** Value associated with the key above */
   V value;
   /** The children of this subtree -- if both null, this tree is empty */
   MyTreeMap<K,V> left,right;
   /** The number of nodes within this subtree (including self) */
   int size;
   /** The height of this subtree */
   int height;
   
   // change this value to true and the rotations will be printed
   private boolean debug = false;
   
   public V get(K searchKey) {
	   if (!isEmpty()) {
		   if (searchKey.equals(this.key)) return this.value;
		   else {
			   if (searchKey.compareTo(this.key) < 0) return left.get(searchKey);
			   else return right.get(searchKey);
		   }
	   }
	   return null;
   }
   
   public V put(K key, V value){
	   V output;
	   if (isEmpty()) {
		   // If this is empty, create new leaf
		   this.key = key;
		   this.value = value;
		   this.left = new MyTreeMap<K,V>();
		   this.right = new MyTreeMap<K,V>();
		   size=1;
		   height = 0;
		   output = null;
	   } else if (!isEmpty() && this.key.equals(key)) {
		   // If inputed key is equal to this key, replace value
		   V prev = this.value;
		   this.value = value;
		   output = prev;
	   } else {
		   // If inputed key is left than this key, put left; vice-versa
		   if (key.compareTo(this.key) < 0) output = left.put(key, value);
		   else output = right.put(key, value);
		   if (Math.abs(left.getHeight() - right.getHeight()) > 1) restructure();
		   setHeight();
		   setSize();
	   }
	   return output;
   }
 
   private void restructure() {
      MyTreeMap<K,V> a, b, c, t0, t1, t2, t3;
      
      if (left.height > right.height) {
         // left side taller, right subtree is last
         if (left.left.height > left.right.height) {
            // left->left
            if (debug) System.err.println("Single right rotation at: "+key);            
            c = this;
            b = left;
            a = left.left;
            t0 = a.left;
            t1 = a.right;
            t2 = b.right;
            t3 = c.right;
            
         } else {
            // left->right turn
            if (debug) System.err.println("Double right rotation at: "+key);
            a=left;
            b=left.right;
            c=this;
            t0=a.left;
            t1=b.left;
            t2=b.right;
            t3=c.right;                     
         }
      } else {
         if (right.right.height > right.left.height) {
            // right->right
            if (debug) System.err.println("Single left rotation at: "+key);
            a=this;
            b=a.right;
            c=b.right;
            t0=a.left;
            t1=b.left;
            t2=c.left;
            t3=c.right;          
         } else {
            // right->left turn
            if (debug) System.err.println("Double left rotation at: "+key);
            a=this;
            c=a.right;
            b=c.left;
            t0=a.left;
            t1=b.left;
            t2=b.right;
            t3=c.right;           
         }
      }    
      // Fix your current node
      MyTreeMap<K, V> newLeft = new MyTreeMap<K, V>(a.key,a.value,t0,t1);
      MyTreeMap<K, V> newRight = new MyTreeMap<K, V>(c.key,c.value,t2,t3);
      a.left = t0;
      a.right = t1;
      c.left = t2;
      c.right = t3;
      // Now fix myself
      this.key = b.key;
      this.value = b.value;
      this.left = newLeft;
      this.right = newRight;
   }
   
   //--------------------------------------------------------------------------
   // Constructors - outside users can only make an empty tree
   //--------------------------------------------------------------------------
   
   /**
    * Creates an empty tree.
    */
   public MyTreeMap(){
      this(null,null,null,null);
      this.size = 0;
      this.height = -1;
   }
   
   /**
    * Build a tree from existing components.  To be used only within MyTreeMap
    * and subchildren.  
    * @param key Key to store at this location
    * @param value Value to store at this location (associated with Key)
    * @param left An existing subtree, all Key values should be smaller than key
    * @param right An existing subtree, all Key values should be greater than key
    */
   
   protected MyTreeMap(K key, V value, MyTreeMap<K, V> left, MyTreeMap<K, V> right) {
      this.key = key;
      this.value = value;
      this.left = left;
      this.right = right;
      // fix the height
      setHeight();
      // and the size
      setSize();
   }
   
   
   /**
    * Used to construct a single leaf element.  Empty subtrees are put in place of
    * children to allow for recursive methods to not have to worry about null
    * pointers.
    * @param key Key to store at this location
    * @param value Value to store at this location (associated with Key)
    */
   
   protected MyTreeMap(K key, V value) {
      this(key,value,new MyTreeMap<K,V>(), new MyTreeMap<K,V>());
      height = 0;
      size = 1;
   }
   
   /**
    * Determine if this is a placeholder subtree for an empty leaf branch.
    * @return true only if this is a placeholder node, false otherwise
    */
   public boolean isEmpty(){
      return left==null && right==null;
   } 
   
   /**
    * Determine if this is a leaf node (not an empty node).
    * @return true if this node is not a placeholder and has no children
    */
   public boolean isLeaf() {
      return !isEmpty() && left.isEmpty() && right.isEmpty();
   }
   
   /**
    * Creates a StringBuffer (String takes too long) and then starts the
    * recursive traversal of nodes.
    * @return String representation of the (Key:Value) pairs
    */
   private String toStringHelper(){
      StringBuilder sbuf = new StringBuilder();
      toStringHelper(sbuf);
      return sbuf.toString();
   }
   
   /**
    * Adds (Key:Value) pairs inorder into the StringBuffer.
    * @param sbuf where to store the string version of the tree
    */
   private void toStringHelper(StringBuilder sbuf) {
      if(isEmpty())
         return;
      else {
         if(!left.isEmpty()) {
            left.toStringHelper(sbuf);
            sbuf.append(",");
         }
         sbuf.append( "("+key+":"+value+")" );
         if(!right.isEmpty()) {
            sbuf.append(",");
            right.toStringHelper(sbuf);
         }
      }
   }
   
   public String toString(){
      return "[" + toStringHelper() + "]";
   }
   
   // helper methods for trees
   private void setHeight() {
      if (isEmpty()) 
         this.height = -1;
      else
         this.height = 1+Math.max(left.height,right.height);
   }
   
   private void setSize(){
      if(isEmpty())
         this.size = 0;
      else
         this.size = 1+left.size+right.size;
   }
   
   public int size() {
      return this.size;
   }
   
   public int getHeight(){
      return this.height;
   }
   
   
   //--------------------------------------------------------------------------
   // Some iterators
   //--------------------------------------------------------------------------
   
   /**
    * Create an inorder iterator of the Keys in the tree.
    * @return inorder iterator of the Keys
    */
   
   public Iterator<K> keys() {
      List<K> list = new LinkedList<K>();
      addKeysToList(list);
      return list.iterator();
   }
   
   private void addKeysToList(List<K> l) {
      if (!isEmpty()){
         this.left.addKeysToList(l);
         l.add(this.key);
         this.right.addKeysToList(l);
      }
   }
   
   //--------------------------------------------------------------------------
   
   public Iterator<Map.Entry<K, V>> entries() {
      List<Map.Entry<K,V>> list = new LinkedList<Map.Entry<K,V>>();
      addEntriesToList(list);
      return list.iterator();
   }
   
   private void addEntriesToList(List<Map.Entry<K, V>> list) {
      if (!isEmpty()){
         this.left.addEntriesToList(list);
         list.add(new AbstractMap.SimpleEntry<K,V>(key,value));
         this.right.addEntriesToList(list);
      }
   }    
}
