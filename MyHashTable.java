package HashTable;
//TCSS - 342

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Samin Bahizad
 * @version Winter 2021.
 */
public class MyHashTable {
    
    double LD;
    private int theSize;
    private int totalCollision;
    private int tableSize;
    private AnagramNode[] myHashTable;
    /**
     * 
     * @param theSize size of the table.
     */

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return (theSize == 0);
    }
    public MyHashTable(final int theSize) {

        //This is found by try and error. 
        //It has least collision. 
        tableSize = 199967;
        myHashTable = new AnagramNode[tableSize];
    }

    /**
     * @param Key sorted key.
     * @param Value the original value.
     */
    public void put(final String Key, final String Value) {
               
        int p =1;
        boolean flag = false;
        AnagramNode theAnagramNode = new AnagramNode(Key, Value);
        
        int New_Key = HashFunction(theAnagramNode.myKey);
        
        //When running this Java's hash function we get 1600:
//        int New_Key = hashMethodOfJavas(Key);
        
        int original = New_Key;
        
//        System.out.println("Hash Key : " + New_Key);
//        System.out.println("value " + Value);

        do {
            
            if(myHashTable[New_Key] == null) {
                
                myHashTable[New_Key] = theAnagramNode;
                flag = true;
                break;
            }
            
            else if(myHashTable[New_Key].myKey.equals(theAnagramNode.myKey)) {
                
                theAnagramNode.next = myHashTable[New_Key];
                myHashTable[New_Key] = theAnagramNode;
                

                flag = true;

                break;
            }
            else if(!(myHashTable[New_Key].myKey.equals(theAnagramNode.myKey))) {
                
                New_Key = (original + (p*p)) % tableSize;
                                              
                if(p == 1) {
                    totalCollision++;
                }
                p++; 
            }
            
        } while(!flag);
        
        theSize++;
        LD = (double) theSize/tableSize;
    }
    
    /**
     * Finds the value.
     * @param thekey the string
     */
    public String[] getValue(final String thekey) {
                
        int position = HashFunction(thekey);
        

                
        int original = position;
        
        int i = 0;
        boolean flag = false;
        int p = 1;
        
        while(true) {
            

          if(!flag && position >= tableSize) {
              
              return null;

          }
          AnagramNode start = myHashTable[position];    
          
          if(start != null && start.myKey.equals(thekey)) {

              flag = true;
              String value[] = new String[start.count()];
              value[i] = start.myValue;
              

              
              while(start.next != null) {
                  
                  i++;
                  start = start.next;
                  value[i] = start.myValue;

              }
              return value;

          }
          else {
              position = original + (p*p);
              p++;
              i++;
          }
         
        }      
       

    }    
    
    /**
     * A method which implements java's hashCode method.
     * @param K string to find hashcode.
     * @return hashcode of the string;
     */
    public int hashMethodOfJavas(final String K) {
        
        return (Math.abs(K.hashCode()) % tableSize);
    }
    
    /**
     * A hash function to find the hash key of the string and implements Quadratic probing.
     * 
     * 
     * @param K String to find the hashcode.
     * @return the int hash code of that string.
     */
    private int HashFunction(final String K) {
                
        int length = K.length();
        int count = 0;
                
        for(int i = 0; i < length; i++) {
            
            count = (count << 5) | (count >>> 27);
            count = count + (int) K.charAt(i);
        }
        
        count = count * length + K.charAt(length/2) - K.charAt(length - 1);
                
        if(count < 0) {
            count = count + tableSize;
        }
                  
        count = Math.abs(count) % tableSize;

        return count;
        
    }
    
    private boolean isPrime(final int num) {
        boolean flag = true;
        
        for(int i = 2; i < num; i++) {
            if(num % i == 0) {
                flag = false;
                break;
            }
        }
        
        return flag;
    }
    /**
     * Determine the size for our hash table. 
     * @param mySize
     */
    public void FindSize(final int mySize) {
        
        int s = mySize+1;

        if((mySize+1) % 2 == 0) {
            s = mySize + 1 + 1;
        }

        for (int i = s; i < s*s; i=i+2) {
            if(isPrime(i)) {
                tableSize = i;
                break;
            }
        }
        
    }
 
    /**
     * Prints the whole table with anagram in the "OutputWords.txt" file.
     */
    public void print() {
        
        try {
            FileWriter writer = new FileWriter("OutputWords.txt");
            
            System.out.println("Number of collisions (by my hashing method): " + totalCollision);
            System.out.println("Number of collisions (by Java's hashing method): " + 1600);

       System.out.println("LF : " + LD);

            writer.write("Table size : " + tableSize + "\n");
            writer.write("Number of collisions (by new hashing method) : " + totalCollision + "\n");
            writer.write("Number of words: " + theSize + "\n");

            System.out.println("Number of words placed : " + theSize + "\n");

            for(int i = 0; i < tableSize; i++) {
                
                if(myHashTable[i] == null) {
                    continue;
                }

                AnagramNode start = myHashTable[i];
                writer.write(start.myValue +" " + (start.count() - 1) + " ");

                while(start.next != null) {
                    start = start.next;
                    writer.write(start.myValue + " "+ start.myKey);
                }
                writer.write("\n");
            }
            
            writer.close();
            
        } catch(IOException Err) {
            Err.printStackTrace();
        }
        
    }

}