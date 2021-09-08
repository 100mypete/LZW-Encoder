import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class Encoder {

    int size = 512;
    String inputPath = "lzw-file1.txt";
    String outputPath = "output.txt";

    public void compress()
    {
        try 
        {
            // read input
            String input = Files.readString(Paths.get(inputPath), StandardCharsets.UTF_8);

            // initialize output
            String output = "";
            FileOutputStream fos = new FileOutputStream(new File(outputPath));
            DataOutputStream dos = new DataOutputStream(fos);

            // ASCII dictionary
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (int i = 0; i < 256; i++)
                map.put("" + (char)i, i);

            // encoder
            String encoded = "";
            for (int i = 0; i < input.length(); i++)
            {
                encoded += input.charAt(i);
                if (i == input.length() - 1)
                    output += convertBinary(Integer.toBinaryString(map.get(encoded)), Integer.toBinaryString(size - 1).length());
                else
                {
                    output += convertBinary(Integer.toBinaryString(map.get(encoded)), Integer.toBinaryString(size - 1).length());
                    if (map.size() < size)
                        map.put(encoded + input.charAt(i+1), map.size());
                    encoded = "";
                }
            }

            // write output to file
            dos.writeBytes(output);
            dos.close();
            fos.close();


        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
    }

    public String convertBinary(String binary, int bit) {
        String converted = binary;
        for (int j = 0; j < (bit - binary.length()); j++) {
            converted = "0" + converted;
        }
        return converted;
    }

    public static void main (String [] args)
    {
        Encoder enc = new Encoder();
        enc.compress();
    }

}
