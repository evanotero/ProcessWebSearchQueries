/**
 * @author Evan Otero
 * Date: November 24, 2015
 * 
 * JUnit Test Class for MyPrioriryQueue.java
 */

import java.io.IOException;
import java.util.*;

import junit.framework.TestCase;

public class MyPriorityQueueTest extends TestCase{

	public void testConstruct() throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("google");
		
		WebPageIndex idx1 = new WebPageIndex("https://www.google.com/");
		WebPageIndex idx2 = new WebPageIndex("http://www.cs.bc.edu/~straubin/");
		WebPageIndex idx3 = new WebPageIndex("http://www.cs.bc.edu/~donaldja/");
				
		MyPriorityQueue<WebPageIndex> queue = new MyPriorityQueue<WebPageIndex>(new URLScorer(list));
		assertTrue(queue.isEmpty());
		
		queue.add(idx1);
		queue.add(idx3);
		queue.add(idx2);
		
		assertFalse(queue.isEmpty());
	}
	
	public void testRemove() throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("gmail");
		
		WebPageIndex idx1 = new WebPageIndex("https://www.google.com/");
		WebPageIndex idx2 = new WebPageIndex("http://www.cs.bc.edu/~straubin/");
		WebPageIndex idx3 = new WebPageIndex("http://www.cs.bc.edu/~donaldja/");
				
		MyPriorityQueue<WebPageIndex> queue = new MyPriorityQueue<WebPageIndex>(new URLScorer(list));
		queue.add(idx2);
		queue.add(idx1);
		queue.add(idx3);
		assertEquals("gmail - idx1",idx1,queue.remove());
	}
}
