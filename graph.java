import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

/*
I chose my tours in a DFS manner. My code started with a vertex and iterated through it's keyset until it's
first child was found(adjacent vertex), then I pushed that child onto the stack and broke out of the loop.
Then it ran again over and over until the vertex reached had no more adjacent unvisited vertices. Once a vertex
with no adjacent vertex had been reached, it popped it off the stack and tried to find another adjacent unvisited
vertex to the popped vertex's parent and started the process over again. In other words, my tours were chosen by
exploring as far as possible on one given route until there was no where to go, then it backtracked accordingly
and explored other paths until all vertexes were visited, thus completing the tour.

The chosen tour distances are worse than the "best" case tour because I used a DFS approach. My code essentially
tried to find A route to all of the vertex's, not the most EFFICIENT route. The "best" tour would have needed to
take into account all possible routes through all of the vertexes from one specific vertex and record the shortest
one.
 */
public class graph
{
	static int dim;
	HashMap<person, HashMap<person, route>> matrix;

	// result of MST
	HashMap<person, HashMap<person, route>> result;

	// best weight
	double bestweight;

	graph( int dim )
	{
		matrix = new HashMap<person, HashMap<person, route>>( dim );
		result = new HashMap<person, HashMap<person, route>>( dim );

		bestweight = Double.MAX_VALUE;
	}

	// inserts unique vertex
	// vertex = person (x coordinate, y coordinate, fare price)
	public boolean insertvertex( person p )
	{
		if ( !matrix.containsKey( p ) )
		{
			matrix.put( p, new HashMap<person, route>( dim ) );

			if ( !result.containsKey( p ) )
			{
				// setting result row as well
				result.put( p, new HashMap<person, route>( dim ) );
			}

			return true;
		}
		else
		{
			System.out.println( "Already inserted " + p );
			return false;
		}
	}

	// this method creates the actual graph (matrix variable) by setting edges/routes
	// assumes all vertices have been inserted already
	// generates an edge from each vertex, to every other vertex
	// edges are undirected, weighted with distance between two vertices
	public void generateroutes()
	{
		for ( person row : matrix.keySet() )
		{
			for ( person column : matrix.keySet() )
			{
				// create route for each possible pair of vertices in this row
				matrix.get(row).put( column, new route( row.xpos, row.ypos, column.xpos, column.ypos ) );
				matrix.get(column).put( row, new route( column.xpos, column.ypos, row.xpos, row.ypos ) );
			}
		}
	}

	// finds the minimum spanning tree
	// stores answer in "result" (a member field of graph)
	// use Prim's algorithm
	public void mst( person start )
	{
		// deep copy of original matrix
		HashMap<person, HashMap<person, route>> tempmatrix = generateUnknown();
		// set of unknown persons from original matrix
		Set<person> unknown = tempmatrix.keySet();
		// empty set of known persons
		Set<person> known = new HashSet<person>();

		person current = start;  // the newest known vertex
		person leastvertex = null;  // smallest unknown weight seen in an iteration
		tempmatrix.get( current ).get( current ).setDistance( 0.0 );  // setting starting point distance to 0
		while ( !unknown.isEmpty() )
		{
			// in case you need this: Double.MAX_VALUE is the maximum value of a double defined in Java, use this instead of the usual "999" we usually use

			// NOTE: result should store 1 set of routes, storing dual results will be INCORRECT
			// i.e. either the upper triangle or the lower triangle of the result matrix should be filled

			double mindist = Double.MAX_VALUE;
			double tempdist;
			leastvertex = null;
	
			// add current to known set
			known.add( current );
			// remove current from unknown set
			unknown.remove( current );

			// iterate through known->unknown edges
			for ( person p : known )
			{
				for ( person j : unknown )
				{
					// simultaneously update unknown distances and set next smallest weighted vertex (for the next iteration)
					double unknownweight = tempmatrix.get(j).get(p).distance;  // using bottom half of tempmatrix because the known rows will be removed (which exhibit as null reference errors)
					tempdist = matrix.get(p).get(j).distance;

					// update unknown weight with lesser value
					if ( tempdist < unknownweight && tempdist > 0 )
					{
						tempmatrix.get(j).get(p).setDistance( tempdist );
						unknownweight = tempdist;
					}

					// check whether new distance is the smallest seen so far
					// update "parent" (leastvertex) if so
					if ( unknownweight < mindist && unknownweight > 0 )
					{
						mindist = unknownweight;
						leastvertex = j;
						current = p;
					}
				}
			}

			// check whether a leastvertex was found, then update result
			if ( leastvertex != null )
			{
				// setting result from known->unknown
				result.get( current ).put( leastvertex, new route( current.xpos, current.ypos, leastvertex.xpos, leastvertex.ypos ) );
				result.get( leastvertex ).put( current, new route( leastvertex.xpos, leastvertex.ypos, current.xpos, current.ypos ) );

				// set newest known vertex as current (for addition to "known" and removal from "unknown" in the next iteration)
				current = leastvertex;
			}
		}


		// generate 10 tours (on the graph with 30 people) with MST
		// if testing with the 6 person graph, try 3 tours
		int tourcount = 0;
		double totalDistance;
		for ( person zz : result.keySet() )
		{
			ArrayList<person> temptour = constructtour( zz );
			// YOUR CODE HERE
			// print tour & total weight
			totalDistance = 0;
			for(int i = 0; i < temptour.size() ; i++){
				person a = temptour.get(i);
				person b = a;

				if( i != temptour.size() - 1) {
					 b = temptour.get(i + 1);
					}

				else{

				}
				double distance = matrix.get(a).get(b).distance;
				totalDistance += distance;
				System.out.print( a.xpos + ", " + a.ypos);
				//System.out.print( " " + distance + " ");
				if( i != temptour.size() - 1){
					System.out.print(" - ");
				}
				else{
					System.out.print( " " + totalDistance );
				}
			}

			// increment tourcount
			// if tourcount is 10 (or 3), break for loop
			System.out.println();

			tourcount += 1;
			if(tourcount == 10){
				break;
			}

		}

		// find best
		person[] original = matrix.keySet().toArray( new person[dim] );
		permute( original, 0, original.length-1 );
		System.out.println( "BEST " + bestweight );
	}

	// given a starting person, construct a tour with MST
	public ArrayList<person> constructtour( person start )
	{
		ArrayList<person> tour = new ArrayList<person>();
		if ( result.get( start ).size() == 0 )
		{
			System.out.println( "START PERSON ON TOUR HAS NO ADJACENT VERTICES" );
			System.exit( 0 );
		}
		else
		{
			// find a valid tour with depth first search traversal (pre-order)
			Set<person> visited = new HashSet<person>();  // hash table to keep track of visited persons
			Stack<person> dfs = new Stack<person>();  // stack for DFS (pre-order traversal)
			dfs.push( start );
			while ( !dfs.empty() )
			{
				// YOUR CODE HERE
				// peek top of dfs, if not visited put into tour & visited
				person current = dfs.peek();
				if( !visited.contains( current ))
				{
					visited.add(current);
					tour.add(current);
				}
				else{
				}
				// if top has at least 1 adjacent vertex
				if( result.get( current ).size() > 0 )
					// search for unvisited adjacent vertices, push an unvisited adjacent onto stack, pop if no more unvisited adjacent vertices
				{
					for( person a : result.get( current ).keySet())
					{
						if( !visited.contains( a ))
						{
							dfs.push( a );
							break;
						}
						else{
						}
					}
					if( dfs.peek() == current )
					{
						dfs.pop();
					}
					else{

					}
				}
				else{//for if current does not have any children
					dfs.pop();
				}
				// else no adjacent vertices, pop
			}

			// adding start to end of tour
			tour.add( start );
		}

		return tour;
	}

	public void permute( person [] q, int left, int right )
	{
		if ( left == right )
		{
			double sumweight = 0.0;
			for ( int ii = 0; ii < q.length; ++ii )
			{
				if ( ii < q.length-1 )
				{
					sumweight += matrix.get( q[ii] ).get( q[ii+1] ).distance;
				}

				// add starting vertex to end
				if ( ii == q.length-1 )
				{
					sumweight += matrix.get( q[ii] ).get( q[0] ).distance;
				}
			}

			if ( sumweight < bestweight && sumweight > 0.0 )
			{
				bestweight = sumweight;
			}
		}
		else
		{
			for ( int jj = left; jj <= right; ++jj )
			{
				// swap
				person temp = q[left];
				q[left] = q[jj];
				q[jj] = temp;
				// permute
				permute( q, left+1, right );
				// swap back (backtrack)
				temp = q[left];
				q[left] = q[jj];
				q[jj] = temp;
			}
		}
	}

	// NOT NEEDED, but can help with debugging
	// creates a deep copy of a HashMap<person, HashMap<person, route>> matrix
	public HashMap<person, HashMap<person, route>> deepcloneHM( HashMap<person, HashMap<person, route>> input )
	{
		HashMap<person, HashMap<person, route>> copy = new HashMap<person, HashMap<person, route>>();
		for ( Map.Entry<person, HashMap<person, route>> E : input.entrySet() )
		{
			copy.put( E.getKey(), new HashMap<person, route>( dim ) );


			for ( Map.Entry<person, route> F : (E.getValue()).entrySet() )
			{
				// copy constructor of route used here
				copy.get( E.getKey() ).put( F.getKey(), new route( F.getValue() ) );
			}
		}

		return copy;
	}

	// creates initial "unknown" matrix
	// sets ALL route.distance to Double.MAX_VALUE
	// remember to set starting route.distance to 0
	public HashMap<person, HashMap<person, route>> generateUnknown()
	{
		HashMap<person, HashMap<person, route>> temp = new HashMap<person, HashMap<person, route>>( dim );
		for ( person row : matrix.keySet() )
		{
			temp.put( row, new HashMap<person, route>( dim ) );
		}

		for ( person row : matrix.keySet() )
		{
			for ( person column : matrix.keySet() )
			{
				// create route for each possible pair of vertices in this row
				temp.get(row).put( column, new route( Double.MAX_VALUE ) );
				temp.get(column).put( row, new route( Double.MAX_VALUE ) );
			}
		}

		return temp;
	}

	// prints "result"
	public void printresult()
	{
		double totalweight = 0.0;
		System.out.println( "SOLUTION:" );
		for ( person p : result.keySet() )
		{
			for ( person j : result.get(p).keySet() )
			{
				System.out.println( "s " + p + " " + p.xpos + ", " + p.ypos );
				System.out.println( "d " + j + " " + j.xpos + ", " + j.ypos );
				System.out.println( "l " + result.get(p).get(j).distance );
				totalweight += result.get(p).get(j).distance;
			}
		}

		System.out.println( "t " + totalweight );
	}

	// use this to help with debugging
	// prints a HashMap matrix (any matrix passed to this method)
	public void print( HashMap<person, HashMap<person, route>> in )
	{
		double totalweight = 0.0;
		for ( person p : in.keySet() )
		{
			for ( person j : in.get(p).keySet() )
			{
				System.out.print( in.get(p).get(j).distance + " " );
				totalweight += in.get(p).get(j).distance;
			}
			System.out.print( "\n" );
		}

		System.out.println( "t " + totalweight );
	}
}
