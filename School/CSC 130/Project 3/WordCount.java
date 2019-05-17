import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

    private static void countWords(String dStruct, String freq,String file) {
         DataCounter<String> counter;
      
      //create a new hash table if -h is passed   
      if(dStruct.equals("-h"))
         counter = new HashTable();
         
      //create a new BST if -b is passed   
      else if(dStruct.equals("-b"))
         counter = new BinarySearchTree<String>();
      //else create a new AVL tree   
      else
         counter = new AVLTree<String>();
         
         //initiate timer
         long timeStart = System.currentTimeMillis();
            
            //create a new filewordreader object
            //input every word into data structure
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
        
        //get total counts and put into an array of datacount objects 
        DataCount<String>[] counts = counter.getCounts();
        sortByDescendingCount(counts);
        
        //initiate totalWords to 0;
        int totalWords = 0;
        
        //calculate total number of words to use with frequency normalization
        for(int i = 0; i<counts.length; i++)
        {
           totalWords = totalWords + counts[i].count;
        }
         
        if(freq.equals("-frequency"))
        {
          for (DataCount<String> c : counts)
          {
            //normalized word frequencies
            double normalized = ((double) c.count / (double) totalWords) * 100;
            //only display words which are below 1 and above .01
            if(normalized <1 && normalized > .01)
               System.out.println(c.count + " \t" + c.data);
          }
        }   
        else if(freq.equals("-num_unique"))
           System.out.println(counts.length + " unique words");  
         
         //end timer/ print out time elapsed
         long timeEnd = System.currentTimeMillis();
         System.out.println("tree took: " + (timeEnd - timeStart) + " milliseconds");       
}


    /**
     * TODO Replace this comment with your own.
     * 
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be in alphabetical order (for Strings, that
     * is. In general, use the compareTo method for the DataCount.data field).
     * 
     * This code uses insertion sort. You should modify it to use a different
     * sorting algorithm. NOTE: the current code assumes the array starts in
     * alphabetical order! You'll need to make your code deal with unsorted
     * arrays.
     * 
     * The generic parameter syntax here is new, but it just defines E as a
     * generic parameter for this method, and constrains E to be Comparable. You
     * shouldn't have to change it.
     * 
     * @param counts array to be sorted.
     */
    private static <E extends Comparable<? super E>> void sortByDescendingCount(
            DataCount<E>[] counts) {
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
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: filename of document to analyze");
            System.exit(1);
        }
        countWords(args[0], args[1],args[2]);
    }
}
