
package Tester;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import HashTable.MyHashTable;

/**
 * This class will test to see if our hashing function runs faster than the Java API's.
 * We are running both input and words. output.txt is for input and outputword.txt is for the words. 
 * The console will show the number of collisions by each hashing function and it will prove that that the new hashcode is faster. 
 * The total number of collisions for the new hashcode it is 1494 and for Java API is 1600.
 *  
 * @author Samin Bahizad
 * @version Winter 2021
 */
public class HashTester {
	 private static MyHashTable obj = new MyHashTable(199967);
	    
	    public static void main(String[] args) {

	        String Dictionary = "words";

	        String input_File = "input";


	        try {
	            Scanner sc = new Scanner(new File(Dictionary));

	            while(sc.hasNext()) {
	                
	                String value = sc.next();

	                                
	                if(value.contains(".") || value.contains(",") || value.contains(";")
	                        || value.contains("'") || value.contains("!")) {
	                    continue;
	                }
	                
	                char[] arr = value.toLowerCase().toCharArray();
	                
	                Arrays.sort(arr);
	                String key = new String(arr);

	                obj.put(key, value);
	            }
	            
	            sc.close();  

	            obj.print();
	            
	            readingInputFile(input_File);
	            

	        } catch(IOException theE) {
	            
	            System.out.println("File is not found.");
	            theE.printStackTrace();
	        }

	    }
	    
	    public static void readingInputFile(String theFile) {
	        
	        try {
	            Scanner sc2 = new Scanner(new File(theFile));
	            FileWriter writer = new FileWriter("Output.txt");

	            while(sc2.hasNext()) {
	                
	                String str = sc2.next();
	                char[] cc = str.toLowerCase().toCharArray();
	                Arrays.sort(cc);
	                String ss = new String(cc);
	                
	                String[] anag = obj.getValue(ss);
	                
	                if(anag == null) {
	                    
	                    writer.write(str + " 0" + "\n");
	                    
	                } else {
	                    
	                    writer.write(str + " " + anag.length);
	                    
	                    for(int i = 0; i < anag.length; i++) {
	                        
	                        writer.write(" " + anag[i]);
	                    }
	                    
	                    writer.write("\n");
	                }
	                
	            }
	            writer.close();
	            sc2.close();

	        } catch(IOException theE) {
	            System.out.println("File not found.");

	        }
	    }
}

	    
