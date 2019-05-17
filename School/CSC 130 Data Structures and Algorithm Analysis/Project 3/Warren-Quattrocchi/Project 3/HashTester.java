//Warren Quattrocchi
//CSC 130
//hashTester class

public class HashTester
{
   public static void main(String[] args)
   {
      HashTable hashTable = new HashTable();
      DataCount<String>[] dCount;
      
      hashTable.incCount("Apple");
      hashTable.incCount("The");
      hashTable.incCount("Apple");
      hashTable.incCount("Blue");
      hashTable.incCount("Blue");
      hashTable.incCount("cvcx");
      hashTable.incCount("Blue");
      hashTable.incCount("Purple");
      hashTable.incCount("Purple");
      hashTable.incCount("red");
      hashTable.incCount("red");
      hashTable.incCount("Purple");
      hashTable.incCount("red");
      hashTable.incCount("orange");
      hashTable.incCount("orange");
      hashTable.incCount("orange");
      hashTable.incCount("orange");
      
      
      dCount = hashTable.getCounts();
      for(int i = 0; i< dCount.length; i++)
      {
         System.out.println(dCount[i].getCounts());
      }
    }
}