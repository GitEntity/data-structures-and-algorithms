/**
 * Devante Wilson
 * Data Structures - Assignment 4
 * March 12, 2016
 * 
 * Program sorts a list of entries stored in
 * a text file in both pre-order format and 
 * heap sort with the use of a heap.
 */

// import classes
import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;

public class FourthAssignment<K,V> 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		// define instance variables
		String name = "entry.txt";
		String line = null, temp="";
		int size,cFlag = 0,dFlag=0;
		int key=0;
		char ch;
		Entry<Integer, Character> entry;
		HeapPriorityQueue<Integer, Character> heap = new HeapPriorityQueue<>();
		
		try
		{
			FileReader filereader = new FileReader(name);
			
			BufferedReader bufferReader = new BufferedReader(filereader);
			
			while((line = bufferReader.readLine()) != null)
			{
				System.out.println(line);
				size = line.length();
				
				for(int i = 0;i<size;i++)
				{
					ch = line.charAt(i);
					
					if(Character.isDigit(ch))
					{
						while(Character.isDigit(ch))
						{
							temp = temp+ch;
							
							if(Character.isDigit(ch))
							{
								key = Integer.parseInt(temp);
							}
							
							i++;
							ch = line.charAt(i);
						}
						dFlag++;
						i--;
						temp = "";
						
					}
					if(Character.isLetter(ch))
					{
						cFlag++;
					}
					
					if(dFlag == 1 && cFlag ==1)
					{
						entry = heap.insert(key, ch);
						cFlag = 0;
						dFlag = 0;
					}
				}
			}
			bufferReader.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		
		// print results
		System.out.println("\nPreorder: ");
		heap.preOrder();
		
		System.out.println();
		
		heap = heap.heapSort(heap);
		System.out.println("Sorted List (Heap Sort): ");
		heap.traverse();
	}
}

interface Entry<K,V>
{
	K getKey();
	V getValue();
	String toString();
}

interface PriorityQueue<K,V>
{
	int size();
	boolean isEmpty();
	Entry<K,V> insert(K key, V value) throws IllegalArgumentException;
	Entry<K,V> min();
	Entry<K,V> removeMin();
}

class DefaultComparator<E> implements Comparator<E>
{
	public int compare(E a,E b) throws ClassCastException
	{
		return((Comparable<E>)a).compareTo(b);
	}
}

class HeapPriorityQueue<K,V> implements PriorityQueue<K,V> 
{
	/*
	 *  primary collection of priority queue entries 
	 */
	private class PQEntry<K,V> implements Entry<K,V>
	{
		// instance variables
		private K k;
		private V v;
		
		/*
		 * constructor
		 */
		public PQEntry(K key, V value) 
		{
		      k = key;
		      v = value;
		}
		
	    // methods of the Entry interface
		/*
		 * accessor method
		 */
	    public K getKey() 
	    { 
	    	return k; 
	    }
	    
	    /*
	     * accessor method
	     */
	    public V getValue() 
	    { 
	    	return v; 
	    }
	    
	    public String toString()
	    {
	    	String temp = "<"+k+", "+v+"> ";
	    	return temp;
	    }
	}
	
	// instance variable(s)
	private Comparator<K> comp;
	
	protected ArrayList<Entry<K,V>> heap = new ArrayList<>();
	
   /* 
    * Constructor
    * Creates an empty priority queue 
    * based on the natural ordering of its keys. 
    */
    public HeapPriorityQueue() 
    {
	  this(new DefaultComparator<K>());  
    }

   /*
    * Constructor
    * Creates an empty priority queue using the given comparator to order keys.
    * @param comp comparator defining the order of keys in the priority queue
    */
    public HeapPriorityQueue(Comparator<K> c) 
    { 
    	comp = c; 
    }

    /*
     * constructor
     */
    public HeapPriorityQueue(K[] keys, V[] values) 
    {
    	super();
    	for (int j=0; j < Math.min(keys.length, values.length); j++)
    		heap.add(new PQEntry<>(keys[j], values[j]));
    	heapify();
    }

    public int compare(Entry<K,V> a, Entry<K,V> b) 
    {
    	return comp.compare(a.getKey(), b.getKey());
    }
    
    public boolean checkKey(K key) throws IllegalArgumentException 
    {
    	try 
    	{
    		// see if key can be compared to itself
    		return (comp.compare(key,key) == 0);
    	} 
    	catch (ClassCastException e)
    	{
    		throw new IllegalArgumentException("Incompatible key");
    	}
    }

    // protected utilities
    protected int parent(int j) 
    { 
    	return (j-1) / 2; 
    }
    
    protected int left(int j) 
    { 
    	return 2*j + 1; 
    }
    
    protected int right(int j) 
    { 
    	return 2*j + 2; 
    }
    
    protected boolean hasLeft(int j) 
    { 
    	return left(j) < heap.size(); 
    }
    
    protected boolean hasRight(int j) 
    { 
    	return right(j) < heap.size(); 
    }

    /** Exchanges the entries at indices i and j of the array list. */
    protected void swap(int i, int j) 
    {
    	Entry<K,V> temp = heap.get(i);
    	heap.set(i, heap.get(j));
    	heap.set(j, temp);
    }

    /*
     * Moves the entry at index j higher, if necessary, 
     * to restore the heap property. 
     */
    protected void upheap(int j) 
    {
    	// continue until reaching root (or break statement)
    	while (j > 0) 
    	{            
    		int p = parent(j);
    		
    		// heap property verified
    		if (compare(heap.get(j), heap.get(p)) >= 0) 
    			break;
    		
    		swap(j, p);
    		
    		// continue from the parent's location
    		j = p;
    	}
    }

    /* 
     * Moves the entry at index j lower, if necessary, 
     * to restore the heap property. 
     * */
    protected void downheap(int j) 
    {
    	while (hasLeft(j)) 
    	{              
    		// continue to bottom (or break statement)
    		int leftIndex = left(j);
    		int smallChildIndex = leftIndex;     // although right may be smaller
    		
    		if (hasRight(j)) 
    		{
    			int rightIndex = right(j);
    			
    			if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)
    				smallChildIndex = rightIndex;  // right child is smaller
    		}
    		
    		if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0)
    			// heap property has been restored
    			break;
    		
    		swap(j, smallChildIndex);
    		
    		// continue at position of the child
    		j = smallChildIndex;
    	}
    }

    /** Performs a bottom-up construction of the heap in linear time. */
    protected void heapify() 
    {
    	// start at PARENT of last entry
    	int startIndex = parent(size()-1);
    	
    	// loop until processing the root
    	for (int j=startIndex; j >= 0; j--)
    		downheap(j);
    }

    // public methods

    /**
     * Returns the number of items in the priority queue.
     * @return number of items
     */
    @Override
    public int size() 
    { 
    	return heap.size(); 
    }

    /**
     * Returns (but does not remove) an entry with minimal key.
     * @return entry having a minimal key (or null if empty)
     */
    @Override
    public Entry<K,V> min() 
    {
    	if (heap.isEmpty()) 
    		return null;
    	
    	return heap.get(0);
    }

    /**
     * Inserts a key-value pair and return the entry created.
     * @param key     the key of the new entry
     * @param value   the associated value of the new entry
     * @return the entry storing the new key-value pair
     * @throws IllegalArgumentException if the key is unacceptable for this queue
     */
    @Override
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException 
    {
    	// auxiliary key-checking method (could throw exception)
    	checkKey(key);
    	Entry<K,V> newest = new PQEntry<>(key, value);
    	// add to the end of the list
    	heap.add(newest);
    	// upheap newly added entry
    	upheap(heap.size() - 1);
    	return newest;
    }

    /**
     * Removes and returns an entry with minimal key.
     * @return the removed entry (or null if empty)
     */
    @Override
    public Entry<K,V> removeMin() 
    {
    	if (heap.isEmpty()) 
    		return null;
    	Entry<K,V> answer = heap.get(0);
    	swap(0, heap.size() - 1);              // put minimum item at the end
    	heap.remove(heap.size() - 1);          // and remove it from the list;
    	downheap(0);                           // then fix new root
    	return answer;
    }


    @Override
    public boolean isEmpty() 
    {
    	return size() == 0;
    }

    public void preOrder(int j)
    {
    	if(heap.get(0) == null)
    	{
    		System.out.println("Empty heap");
    		return;
    	}
    	
    	System.out.print(heap.get(j));
    	
    	if(hasLeft(j))
    	{
    		int i = left(j);
    		preOrder(i);
    	}
    	
    	if(hasRight(j))
    	{
    		int i = right(j);
    		preOrder(i);
    	}
    }

    public void preOrder()
    {
    	preOrder(0);
    }

    public void traverse()
    {
    	int sz = heap.size();
    	
    	for(int i = 0;i<sz;i++)
    	{
    		System.out.print(heap.get(i));
    	}
    }

    public void check(int k)
    {
    	System.out.println("at index"+ k +heap.get(k));
    }
    
    public HeapPriorityQueue<Integer,Character> heapSort(
    		HeapPriorityQueue<Integer,Character> hp)
    {
    	HeapPriorityQueue<Integer,Character> heap1 = new HeapPriorityQueue<>();
    	int key;
    	char value;
    	Entry<Integer, Character> entry;
    	
    	while(!hp.isEmpty())
    	{
    		entry = hp.removeMin();
    		key = entry.getKey();
    		value = entry.getValue();
    		heap1.insert(key,value);
    	}
    	return heap1;
    }
}
