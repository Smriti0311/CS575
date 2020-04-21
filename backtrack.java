import java.lang.*;
import java.io.*;
import java.util.*;

public class backtrack  
{
    // to hold optimal path
    public static List<Integer> path = new ArrayList<Integer>(); 
    // start node
    public static int start;
    // to hold optimum cost
    public static double sum = Double.POSITIVE_INFINITY; 
    // total cities
    public static int n;
    // counter variable
    public static int path_length = 1;
    // create the adjacency matrix which contains the distances between cities
    public static double[][] adj;
    // to  see if city is seen or not
    public static String[] seen;
    

    static double tsp(int now_at, double cost)  
    {    

        // if at last node(cnt == n), add to the cost, the value  at adj[now_at][start]
        // and go through backtraking code to find remaining possible values
        if (path_length == n)  
        { 
            if(sum > (cost + adj[now_at][start]))
            {
                sum = (cost + adj[now_at][start]);
                return sum;
            }
            return sum; 
        } 
        

        // backtrack here
        for (int i = 0; (i < n); i++)  
        { 
            if (seen[i] == "no" && adj[now_at][i] > 0)  
            {
                seen[i] = "yes";
                path.add(now_at);
                path_length += 1;
                sum = tsp(i, cost + adj[now_at][i]); 
                path.add(now_at);
                seen[i] = "no"; 
            } 
        } 
        return sum; 
    } 
  
    
    public static void main(String[] args) throws IOException
    { 
  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("\n\tEnter number of cities: ");
        n = Integer.parseInt(br.readLine()); // l is capital in readline
        System.out.println();

        if(n < 0)
            throw new IllegalArgumentException("\n\tNumber of cities cannot be negative!!");

        if(n <= 2 && n>=0)
            throw new IllegalArgumentException("\n\tTSP with 2 or lesser cities is trivial!!");
        
        if(n >= 15)
            throw new IllegalArgumentException("\n\tComputation is explosive for N >= 15");
        

        // initialise adjacency matrix
        adj = new double[n][n];
        
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
                    adj[i][j] = 0.0;
                }
                else
                {
                    if(i>j)
                        continue;
                    else
                    {
                    System.out.print("\tA["+i+"]"+"["+j+"]: ");
                    adj[i][j] = adj[j][i] = Double.parseDouble(br.readLine());
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
                System.out.print("\t"+adj[i][j]);
            }
            System.out.println();
        }

        System.out.print("\n\tEnter the start node : ");
        int start_node = Integer.parseInt(br.readLine());
        start = start_node;
        if(start_node < 0 || start_node >= n)
            throw new IllegalArgumentException("\n\tStarting node must be satisfy the condition:  0 <= S < N");
        System.out.println(" ");

        //String [] 
        seen = new String[n];
        for( int i = 0 ; i < n ; i++)
        {
            if(i != start_node)
                seen[i] = "no";
        }
        seen[start_node] = "yes";

        backtrack tspbackobj =  new backtrack();
        
        System.out.println("\tThe optimal cost : "+tspbackobj.tsp(start_node, 0.0));
        
        
        for( int i = 0 ; i < path.size()-2; i++ )
        {
            for(int j = i+1 ; j < path.size() ;  j++ )
            {
                if(path.get(i) == path.get(j))
                    path.set(j,-1);
            }
        }
        
        System.out.print("\n\tThe optimal path : ");

        for( int i: path)
        {
            if( i != -1 )
                System.out.print("  "+i);
        }
        System.out.print("  "+start_node+"\n\n");


        
    } 
} 