/**
 * 
 * @author Evan Otero
 * Date: November 24, 2015
 *
 * Application that processes a list of urls and, by using a user-entered query,
 * displays all URLs that match the query in order from the highest score to the lowest
 * 
 */

import java.io.*;
import java.util.*;

public class ProcessQueries {
	
	public static void main(String[] args)
			throws InterruptedException, FileNotFoundException {
		
		/* If no file name is given, return print statement and exit */
		if(args.length<1 || args.length>2){
		    System.out.println("Usage: java ProcessQueries <file> [max results]");
		    System.exit(1);
		}
		/**/
		
		/* Scan file */
		String fname = args[0];
		int maxResults = -1;
		if (args.length==2) maxResults = Integer.parseInt(args[1]);
		Scanner scanFile;
		
		try {
			scanFile = new Scanner(new File(fname));
		} catch (FileNotFoundException e) {
			scanFile = null;
			System.out.println(e.getMessage());
	        System.exit(1);
		}
		/**/

		/* Create a list of WebPageIndexes for each url in the file */
		List<WebPageIndex> urlIndex = new ArrayList<WebPageIndex>();
		int urlCount = 1;
		List<String> errors = new ArrayList<String>();
        while (scanFile.hasNextLine()) {
            String url = scanFile.nextLine();
            System.out.print("\rCounting Urls: "+urlCount);
            urlCount++;
            try {
				urlIndex.add(new WebPageIndex(url));
			} catch (IOException e) {
				errors.add(url);
			}
        }
        /**/
        
        /* Print out all "HTTP/S error Fetching URL" urls and proceed to ask user for query */
        System.out.println();
        if (!errors.isEmpty()) {
        	System.out.println("Error Fetching URL at:");
        	for (String i : errors) System.out.println(i);
        }
        
        if (maxResults!=-1) System.out.println("Results Limit: "+maxResults);
        else System.out.println("No output limit specified");
        
        System.out.println("Ready.");
        
		String query;
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter a query: ");
        /**/
        
        /* Continue asking for queries until user enters blank space */
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Scanner s2 = new Scanner(line);
            
            /* Proceed if user enters query */
            if (s2.hasNext()) {
                query = s2.nextLine().toLowerCase();
                List<String> list = Arrays.asList(query.split("\\s+")); // split string by whitespace and convert to list
                URLScorer comparator = new URLScorer(list); // create new comparator using query
                
                MyPriorityQueue<WebPageIndex> queue = new MyPriorityQueue<WebPageIndex>(comparator);

                /* For each url, add its WebPageIndex to the Priority Queue */
                for (WebPageIndex x : urlIndex) {
                	queue.add(x);
                }
                
                /* Print all urls with a score greater than 0 */
                int count = 0;
                if (args.length==1){
                	while (!queue.isEmpty()) {
	                	WebPageIndex target = queue.remove();
	                	if (comparator.score(target) > 0) {
	                		System.out.println("(Score: "+comparator.score(target)+") "+target.getUrl());
	                		count++;
	                	}
                	}
                } else {
                	while (!queue.isEmpty()) {
                    	WebPageIndex target = queue.remove();
                    	if (comparator.score(target) > 0) {
                    		if(count<maxResults) {
                    			System.out.println("(Score: "+comparator.score(target)+") "+target.getUrl());
                        		count++;
                    		}
                    	}
                	}
                }
                if (count==0) System.out.println("No results contained that query.");
                } else { // If blank space is entered by user, terminate program
	                System.out.println("Program terminated by user.");
	                System.exit(1);
                }
            /**/
            
            /* Prompt user for new query */
            System.out.println();
            System.out.print("Enter a query: ");
            s2.close();
            /**/
        }
        input.close();
	}
}
