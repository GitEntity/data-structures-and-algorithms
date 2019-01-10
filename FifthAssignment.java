/**
 * Devante Wilson
 * March 24, 2016
 * Data Structures - Assignment 5
 * 
 * Program compares the running time
 * of various sorting algorithms
 * using randomly generated numbers.
 */

// import classes
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class FifthAssignment 
{
	// instance/class variables
	static long startTime, endTime, elapsedTime;
	static int[] randomArray = new int[10000];
	static long[] elapsedTimeArray = new long[10];
	static Random randomGenerator = new Random();
	static Comparator <Integer> comp = new Comparator<Integer>()
		{
			public int compare(Integer d1, Integer d2)
			{
				return d1.compareTo(d2);
			}
		};
	
	/*
	 * initialize array with random generator
	 */
	private static void initialize(int[] arr)
	{
		for (int a = 0; a < arr.length; a++)
		{
			// initialize each element
			arr[a] = randomGenerator.nextInt(100000);
		}
	}
	
	/*
	 * sort minimum times of all ten trials 
	 */
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
	
	/*
	 * sort maximum times of all ten trials
	 */
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
	
	/* 
	 * find mean time of all ten trials 
	 */
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
	
	public static void main(String[] args) 
	{
		// print table headers
		System.out.println("Algorithm Name" + "\t\t" 
				+ "Min. Time (ms)\t" 
				+ "Max. Time (ms)\t"
				+ "Mean Time (ms)");
		
		// repeat the experiment for ten trials (heap sort)
		for (int a = 0; a < elapsedTimeArray.length; a++)
		{
			initialize(randomArray);
			
			// record the starting time
			startTime = System.currentTimeMillis();
			
			// run the algorithm
			HeapSort.sort(randomArray);
			
			// record the ending time
			endTime = System.currentTimeMillis();
			
			// compute the elapsed time
			elapsedTime = endTime - startTime;
			
			// store the elapsed times
			elapsedTimeArray[a] = elapsedTime;
		}
		
		// print results
		System.out.println("Heap-Sort\t\t\t" 
				+ minTimeSort(elapsedTimeArray) + "\t\t" 
				+ maxTimeSort(elapsedTimeArray) + "\t\t" 
				+ meanTime(elapsedTimeArray));
		
		// repeat the experiment for ten trials (in-place quick sort)
		for (int a = 0; a < elapsedTimeArray.length; a++)
		{
			initialize(randomArray);
			
			// record the starting time
			startTime = System.currentTimeMillis();
			
			// run the algorithm
			QuickSort.quickSortInPlace(randomArray, comp);
			
			// record the ending time
			endTime = System.currentTimeMillis();
			
			// compute the elapsed time
			elapsedTime = endTime - startTime;
			
			// store the elapsed times
			elapsedTimeArray[a] = elapsedTime;
		}
		
		// print results
		System.out.println("In-place Quick-Sort\t\t" 
				+ minTimeSort(elapsedTimeArray) + "\t\t" 
				+ maxTimeSort(elapsedTimeArray) + "\t\t" 
				+ meanTime(elapsedTimeArray));
		
		// repeat the experiment for ten trials (merge sort)
		for (int a = 0; a < elapsedTimeArray.length; a++)
		{
			initialize(randomArray);
			
			// record the starting time
			startTime = System.currentTimeMillis();
			
			// run the algorithm
			MergeSort.mergeSort(randomArray, comp);
			
			// record the ending time
			endTime = System.currentTimeMillis();
			
			// compute the elapsed time
			elapsedTime = endTime - startTime;
			
			// store the elapsed times
			elapsedTimeArray[a] = elapsedTime;
		}
		
		// print results
		System.out.println("Merge-Sort\t\t\t" 
				+ minTimeSort(elapsedTimeArray) + "\t\t" 
				+ maxTimeSort(elapsedTimeArray) + "\t\t" 
				+ meanTime(elapsedTimeArray));
	}
}

class MergeSort 
{
	  //-------- support for top-down merge-sort of arrays --------
	  /** Merge contents of arrays S1 and S2 into properly sized array S. */
	  public static void merge(int[] S1, int[] S2, int[] S, 
			  Comparator<Integer> comp) {
	    int i = 0, j = 0;
	    while (i + j < S.length) {
	      if (j == S2.length || (i < S1.length && comp.compare(S1[i], S2[j]) < 0))
	    	  // copy ith element of S1 and increment i
	    	  S[i+j] = S1[i++];
	      else
	    	// copy jth element of S2 and increment j
	        S[i+j] = S2[j++];
	    }
	  }

	  /** Merge-sort contents of array S. */
	  public static void mergeSort(int[] S, Comparator<Integer> comp) {
	    int n = S.length;
	    if (n < 2) return;                        // array is trivially sorted
	    // divide
	    int mid = n/2;
	    int[] S1 = Arrays.copyOfRange(S, 0, mid);   // copy of first half
	    int[] S2 = Arrays.copyOfRange(S, mid, n);   // copy of second half
	    // conquer (with recursion)
	    mergeSort(S1, comp);                      // sort copy of first half
	    mergeSort(S2, comp);                      // sort copy of second half
	    // merge results
	    // merge sorted halves back into original
	    merge(S1, S2, S, comp);
	  }
	  
	  //-------- support for bottom-up merge-sort of arrays --------
	  /** 
	   * Merges in[start..start+inc-1] and in[start+inc..start+2*inc-1] into out. 
	   */
	  public static void merge(int[] in, int[] out, Comparator<Integer> comp,
	                                                       int start, int inc) {
	    int end1 = Math.min(start + inc, in.length);      // boundary for run 1
	    int end2 = Math.min(start + 2 * inc, in.length);  // boundary for run 2
	    int x=start;                                      // index into run 1
	    int y=start+inc;                                  // index into run 2
	    int z=start;                                      // index into output
	    while (x < end1 && y < end2)
	      if (comp.compare(in[x], in[y]) < 0)
	        out[z++] = in[x++];                           // take next from run 1
	      else
	        out[z++] = in[y++];                           // take next from run 2
	    // copy rest of run 1
	    if (x < end1) 
	    	System.arraycopy(in, x, out, z, end1 - x);   
	    // copy rest of run 2
	    else if (y < end2) 
	    	System.arraycopy(in, y, out, z, end2 - y);
	  }
}

/*
 * Class for quick sort
 */
class QuickSort 
{
	  //-------- support for in-place quick-sort of an array --------
	  /** Quick-sort contents of a queue. */
	  public static void quickSortInPlace(int[] S, Comparator<Integer> comp) {
	    quickSortInPlace(S, comp, 0, S.length-1);
	  }

	  /** Sort the subarray S[a..b] inclusive. */
	  private static void quickSortInPlace(int[] S, Comparator<Integer> comp,
	                                                              int a, int b) 
	  {
	    if (a >= b) return;                // subarray is trivially sorted
	    int left = a;
	    int right = b-1;
	    int pivot = S[b];
	    int temp;                            // temp object used for swapping
	    while (left <= right) {
	      // scan until reaching value equal or larger than pivot 
	      // (or right marker)
	      while (left <= right && comp.compare(S[left], pivot) < 0) left++;
	      // scan until reaching value equal or smaller than pivot 
	      // (or left marker)
	      while (left <= right && comp.compare(S[right], pivot) > 0) right--;
	      if (left <= right) {             // indices did not strictly cross
	        // so swap values and shrink range
	        temp = S[left]; S[left] = S[right]; S[right] = temp;
	        left++; right--;
	      }
	    }
	    // put pivot into its final place (currently marked by left index)
	    temp = S[left]; S[left] = S[b]; S[b] = temp;
	    // make recursive calls
	    quickSortInPlace(S, comp, a, left - 1);
	    quickSortInPlace(S, comp, left + 1, b);
	  }
	}

/* 
 * Class for heap sort 
 */
class HeapSort 
{    
    private static int N;
    /* Sort Function */
    public static void sort(int arr[])
    {       
        heapify(arr);        
        for (int i = N; i > 0; i--)
        {
            swap(arr,0, i);
            N = N-1;
            maxheap(arr, 0);
        }
    }     
    
    /* Function to build a heap */   
    public static void heapify(int arr[])
    {
        N = arr.length-1;
        for (int i = N/2; i >= 0; i--)
            maxheap(arr, i);        
    }
    
    /* Function to swap largest element in heap */        
    public static void maxheap(int arr[], int i)
    { 
        int left = 2*i ;
        int right = 2*i + 1;
        int max = i;
        if (left <= N && arr[left] > arr[i])
            max = left;
        if (right <= N && arr[right] > arr[max])        
            max = right;
 
        if (max != i)
        {
            swap(arr, i, max);
            maxheap(arr, max);
        }
    }    
    
    /* Function to swap two numbers in an array */
    public static void swap(int arr[], int i, int j)
    {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp; 
    }    
}
