

import java.util.*;
import java.io.IOException;
//import java.nio.charset.*;
//import java.nio.file.*;



public class Encoder {
	
	 public static void main (String[] args) throws IOException {
	        Encoder enc = new Encoder();
	       // System.out.println (enc.compress("abcabc"));
	        ArrayList<Integer> compressed = enc.compress("abcabc");
	        System.out.println(enc.decompress(compressed));
	        
	    }

    public ArrayList<Integer> compress (String str) throws IOException
		{
		    //int size = 512;
		    //String inputPath = "lzw-file1.txt";
		    //String outputPath = "output.txt";
		    
		    //String str = Files.readString(Paths.get(inputPath), StandardCharsets.UTF_8);


		    int size = 256;
		    HashMap<String, Integer> map = new HashMap<String, Integer>();
		    for (int i = 0; i < 256; i++)
		       map.put("" + (char)i, i);
		    String s = "";
		    ArrayList<Integer> result = new ArrayList<Integer>();
		    for (char ch : str.toCharArray())
		    {
		        String ch1 = s + ch;
		        if (map.containsKey(ch1))
		            s = ch1;
		        else
		        {
		            result.add(map.get(s));
		            map.put(ch1, size++);
		            s = "" + ch;
		        }
		    }

		    if (!s.equals(""))
		        result.add(map.get(s));
		    
        return result;
    }
    public String decompress (ArrayList<Integer> compressed) {
 
    	 HashMap<Integer, String> map = new HashMap< Integer, String>();
         for (int i = 0; i < 256; i++)
            map.put(i, "" + (char)i);
         for(int i = 0; i <= compressed.size(); i++) {	 
        	 //need to work on edge case at end of for loop
        	 if (i+1 < compressed.size()) {
        		 String current = map.get(compressed.get(i).intValue());
        		 String next = map.get(compressed.get(i+1).intValue());
        		 String fstLetNext = next.substring(0,1);
        		 map.put(i+256, current+fstLetNext);
        		 
        	 }	 
        	 
         }
         String result = "";
         for(int i = 0; i <= compressed.size(); i++) {
        	 result = result + map.get(compressed.get(i));
         }
         
    	
    	return result;
    }

   

}
