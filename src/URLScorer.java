/**
 * This class contains the code to compute the relevance score of a
 * web page, in response to a query.  It also contains a method to
 * compare the scores of two web pages.
 * 
 * @author Evan Otero
 * Date: November 24, 2015
 * 
 */

import java.util.*;

class URLScorer implements Comparator<WebPageIndex> {

   List<String> query;
   
   URLScorer(List<String> query){
      this.query = query;
   }
   
   public int score(WebPageIndex idx){
	   int sum = 0;
	   for (String x : query) {
		   sum+=idx.getCount(x);
	   }
	   return sum;
   }
      
    public int compare(WebPageIndex idx1, WebPageIndex idx2){ 
       if (score(idx1) < score(idx2)) return -1;
       else if (score(idx1) > score(idx2)) return 1;
       else return 0;
    }

}
