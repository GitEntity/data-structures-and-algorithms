/**
 * Devante Wilson
 * Group 12 - Data Structures
 * February 19, 2016
 * Assignment 3
 * 
 * Program sorts a stack;
 * receiving input from a set of
 * randomly generated integers.
 */

// import classes
import java.util.Random;

public class ThirdAssignment 
{
	public static class SinglyLinkedList
	{
	    /*
	     * start Node class
	     */
	    private static class Node 
	    { 
	    	// instance/class variables
	        private Integer element;
	        private Node next;
	        
	        /* 
	         * constructor/mutator method
	         */
	        public Node(Integer e, Node n)
	        {
	            element = e;
	            next = n;   
	        }
	        
	        /*
	         * mutator method
	         * set new node
	         */
	        public void setElement(int e)
	        {
				element = e;
	        }
	        /*
	         *  accessor method
	         *  return an element
	         */
	        public Integer getElement() 
	        {
	            return element;
	        }
	        
	        /*
	         *  accessor method
	         *  return reference of next node
	         */
	        public Node getNext()
	        {
	            return next;
	        }
	        
	        /*
	         * mutator method
	         * set new reference to next node
	         */
	        public void setNext(Node n)
	        {
	            next = n;   
	        }
	    }
	    /** END OF NODE CLASS **/
	    
	    // instance/class variables
	    private Node head = null;
	    private Node tail = null;
	    private int size = 0;
	    
	    // constructor
	    public SinglyLinkedList() 
	    {
	        
	    }
	    
	    /*
	     * check for empty linked list
	     * return verdict
	     */
	    public boolean isEmpty() 
	    {
	    	return size == 0;
	    }
	    
	    /*
	     * accessor method
	     * return element in first node
	     */
	    public Integer first() 
	    {
	        if(isEmpty()) 
	        {
	            return null;   
	        }
	        else 
	        {
	            return head.getElement();
	        } 
	    }
	    
	    /*
	     * accessor method
	     * return element in last node
	     */
	    public Integer last()
	    {
	        if(isEmpty()) 
	        {
	            return null;
	        }
	        else 
	        {
	            return tail.getElement();
	        }
	        
	    }
	    
	    /*
	     * mutator method
	     * add node to front of list
	     */
	    public void addFirst(Integer e) 
	    {
	    	// create new head node
	    	head = new Node(e, head);
	    	
	    	if(size == 0) 
	    	{
	    		// tail node is also head node if list is empty
	    		tail = head;
	    	}
	    	
	    	// increment size of list
	    	size++;
	    }
	    
	    /*
	     * mutator method
	     * add node to end of list
	     */
	    public void addLast(Integer e) 
	    {
	    	// create new node
	        Node newest = new Node(e, null);
	        
	        if(isEmpty()) 
	        {   
	        	// tail also happens to be head node if list is empty
	            head = newest;   
	        }
	        else 
	        {    
	        	// change pointer of old tail node to new tail node
	            tail.setNext(newest);
	            // set tail node to new node
	            tail = newest;
	            // increment size of list
	            size++;   
	        }
	    }
	    
	    /*
	     * mutator method
	     * remove the head node from list
	     */
	    public Integer removeFirst() 
	    {
	    	// return null if list is empty
	    	if(isEmpty()) 
	    	{
	            return null;   
	        }
	        
	    	// retrieve head element
	    	Integer answer = head.getElement();
	        // set new head node to next node
	        head = head.getNext();
	        // decrement size of list
	        size--;
	        
	        if(size == 0) 
	        {
	        	// if head was only node in list, tail is void
	            tail = null; 
	        }
	        
	        // return head node
	        return answer; 
	    }
	}
	    
	// instance/class variables
	private int top = -1;	// position of top stack element
	private SinglyLinkedList stack;	// stack
	
	/*
	 * default no-argument constructor
	 */
	public ThirdAssignment()
	{
		stack = new SinglyLinkedList();
	}
	
	/*
	 * add an element on the stack.
	 */
	private void push(int newElement)
	{
		// add element to top of stack
		stack.addFirst(newElement);
		// increment position
		top++;
	}
	
	/*
	 * remove and return the topmost
	 * element from the stack.
	 */
	private Integer pop()
	{
		if (isEmpty())
			return null;
		
		// temporary element to hold top stack element
		int top = stack.first();
		// remove top stack element
		stack.removeFirst();
		// decrement stack position;
		this.top--;
		
		return top;
	}
	
	/*
	 * return but do not remove topmost
	 * element in the stack.
	 */
	private Integer top()
	{
		int top;	// hold topmost element
		
		if (!isEmpty())
		{
			// set top most element
			top = stack.first();
			
			return top;
		}
		else
			return null;
	}
	
	/*
	 * return verdict on whether or not
	 * stack contains elements.
	 */
	private boolean isEmpty()
	{
		return stack.isEmpty();
	}
	
	/*
	 * sort stack with smallest element on top
	 * immutable sort - will change not original stack
	 */
	private SinglyLinkedList stackSort()
	{
		SinglyLinkedList sortedStack = stack;
		Stack.ThirdAssignment.SinglyLinkedList.Node pointer = sortedStack.head;
		Stack.ThirdAssignment.SinglyLinkedList.Node test;
		int temp;
		
		while(pointer!=null)
		{
			test = pointer.getNext();
			
			while(test!=null)
			{
				if(pointer.getElement() > test.getElement())
				{
					temp = pointer.getElement();
					pointer.setElement(test.getElement());
					test.setElement(temp);
				}
				
				test = test.getNext();
			}
			
			pointer = pointer.getNext();
		}
		
		return sortedStack;
	}
	
	public static void main(String[] args) 
	{
		// create objects/variables
		Random randomGenerator = new Random();	// random generator object
		ThirdAssignment stack = new ThirdAssignment();
		int stackSize = randomGenerator.nextInt(20);  // random stack size
		
		System.out.print("Random Number = " + stackSize 
				+ "\nInitial Stack: ");
		
		// initialize stack
		for (int a = 0; a < stackSize; a++)
		{
			// fill stack with randomly generated integers
			stack.push(randomGenerator.nextInt(50));
			// print original
			System.out.print(stack.top() + " ");
		}
		
		stack.stack = stack.stackSort();
		
		System.out.print("\nSorted Stack: ");
		
		for (int b = 0; b < stackSize; b++)
		{
			// print sorted stack
			System.out.print(stack.pop() + " ");
		}
	}
}
