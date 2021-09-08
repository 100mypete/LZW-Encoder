import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class Encoder {

    int size = 512;
    String inputPath = "lzw-file1.txt";
    String outputPath = "output.txt";

      /** Empty char array. */
    private static final char[] EMPTY_CHAR_ARRAY = new char[0];

    /** Empty byte array. */
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /** Mask for bit 0 of a byte. */
    private static final int BIT_0 = 1;

    /** Mask for bit 1 of a byte. */
    private static final int BIT_1 = 0x02;

    /** Mask for bit 2 of a byte. */
    private static final int BIT_2 = 0x04;

    /** Mask for bit 3 of a byte. */
    private static final int BIT_3 = 0x08;

    /** Mask for bit 4 of a byte. */
    private static final int BIT_4 = 0x10;

    /** Mask for bit 5 of a byte. */
    private static final int BIT_5 = 0x20;

    /** Mask for bit 6 of a byte. */
    private static final int BIT_6 = 0x40;

    /** Mask for bit 7 of a byte. */
    private static final int BIT_7 = 0x80;

    private static final int[] BITS = { BIT_0, BIT_1, BIT_2, BIT_3, BIT_4, BIT_5, BIT_6, BIT_7 };

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
                else if (!map.containsKey(encoded + input.charAt(i+1)))
                {
                    output += convertBinary(Integer.toBinaryString(map.get(encoded)), Integer.toBinaryString(size - 1).length());
                    if (map.size() < size)
                        map.put(encoded + input.charAt(i+1), map.size());
                    encoded = "";
                }
            }

            // write output to file
            BinaryOut out = new BinaryOut("output.dat");
            for (int i = 0; i < output.length(); i++) {
                if (output.charAt(i) == '0') {
                    out.write(false);
                } else {
                    out.write(true);
                }
            }
            out.flush();


        } catch (IOException ex){
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
