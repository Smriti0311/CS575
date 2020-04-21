import java.lang.*;
import java.io.*;
import java.util.*;
public class tspdp
{
	//these variables are final, 
	//these values are initialised once, 
	//cannot be modified later on, essentially constants
	 final int N ;
	 final int START_NODE ;
	 final int END_NODE ; 

	// formal parameter for the constructor,
	// actual parameter is the adjacency_matrix
	 double[][] distance_matrix;

	// the minimum tour cost, initialize to +∞
	 double minOptimalTourCost = Double.POSITIVE_INFINITY;

	// creating a array of integers for the tour 
	 	int [] optimalTour = new int[15];
	 
	// this constructor initialises an object of class tsp1 with 
	 	// the adjacency matrix and the start node
	public tspdp(int start, double[][] distance_matrix)
	{
		this.distance_matrix = distance_matrix;
		N = distance_matrix.length;
		START_NODE = start;
		END_NODE = (1 << N) - 1;
	}

	
	public void gettour()
	{
		int current_state = 1 << START_NODE;
		Double [][] dp_state = new Double[N][1 << N];
		Integer[][] prior = new Integer[N][1 << N];
		minOptimalTourCost = find_tsp(START_NODE, current_state, dp_state, prior);
		int position = START_NODE;
		int aa = 0;
		for(aa=0; ;aa++)
		{
			optimalTour[aa] = position;
			Integer next_position = prior[position][current_state];
			if(next_position == null) 
				break;
			int next_state = current_state  | (1 << next_position);
			current_state = next_state;
			position = next_position;
		}
		optimalTour[aa+1] = START_NODE;
		System.out.print("\tPrinting the Optimal Tour here: ");
		for( int ii = 0; ii < (N+1); ii++ )
		{
			System.out.print(" "+optimalTour[ii]);
		}
		System.out.println("\n");
	}

	public void getcost()
	{
		int current_state = 1 << START_NODE;
		Double [][] dp_state = new Double[N][1 << N]; // table filled with null values, memoized
		Integer[][] prior = new Integer[N][1 << N];
		System.out.println("\tThe optimal cost is:  "+(find_tsp(START_NODE, current_state, dp_state, prior)));
	}

	private double find_tsp(int i, int current_state, Double[][] dp_state, Integer [][] prior)
	{
		// if the tour is completed, return the cost of going back to the start node
		// from the end node
		if(current_state == END_NODE)
			return distance_matrix[i][START_NODE];

		// Look up table if already computed, i.e., DP
		if(dp_state[i][current_state] !=  null)
			return dp_state[i][current_state];

		double minimumCost = Double.POSITIVE_INFINITY;
		int loc = -1;
		for( int new_next = 0 ; new_next < N ; new_next++ )
		{
			// if the next node has already been visited, skip it
			if((current_state & (1 << new_next)) != 0)
				continue;

			int new_next_state = current_state | (1 << new_next);
			double new_cost = distance_matrix[i][new_next] + find_tsp(new_next, new_next_state, dp_state, prior);
			if(new_cost < minimumCost)
			{
				minimumCost = new_cost;
				loc = new_next;
			}
		}
		prior[i][current_state] = loc;
		return dp_state[i][current_state] = minimumCost;
	}


	public static void main(String [] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("\n\tEnter number of cities: ");
		int n = Integer.parseInt(br.readLine()); // l is capital in readline
		System.out.println();

		if(n < 0)
			throw new IllegalArgumentException("\n\tNumber of cities cannot be negative!!");

		if(n <= 2 && n>=0)
			throw new IllegalArgumentException("\n\tTSP with 2 or lesser cities is trivial!!");
		
		if(n >= 15)
			throw new IllegalArgumentException("\n\tComputation is explosive for N >= 15");
		

		// create the adjacency matrix which contains the distances between cities
		double [][] adjacency_matrix = new double[n][n];
		
		LinkedHashMap<Integer, String> lhm =  new LinkedHashMap<Integer, String>();

		System.out.println("\tEnter the cities: \n");
		for(int i=0;i<n;i++)
		{
			System.out.print("\t");
			lhm.put(i,br.readLine());
		}

		// Generating a Set of entries
		Set set = lhm.entrySet();

		// Displaying elements of LinkedHashMap
		Iterator it = set.iterator();
		System.out.println("\n\tThe cities are: \n");
		while(it.hasNext())
		{
			Map.Entry me = (Map.Entry)it.next();
			System.out.println("\t"+me.getKey()+"  "+me.getValue());
		}

		
		System.out.println("\n\tEnter the distances between the cities: \n");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(i == j)
				{
					adjacency_matrix[i][j] = 0.0;
				}
				else
				{
					if(i>j)
						continue;
					else
					{
					System.out.print("\tA["+i+"]"+"["+j+"]: ");
					adjacency_matrix[i][j] = adjacency_matrix[j][i] = Double.parseDouble(br.readLine());
					}
				}
			}
			System.out.println();
		}
		System.out.print("\tThe distance matrix is: \n\n");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.print("\t"+adjacency_matrix[i][j]);
			}
			System.out.println();
		}

		System.out.print("\n\tEnter the start node : ");
		int start_node = Integer.parseInt(br.readLine());
		if(start_node < 0 || start_node >= n)
			throw new IllegalArgumentException("\n\tStarting node must be satisfy the condition:  0 <= S < N");
		System.out.println(" ");

		tspdp tspobj = new tspdp(start_node,adjacency_matrix);
		 
		tspobj.gettour();

		tspobj.getcost();

		System.out.println(" ");
	}
}




/*

smritirao@Smriti-2 Desktop % javac tsp1.java
smritirao@Smriti-2 Desktop % java tsp1      

	Enter number of cities: 4

	Enter the cities: 

	Mumbai
	Pune
	Delhi
	Goa

	The cities are: 

	0  Mumbai
	1  Pune
	2  Delhi
	3  Goa

	Enter the distances between the cities: 

	A[0][1]: 10
	A[0][2]: 15
	A[0][3]: 20

	A[1][2]: 35
	A[1][3]: 25

	A[2][3]: 30


	The distance matrix is: 

	 0.0	10.0	15.0	20.0
	10.0	 0.0	35.0	25.0
	15.0	35.0	 0.0	30.0
	20.0	25.0	30.0	 0.0

	Enter the start node : 2
 
	Printing the Optimal Tour here:  2 0 1 3 2

	The optimal cost is:  80.0
 


If start node is 0, path is 0 1 3 2 0
If start node is 1, path is 1 0 2 3 1 
If start node is 2, path is 2 0 1 3 2 
If start node is 3, path is 3 1 0 2 3

Cost is 80.

*/