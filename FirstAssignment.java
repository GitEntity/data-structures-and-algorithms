/**
 * Devante Wilson
 * January 24, 2016
 * 
 * Data Structures - 2715U
 * Assignment 1
 * 
 * Using the bubble sort routine,
 * the program calculates the
 * running times of this
 * algorithm given randomly
 * generated array inputs.
 */

package algorithmAnalysis;

// import classes
import java.util.Random;

public class FirstAssignment 
{
	// instance/class variables
	static long startTime, endTime, elapsedTime;
	static int[] randomArray1 = new int[1000];
	static int[] randomArray2 = new int[5000];
	static int[] randomArray3 = new int[10000];
	static long[] elapsedTimeArray = new long[10];
	static Random randomGenerator = new Random();
	
	/* initialize array with random generator */
	private static void initialize(int[] arr)
	{
		for (int a = 0; a < arr.length; a++)
		{
			// initialize each element
			arr[a] = randomGenerator.nextInt(10);
		}
	}
	
	/* bubble sort algorithm */
	private static void bubbleSort(int[] arr)
	{
		int temp;	// temporary storage variable
		boolean swap = true;	// notice to keep passing through array
		
		while (swap)	// start first full iteration
		{
			swap = false;	// await possible swap
			
			for (int a = 0; a < arr.length - 1; a++)
			{
				// descending sort
				if (arr[a] < arr[a + 1])
				{
					temp = arr[a];
					arr[a] = arr[a + 1];
					arr[a + 1] = temp;
					swap = true;	// swap occurred
				}
			}
		}
	}
	
	/* sort minimum times of all ten trials */
	private static long minTimeSort(long[] times)
	{
		long temp;	// temporary storage variable
		boolean swap = true;	// notice to keep passing through array
				
		while (swap)	// start first full iteration
		{
			swap = false;	// await possible swap
			
			for (int a = 0; a < times.length - 1; a++)
			{
				// ascending sort
				if (times[a] > times[a + 1])
				{
					temp = times[a];
					times[a] = times[a + 1];
					times[a + 1] = temp;
					swap = true;	// swap occurred
				}
			}
		}
		
		// return smallest time
		return times[0];
	}
	
	/* sort maximum times of all ten trials */
	private static long maxTimeSort(long[] times)
	{
		long temp;	// temporary storage variable
		boolean swap = true;	// notice to keep passing through array
		
		while (swap)	// start first full iteration
		{
			swap = false;	// await possible swap
			
			for (int a = 0; a < times.length - 1; a++)
			{
				// descending sort
				if (times[a] < times[a + 1])
				{
					temp = times[a];
					times[a] = times[a + 1];
					times[a + 1] = temp;
					swap = true;	// swap occurred
				}
			}
		}
		
		// return largest time
		return times[0];
	}
	
	/* find mean time of all ten trials */
	private static long meanTime(long[] times)
	{
		// hold total of all times
		long total = 0;
		
		for (int a = 0; a < times.length; a++)
		{
			// sum elapsed times
			total += times[a];
		}
		
		// return average elapsed time
		return total/times.length;
	}
	
	/* run experiment for a given array */
	private static void runExperiment(int[] random)
	{
		// repeat the experiment for ten trials
		for (int a = 0; a < elapsedTimeArray.length; a++)
		{
			initialize(random);
			
			// record the starting time
			startTime = System.currentTimeMillis();
			
			// run the algorithm
			bubbleSort(random);
			
			// record the ending time
			endTime = System.currentTimeMillis();
			
			// compute the elapsed time
			elapsedTime = endTime - startTime;
			
			// store the elapsed times
			elapsedTimeArray[a] = elapsedTime;
		}
		
		// print results
		System.out.println(random.length + "\t\t\t" 
				+ minTimeSort(elapsedTimeArray) + "\t\t" 
				+ maxTimeSort(elapsedTimeArray) + "\t\t" 
				+ meanTime(elapsedTimeArray));
	}
	
	public static void main(String[] args) 
	{
		// print table headers
		System.out.println("Input amount" + "\t\t" 
				+ "Min. Time (ms)\t" 
				+ "Max. Time (ms)\t"
				+ "Mean Time (ms)");
		
		// run experiment for given arrays
		runExperiment(randomArray1);
		runExperiment(randomArray2);
		runExperiment(randomArray3);
	}
}
