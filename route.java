import java.lang.*;

// an edge between 2 "person" objects
public class route
{
	double distance;

	person p1;  // not required for Homework 3, but can use if implementing priority queue
	person p2;  // not required for Homework 3, but can use if implementing priority queue

	public route( double d )
	{
		distance = d;

		p1 = null;
		p2 = null;
	}

	public route( int x1, int y1, int x2, int y2 )
	{
		double r1 = Math.pow((double)x1-(double)x2, 2);
		double r2 = Math.pow((double)y1-(double)y2, 2);
		distance = Math.sqrt( r1+ r2 );

		p1 = null;
		p2 = null;
	}

	// copy constructor
	route( route in )
	{
		this.distance = in.distance;

		p1 = null;
		p2 = null;
	}

	// set distance member field
	public boolean setDistance( double x )
	{
		if ( x <= Double.MAX_VALUE && x >= Double.MIN_VALUE )
		{
			distance = x;
			return true;
		}
		else
		{
			return false;
		}
		
	}
}
