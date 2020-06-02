package com.appeti.utils;

public class StringUtilityFunctions {
	
	public static int getLevenshteinDistance(String s1, String s2) {
	      if (s1 == null || s2 == null) {
	          throw new IllegalArgumentException("Strings must not be null");
	      }

	      /*
	         The difference between this impl. and the previous is that, rather 
	         than creating and retaining a matrix of size s.length()+1 by t.length()+1, 
	         we maintain two single-dimensional arrays of length s.length()+1.  The first, d,
	         is the 'current working' distance array that maintains the newest distance cost
	         counts as we iterate through the characters of String s.  Each time we increment
	         the index of String t we are comparing, d is copied to p, the second int[].  Doing so
	         allows us to retain the previous cost counts as required by the algorithm (taking 
	         the minimum of the cost count to the left, up one, and diagonally up and to the left
	         of the current cost count being calculated).  (Note that the arrays aren't really 
	         copied anymore, just switched...this is clearly much better than cloning an array 
	         or doing a System.arraycopy() each time  through the outer loop.)

	         Effectively, the difference between the two implementations is this one does not 
	         cause an out of memory condition when calculating the LD over two very large strings.
	       */


	      s1 = s1.toLowerCase();
	      s2 = s2.toLowerCase();

	      int[] costs = new int[s2.length() + 1];
	      for (int i = 0; i <= s1.length(); i++) {
	        int lastValue = i;
	        for (int j = 0; j <= s2.length(); j++) {
	          if (i == 0)
	            costs[j] = j;
	          else {
	            if (j > 0) {
	              int newValue = costs[j - 1];
	              if (s1.charAt(i - 1) != s2.charAt(j - 1))
	                newValue = Math.min(Math.min(newValue, lastValue),
	                    costs[j]) + 1;
	              costs[j - 1] = lastValue;
	              lastValue = newValue;
	            }
	          }
	        }
	        if (i > 0)
	          costs[s2.length()] = lastValue;
	      }
	      return costs[s2.length()];
	  }

}
