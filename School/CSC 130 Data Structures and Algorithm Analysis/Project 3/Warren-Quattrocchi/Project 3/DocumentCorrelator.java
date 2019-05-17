//Warren Quattrocchi
//CSC 130
//Documentcorrelator class
import java.io.IOException;

class DocumentCorrelator
{

   public static void compareDocuments(String dStruct, String file1, String file2)
   {
      DataCounter<String> counter;
      DataCounter<String> counter2;
      
      if(dStruct.equals("-h"))
      {
         counter = new HashTable();
         counter2 = new HashTable();
      }
      else if(dStruct.equals("-a"))
      {
         counter = new AVLTree<String>();
         counter2 = new AVLTree<String>();
      }   
      else
      {
         counter = new BinarySearchTree<String>();
         counter2 = new BinarySearchTree<String>();
      }   
         
       try{
            FileWordReader reader = new FileWordReader(file1);
            FileWordReader reader2 = new FileWordReader(file2);
            String word = reader.nextWord();
            String word2 = reader2.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }    
            while (word2 != null) {
                counter2.incCount(word2);
                word2 = reader2.nextWord();
            }  
              
        }catch (IOException e) {
            System.err.println("Error processing " + file1 + e);
            System.exit(1);
        }
        
        DataCount<String>[] counts = counter.getCounts();
        DataCount<String>[] counts2 = counter2.getCounts();
        sortByDescendingCount(counts);
        sortByDescendingCount(counts2);
        if(counts2.length > counts.length)
        {
         DataCount<String>[] temp = counts2;
         counts2 = counts;
         counts = temp;
        }
        
        int correlation = 0;
        for (DataCount<String> c : counts)
        {
         String curr = c.data;
         for(DataCount<String> d : counts2)
         {
            if(d.data.equals(curr))
            {
               correlation = correlation + (int)Math.pow(c.count - d.count, 2);
            }
          }
        }
        System.out.println("The correlation between the 2 documents is: " + correlation );
        
   }
   
   
   
   public static void main(String[] args)
   {
      if(args.length !=3)
         {
            System.out.println("[-h  -a  -b] structure to compare with <filename1> <filename2>");
            System.exit(1);
         }
         compareDocuments(args[0], args[1], args[2]);
           
   }
   
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
}