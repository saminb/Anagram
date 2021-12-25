package HashTable;

/**
 * @author Samin Bahizad
 * @version Winter 2021.
 *
 */
public class AnagramNode {
    AnagramNode next;
    String myKey;
    String myValue;

    public AnagramNode(final String Key, final String Value) {
        
        myKey = Key;
        myValue = Value;
    }
    public int count() {
        int nodes = 1;
        AnagramNode temp1 = this;
        while(temp1.next != null) {
            nodes++;
            temp1 = temp1.next;
        }
        return nodes;
    }

}
