/**
 * Devante Wilson
 * Group 12 - Data Structures
 * February 10, 2016
 * Assignment 2
 * 
 * Program sorts a phone book by surname
 * stored in a text file
 * using a doubly linked list.
 */

// import classes
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class SecondAssignment 
{
	// nested contact class
	public static class Contact 
	{
		// instance variables
		private String name;
		private String surname;
		private String email;
		private String cellphone;
		
		public Contact()
		{
			
		}
		
		// constructor/mutator method
		public Contact(String name, 
				String surname,
				String email, 
				String cellphone)
		{
			this.name = name.trim();
			this.surname = surname.trim();
			this.cellphone = cellphone;
			this.email = email.trim();
		}
		
		// accessor method
		public String getname()
		{
			return surname;
		}
		
		public String toString()
		{
			String temp = name + " " 
					+ surname + " " 
					+ email + " " 
					+ cellphone;
			
			return temp;
		}
	}
	
	// nested phone book class
	public static class PhoneBook 
	{
		// nested node class
		private class Node
		{
			// instance variables
			private Contact element;
			private Node prev;
			private Node next;
			
			// constructor
			public Node(Contact e, Node p, Node n)
			{
				element = e;
				prev = p;
				next = n;
			}
			
			// constructor
			public Node()
			{
				element = null;
				prev = null;
				next = null;
			}
			
			/* 
			 * accessor method from contact class
			 */
			public Contact getElement()
			{
				return element;
			}
			
			/* 
			 * mutator method
			 * set new element in linked list
			 */
			public void setElement(Contact e)
			{
				element = e;
			}
			
			/* 
			 * accessor method
			 * get previous node
			 */
			public Node getPrev()
			{
				return prev;
			}
			
			/*
			 * accessor method
			 * get next node
			 */
			public Node getNext()
			{
				return next;
			}
			
			/*
			 * mutator method
			 * set new previous node
			 */
			public void setPrev(Node p)
			{
				prev = p;
			}
			
			/*
			 * mutator method
			 * set new next node
			 */
			public void setNext(Node n)
			{
				next = n;
			}
		}
		
		// instance variables
		private Node header;
		private Node trailer;
		private int size = 0;
		
		/*
		 * constructor/ mutator method
		 */
		public PhoneBook()
		{
			header = new Node(null,null,null);
			trailer = new Node(null, header,null);
			header.setNext(trailer);
		}
		
		/*
		 * accessor method
		 * return size of linked list
		 */
		public int size()
		{
			return size;
		}
		
		/*
		 * accessor method
		 * return size of zero
		 * if list is found to be empty
		 */
		public boolean isEmpty()
		{
			return size == 0;
		}
		
		/*
		 * modify list by adding a contact in a certain position
		 */
		private void addBetween(Contact e, Node pred, Node succ)
		{
			Node newest = new Node(e, pred, succ);
			pred.setNext(newest);
			succ.setPrev(newest);
			size++;
		}
		
		/*
		 * add node to front of list
		 */
		public void addFirst(Contact e)
		{
			addBetween(e,header, header.getNext());
		}
		
		/*
		 * add node to end of list
		 */
		public void addLast(Contact e)
		{
			addBetween(e,trailer.getPrev(),trailer);
		}
		
		/*
		 * return header node from list
		 */
		public Contact first()
		{
			if(isEmpty()) 	
				return null;
			
			return header.getNext().getElement();
		}
		
		/*
		 * return trailer node from list
		 */
		public Contact last()
		{
			if(isEmpty())
				return null;
			
			return trailer.getPrev().getElement();
		}
		
		/*
		 * remove and reutnr a node from list
		 */
		private Contact remove(Node node)
		{
			Node pred = node.getPrev();
			Node succ = node.getNext();
			
			pred.setNext(succ);
			succ.setPrev(pred);
			size --;
			
			return node.getElement();
		}
		
		/*
		 * remove and return header node
		 */
		public Contact removeFirst()
		{
			if(isEmpty())
				return null;
			else 
				return remove(header.getNext());
		}
		
		/*
		 * remove and return trailer node
		 */
		public Contact removeLast()
		{
			if(isEmpty())
				return null;
			
			return remove(trailer.getPrev());
		}
		
		/*
		 * traverse list
		 */
		public void traverse()
		{
			Node pointer = header;
			int i = 0;
			
			for(int j = 0; j < size; j++)
			{
				System.out.println(pointer.getNext().getElement());
				
				pointer = pointer.getNext();
				i++;
			}
		}
		
		/*
		 * return node before trailer
		 */
		public Contact getSecondLast()
		{
			return trailer.getPrev().getPrev().getElement();
		}
		
		/*
		 * accessor method
		 * return size of list
		 */
		public int getSize()
		{
			return size;
		}
		
		/*
		 * bubble sort algorithm for linked list
		 */
		public void bubbleSort()
		{
			// define variables/objects
			int i = 0;
			Node currentNode;
			Contact temp = new Contact();
			boolean check;
			int k;
			
			for(int j = 0; j < size; j++)
			{
				currentNode = header.getNext();
				
				for(i = 0; i < size - 1; i++)
				{
					k = currentNode.getElement().getname().compareTo(
							currentNode.getNext().getElement().getname());
					
					if(k > 0)
					{
						check = true;
					}
					else
					{
						check = false;
					}
					
					// swap nodes
					if (check) 
					{
			            temp = currentNode.getElement();
			            currentNode.setElement(
			            		currentNode.getNext().getElement());
			            
			            currentNode.getNext().setElement(temp);
			        }
					
				currentNode = currentNode.getNext();
				}
			}
		}
	}
	
	/*
	 * main method to run program
	 */
	public static void main(String[] args) throws IOException
	{
		String file = "PhoneBook.txt";	// file path
		String[] fileData;	// store entire record
		String[] contactInfo = new String[4];	// store record fields
		int j, size;
		// create instance of classes
		PhoneBook phonebook = new PhoneBook();
		Contact contact = new Contact();
		
		try
		{
			fileData = openFile(file);	// retrieve contact info.
			size = fileData.length;	// retrieve amount of contacts
			
			for(int i = 0; i < size; i++)
			{
				j = 0;	// hold record field amount
				
				// split string tokens
				for(String retrieval: fileData[i].split(" "))
				{
					contactInfo[j] = retrieval.trim();	// split fields
					j++;	// increment field amount
				}
				
				// build contact using information from tokens
				contact = new Contact(
						contactInfo[0],
						contactInfo[1],
						contactInfo[2],
						contactInfo[3]);
				
				// add contact to linked list
				phonebook.addLast(contact);
			}

			// sort the linked list using bubble sort
			phonebook.bubbleSort();
			// step through the linked list
			phonebook.traverse();
		}
		catch(IOException e)
		{
			// display caught exception
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * read file and count line amount
	 * showing contact amount in phone book
	 */
	public static int readLines(String file_path) throws IOException
	{
		FileReader fr = new FileReader(file_path);
		BufferedReader br = new BufferedReader(fr);
		
		int lines = 0;
		
		while(br.readLine() != null)
		{
			lines++;
		}
		
		// close file reader
		fr.close();
		// close buffered reader
		br.close();
		
		// return number of lines/records
		return lines;
	}
	
	/*
	 * read file and store data in each line
	 * as one record for a contact
	 */
	public static String[] openFile(String file_path) throws IOException
	{
		// create file and buffered readers
		FileReader fr = new FileReader(file_path);
		BufferedReader br = new BufferedReader(fr);
		
		int size = readLines(file_path);	// amount of lines to read
		String[] data = new String[size];	// array to store records
		
		// store data in each line in an array
		for(int i = 0; i<size; i++)
		{
			data[i] = br.readLine();
		}
		
		// close file reader
		fr.close();
		// close buffered reader
		br.close();
		
		// return contact information
		return data;
	}
}
