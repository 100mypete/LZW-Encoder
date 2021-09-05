import java.util.*;

public class Encoder {

    public ArrayList<Integer> compress (String str)
    {
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

    public static void main (String [] args)
    {
        Encoder enc = new Encoder();
        System.out.println (enc.compress("abcabcabcabcabcabcabcabcabcabcabcabc"));
    }

}
