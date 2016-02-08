/**
 * @author Evan Otero
 * Date: November 16, 2015
 * 
 * Class that contains an index representation for a webpage
 */

import java.util.*;
import java.io.*;

public class WebPageIndex {
   
   MyTreeMap<String,List<Integer>> tree = new MyTreeMap<String,List<Integer>>();
   String url = "";
   Integer words = 0;

   // WebPageIndex constructor
   public WebPageIndex(String baseUrl) throws IOException {
    this.url = baseUrl;
			   
    HTMLScanner scanner = new HTMLScanner(url);
    int position = 0;
    while(scanner.hasNext()) {
       String token = scanner.next().toLowerCase();

       List<Integer> value = (List<Integer>) this.tree.get(token);
       
       if (tree.get(token) == null) {
      	 value = new LinkedList<Integer>();
      	 this.tree.put(token,value);
       }
       value.add(Integer.valueOf(position++));
     }
     this.words = position;
   }     
   
   // Returns a count of the words in this web page
   public int getWordCount() {
      return this.words;
   }
   
   public String getUrl() {
      return this.url;
   }
   
   public boolean contains(String s) {
      return this.tree.get(s) != null;
   }
   
   public int getCount(String s) {
      if (contains(s)) {
    	  List<Integer> value = this.tree.get(s);
    	  return value.size();
      } else return 0;
   }
   
   public double getFrequency(String s) {
      double count = getCount(s);
      return (count/words);
   }
   
   public List<Integer> getLocations(String s) {
	   if (!contains(s)) return new LinkedList<Integer>();
	   else return tree.get(s);
   }

   // return an Iterator over all the words in the index
   public Iterator<String> words() {
	   return this.tree.keys();
   }
   
   public String toString() {
      return this.tree.toString();
   }

   // The main method is an application using a WebPageIndex
   public static void main(String[] args) throws IOException {
	  if(args.length<1){
	      System.out.println("Usage: java WebPageIndex <url>");
	      System.exit(1);
	   }

     WebPageIndex index;
     try {
      index = new WebPageIndex(args[0]);
    } catch (FileNotFoundException e) {
      index = null;
      System.out.println(e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      index = null;
      System.out.println(e.getMessage());
      System.exit(1);
    }
	   
	   Iterator<String> iterator = index.words();
	   
	   while (iterator.hasNext()) {
		   String current = iterator.next();
		   System.out.printf("%-18s %-10.5f %-12s\n",current,index.getFrequency(current),index.getLocations(current));
	   }
   }
      
}
