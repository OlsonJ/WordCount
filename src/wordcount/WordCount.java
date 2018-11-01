import java.io.IOException;
import java.lang.*;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

   private static void countWords(String file) {
      DataCounter<String> counter = new BinarySearchTree<String>();
   
      try {
         FileWordReader reader = new FileWordReader(file);
         String word = reader.nextWord();
         while (word != null) {
            counter.incCount(word);
            word = reader.nextWord();
         }
      } catch (IOException e) {
         System.err.println("Error processing " + file + e);
         System.exit(1);
      }
   
      DataCount<String>[] counts = counter.getCounts();
      sortByDescendingCount(counts);
      for (DataCount<String> c : counts)
         System.out.println(c.count + " \t" + c.data);
   }

    /**
     * TODO Replace this comment with your own.
     * 
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be in alphabetical order (for Strings, that
     * is. In general, use the compareTo method for the DataCount.data field).
     * 
     * This code uses HeapSort.
     * 
     * The generic parameter syntax here is new, but it just defines E as a
     * generic parameter for this method, and constrains E to be Comparable. You
     * shouldn't have to change it.
     * 
     * @param counts array to be sorted.
     */
   private static <E extends Comparable<? super E>> void sortByDescendingCount(DataCount<E>[] counts) {
            
      int size = counts.length; 
      int parent = size / 2;
      // Build the heap (rearrange array) 
      for (int i = parent - 1 ; i >= 0; i--){
         buildh(counts, size, i); 
      }      
      
      // rebuild heap starting at root
      for (int i=size-1; i>=0; i--) 
      { 
        
         DataCount<E> current = counts[0]; 
         counts[0] = counts[i]; 
         counts[i] = current; 
   
         buildh(counts, i, 0); 
      }
      
            
   /* *********************************************************************           
        for (int i = 1; i < counts.length; i++) {
            DataCount<E> x = counts[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (counts[j].count >= x.count) {
                    break;
                }
                counts[j + 1] = counts[j];
            }
            counts[j + 1] = x;
        }
        
        
   ***********************************************************************/        
   }

   private static <E> void buildh(DataCount<E>[] counts, int n, int i) {
            
      int largest = i; // Initialize largest as parent 
      int leftC = 2*i + 1; // left = 2*i + 1 
      int rightC = 2*i + 2; // right = 2*i + 2 
                 
      String larg = counts[largest].toString();
      String lef = counts[leftC].toString();
      String rig = counts[rightC].toString();
            
            
      // If left child is larger than parent
      if (leftC < n && lef.compareTo(larg) > 0)
         largest = leftC; 
      else      
      // If right child is larger than parent
      if (rightC < n && rig.compareTo(larg) > 0)
         largest = rightC; 
      else      
      // If largest is not parent commence the swap
      if (largest != i) 
      { 
      
         DataCount<E> swap = counts[i]; 
         counts[i] = counts[largest]; 
         counts[largest] = swap; 
         
         buildh(counts, n, largest);
      } 
   }

   public static void main(String[] args) {
      if (args.length != 1) {
         System.err.println("Usage: filename of document to analyze");
         System.exit(1);
      }
      countWords(args[0]);
   }
}
