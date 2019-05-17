import java.util.*;
import java.math.*;
/**
 * TODO Replace this comment with your own.
 * 
 * Stub code for an implementation of a DataCounter that uses a hash table as
 * its backing data structure. We included this stub so that it's very clear
 * that HashTable works only with Strings, whereas the DataCounter interface is
 * generic.  You need the String contents to write your hashcode code.
 */
public class HashTable      implements DataCounter<String> {
	
	private HashEntry[] table;
   private static final int initialSize = 4; 
	private int size;
	 public HashTable()
	 {
	    	table = new HashEntry[initialSize];
         size = 0;
	 }
	 
    /** {@inheritDoc} */
    public DataCount<String>[] getCounts() {
       DataCount<String>[] dataCount = new DataCount[size];
       int j = 0;
       for(int i = 0; i< table.length; i++)
       {
         HashEntry curr = table[i];
        
         while(curr != null)
         {
            dataCount[j] = new DataCount(curr.getData(), curr.getCount());
            j++;
            curr = curr.getNext();
         }
       }
      return  dataCount;
    }

    /** {@inheritDoc} */
    public int getSize() {
        // TODO Auto-generated method stub
        return this.size;
    }

    /** {@inheritDoc} */
  
    private double loadFactor() 
    {
    	return (double) size / (double) table.length;
    }
    
    public void incCount(String data)
    {
      if(loadFactor() > 1)
         reHash();
      int hash = hashString(data) % table.length;
      HashEntry node = new HashEntry(data);
      
      HashEntry curr = table[hash];
      while(curr != null)
      {
         if(node.getData().toLowerCase().equals(curr.getData().toLowerCase()))
         {
             curr.incCount();         
             return;   
         }
         curr = curr.getNext(); 
      }    
      if(table[hash] == null)
      {
         table[hash] = node;
         size++; 
      }
      else
      {
         node.next = table[hash];
         table[hash] = node;
         size++;
      }   

    }
   
    public void reHash()
    {
      HashEntry curr;
    	HashEntry temp[] = new HashEntry[table.length * 2];
      for(int i = 0; i< table.length; i++)
      {
         curr = table[i];
         while(curr != null)
         {
            HashEntry node = new HashEntry(curr.getData(), curr.getCount()) ;
            int newHash = hashString(node.getData()) % temp.length;
            
            if(temp[newHash] == null)
               temp[newHash] = node;
            else
            {
               node.next= temp[newHash];
               temp[newHash] = node;
            }
            curr = curr.getNext();
         }
      }
      table = temp;
    }
    
    public int hashString(String data)
    {
    	int hash = 7;
    	for (int i = 0; i < data.length(); i++) {
    	    hash = hash*29 + data.charAt(i);
    	}
    	return Math.abs(hash);  
     }
    
    
    public void remove(String data)
    {
        int hash = hashString(data) % table.length;    
        HashEntry start = table[hash];
        HashEntry end = start;
        if (start.getData() == data)
        {
            size--;
            table[hash] = start.getNext();
            return;
        }
        while (end.getNext() != null && !end.getNext().getData().equals(data))
            end = end.getNext();
        if (end.getNext() == null)
        {
            System.out.println("\nElement not found\n");
            return;
        }
        size--;
        if (end.getNext().getNext() == null)
        {
            end.setNext(null);
            return;
        }
        end.setNext(end.getNext().getNext());
        table[hash] = start;
    }
    
//hash node inner class for separate chaining   
public class HashEntry
{
	private String data;
	private HashEntry next;
	private int count;
	
	public HashEntry(String data)
	{
		this.data = data;
		next = null;
		count = 1;
	}
	public HashEntry(String data,int count)
	{
		this.data = data;
		next = null;
		this.count = count;
	}
	
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	public HashEntry getNext()
	{
		return next;
	}
	public void setNext(HashEntry next)
	{
		this.next = next;
	}
	public void incCount()
	{
		count++;
	}
   
   public int getCount()
   {
     return count;
      }
         
   }
}
