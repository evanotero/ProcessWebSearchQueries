/**
 * @author Evan Otero
 * Date: November 24, 2015
 * 
 * A priority queue using a max-heap
 */

import java.util.*;

class MyPriorityQueue<E> implements PriorityQueueADT<E> {
   
   ArrayList<E> heap;
   Comparator<E> comparator;
   int size;
      
   MyPriorityQueue(Comparator<E> comparator){
      this.comparator = comparator;
      heap = new ArrayList<E>();
      heap.add(null); // Use up the zero-space
      size = 0;
   }
   
   public boolean add(E item){
	  heap.add(item);
      siftUp(++size);
      return true;
   }
   
   public E remove(){
	   E result = heap.get(1);
	   heap.set(1, heap.get(size));
	   heap.remove(size--);
	   siftDown(1);
	   return result;
   }
   
   public boolean isEmpty(){
	   return size == 0;
   }
   
   public String toString(){
      return heap+"";
   }
   
   private void siftUp(int pos){
	   heap.set(0, heap.get(pos)); // Acts as sentinel so we don't fall of the array
	   int parent = pos/2;
	   while (comparator.compare(heap.get(parent),heap.get(pos))<0) {
		   E temp = heap.get(parent);
		   heap.set(parent, heap.get(pos));
		   heap.set(pos, temp);
		   pos = parent;
		   parent = pos/2;
	   }
   }
         
   private void siftDown(int pos){
      while (true) {
    	  int left = 2 * pos;
    	  if (left > size) break;
    	  int right = left +1;
    	  int max = right;
    	  if (right > size 
    			  || comparator.compare(heap.get(left),
    					  heap.get(right)) >= 0) max = left;
    	  if (comparator.compare(heap.get(pos), heap.get(max))>=0)
    		  break;
    	  E temp = heap.get(pos);
    	  heap.set(pos,  heap.get(max));
    	  heap.set(max, temp);
    	  pos=max;
      }
   }   
   
}