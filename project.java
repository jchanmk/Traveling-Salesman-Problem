public class project
{
	public static void main ( String[] args )
	{
		// small test graph, comment in to test
	/*
		person t1 = new person( 9, 10, 4.25 );
		person t2 = new person( 18, 34, 12.94 );
		person t3 = new person( 29, 21, 5.08 );
		person t4 = new person( 11, 30, 18.95 );
		person t5 = new person( 38, 34, 20.44 );
		person t6 = new person( 24, 21, 16.61 );
		graph test = new graph( 6 );
		test.insertvertex( t1 );
		test.insertvertex( t2 );
		test.insertvertex( t3 );
		test.insertvertex( t4 );
		test.insertvertex( t5 );
		test.insertvertex( t6 );
		test.generateroutes();
		test.mst( t3 );
	*/

		// small test graph should print:
		/*
		29, 21 - 24, 21 - 18, 34 - 11, 30 - 9, 10 - 38, 34 - 29, 21 100.934278804096
		18, 34 - 24, 21 - 29, 21 - 38, 34 - 9, 10 - 11, 30 - 18, 34 100.93427880409601
		24, 21 - 29, 21 - 38, 34 - 18, 34 - 11, 30 - 9, 10 - 24, 21 87.5744725291205
		11, 30 - 18, 34 - 24, 21 - 29, 21 - 38, 34 - 9, 10 - 11, 30 100.93427880409601
		9, 10 - 24, 21 - 29, 21 - 38, 34 - 18, 34 - 11, 30 - 9, 10 87.5744725291205
		38, 34 - 29, 21 - 24, 21 - 18, 34 - 11, 30 - 9, 10 - 38, 34 100.93427880409601
		BEST 87.5744725291205
		*/

		// larger graph

		person p1 = new person( 9, 10, 4.25 );
		person p2 = new person( 18, 34, 12.94 );
		person p3 = new person( 29, 21, 5.08 );
		person p4 = new person( 11, 30, 18.95 );
		person p5 = new person( 38, 10, 0.71 );
		person p6 = new person( 16, 2, 10.41 );
		person p7 = new person( 22, 23, 13.88 );
		person p8 = new person( 39, 5, 5.19 );
		person p9 = new person( 32, 31, 20.81 );
		person p10 = new person( 39, 27, 12.12 );
		person p11 = new person( 2, 37, 3.44 );

		graph mygraph = new graph( 11 );

		mygraph.insertvertex( p1 );
		mygraph.insertvertex( p2 );
		mygraph.insertvertex( p3 );
		mygraph.insertvertex( p4 );
		mygraph.insertvertex( p5 );
		mygraph.insertvertex( p6 );
		mygraph.insertvertex( p7 );
		mygraph.insertvertex( p8 );
		mygraph.insertvertex( p9 );
		mygraph.insertvertex( p10 );
		mygraph.insertvertex( p11 );

		mygraph.generateroutes();
		mygraph.mst( p2 );


		// large graph solution
		/*
		18, 34 - 11, 30 - 2, 37 - 22, 23 - 29, 21 - 32, 31 - 39, 27 - 38, 10 - 39, 5 - 9, 10 - 16, 2 - 18, 34 164.89460080475467
		39, 5 - 38, 10 - 29, 21 - 32, 31 - 39, 27 - 22, 23 - 18, 34 - 11, 30 - 2, 37 - 9, 10 - 16, 2 - 39, 5 148.16483946512005
		11, 30 - 18, 34 - 22, 23 - 29, 21 - 32, 31 - 39, 27 - 38, 10 - 39, 5 - 9, 10 - 16, 2 - 2, 37 - 11, 30 157.81990405018564
		9, 10 - 16, 2 - 22, 23 - 29, 21 - 32, 31 - 39, 27 - 38, 10 - 39, 5 - 18, 34 - 11, 30 - 2, 37 - 9, 10 163.54324644777398
		29, 21 - 32, 31 - 39, 27 - 38, 10 - 39, 5 - 22, 23 - 18, 34 - 11, 30 - 2, 37 - 9, 10 - 16, 2 - 29, 21 158.10304489415776
		16, 2 - 9, 10 - 22, 23 - 29, 21 - 32, 31 - 39, 27 - 38, 10 - 39, 5 - 18, 34 - 11, 30 - 2, 37 - 16, 2 169.89119537876147
		32, 31 - 29, 21 - 38, 10 - 39, 5 - 22, 23 - 18, 34 - 11, 30 - 2, 37 - 9, 10 - 16, 2 - 39, 27 - 32, 31 166.23517556826664
		22, 23 - 29, 21 - 32, 31 - 39, 27 - 38, 10 - 39, 5 - 18, 34 - 11, 30 - 2, 37 - 9, 10 - 16, 2 - 22, 23 163.54324644777395
		38, 10 - 29, 21 - 32, 31 - 39, 27 - 22, 23 - 18, 34 - 11, 30 - 2, 37 - 9, 10 - 16, 2 - 39, 5 - 38, 10 148.16483946512008
		2, 37 - 11, 30 - 18, 34 - 22, 23 - 29, 21 - 32, 31 - 39, 27 - 38, 10 - 39, 5 - 9, 10 - 16, 2 - 2, 37 157.81990405018564
		BEST 140.7974161202021
		*/
	}
}
