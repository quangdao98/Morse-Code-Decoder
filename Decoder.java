import java.util.*;

public class Decoder {
	
	/* 
	 * Create an ArrayList of lengths of each consecutive run of 1s and 0s.
	 * Within each element of the ArrayList, list the length of the token and
	 * whether it is a run of 1s or of 0s.
	 */
	public static ArrayList<ArrayList<Integer>> getTokens(String bits) {
		//Trim the starting and ending 0s.
		while (bits.charAt(0)=='0') {
			bits=bits.substring(1,bits.length());
		}
	    while (bits.charAt(bits.length()-1)=='0') {
	    	bits=bits.substring(0,bits.length()-1);  
	    }
		
		ArrayList<ArrayList<Integer>> listTokens = new ArrayList<>();
		int tempRun=1;
		for (int i=1; i<bits.length(); i++) {
	        if (bits.charAt(i-1)!=bits.charAt(i)) {
	            ArrayList<Integer> token = new ArrayList<>();
	            token.add(tempRun);
	            
	            if (bits.charAt(i-1)=='0') {
	            	token.add(0);
	            }
	            else {
	            	token.add(1);
	            }
	            
	        	listTokens.add(token);
	            tempRun=1;
	        }
	        else {
	        	tempRun++;
	        }
	    }
		
		//Final token registering.
		if (tempRun>0) {
			ArrayList<Integer> token = new ArrayList<>();
            token.add(tempRun);
            
            if (bits.charAt(bits.length()-1)=='0') {
            	token.add(0);
            }
            else {
            	token.add(1);
            }
            
        	listTokens.add(token);
		}
		
	    return listTokens;
	}
	
	/* Create a HashMap of from the ArrayList of tokens, with the value of
	 * each token being their occurences.
	 */
	public static HashMap<Integer, Integer> mapTokens(
									ArrayList<ArrayList<Integer>> listTokens) {
		
		HashMap<Integer, Integer> hmapTokens= new HashMap<>();
		for (ArrayList<Integer> token : listTokens) {
			int tokenLength=token.get(0);
			if (hmapTokens.containsKey(tokenLength)) {
				hmapTokens.put(tokenLength, hmapTokens.get(tokenLength)+1);
			}
			else {
				hmapTokens.put(tokenLength, 1);
			}
		}
		
		return hmapTokens;
	}
	
	/* Feed the HashMap from getTokens() into the KMeans Clustering algorithm,
	 * then determine whether a length would represent a dot, dash, or space.
	 * Afterwards, convert the bits into actual Morse code.
	 */
    public static String decodeBits(String bits) {
    	
    	ArrayList<ArrayList<Integer>> listTokens = getTokens(bits);
    	HashMap<Integer, Integer> hmapTokens = mapTokens(listTokens);
    	ArrayList<Double> kMeansValues = KMeansClustering.kMeans(hmapTokens);
    	
    	String morseCode="";
    	for (ArrayList<Integer> token : listTokens) {
    		int clusterNumber = KMeansClustering.determineCluster(
    				token.get(0), kMeansValues);
    		morseCode+=tokenToMorse(token.get(1),clusterNumber);
    	}
	    
	    return morseCode;
    }
    
    /* Given the type of a run it belongs to, and whether a run is of 1s or
     * 0s, return the corresponding Morse code.
     */
    public static String tokenToMorse(int bitType, int kMeansType) {
    	if (bitType==0) {
    		if (kMeansType==1) {
    			return " ";
    		}
    		else if (kMeansType==2) {
    			return "   ";
    		}
    	}
    	else {
    		if (kMeansType==0) {
    			return ".";
    		}
    		else if (kMeansType==1) {
    			return "-";
    		}
    	}
    	return "";
    }
	
    /* 
     * Convert the Morse code into words.
     */
	public static String decodeMorse(String morseCode) {
		
		String result = "";
	    for(String word : morseCode.trim().split("   ")) {
	    	for(String letter : word.split("\\s+")) {
	    		result += MorseCode.get(letter);
	        }
	        result += ' ';
	    }
	    
	    return result.trim();
	}
}
