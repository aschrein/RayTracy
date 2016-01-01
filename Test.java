import static org.junit.Assert.*;
import implementation.Body3D;
import implementation.Sphere;
import linalg.Float3;
import linalg.Ray;
import linalg.VectorFactory;
import model.Collision;
import model.Material;
import model.Surface;
import model.ZeroFinder;
public class Test
{
	@org.junit.Test
	public void test()
	{
		Surface surface = new Surface()
		{
			@Override
			public float getDist( Float3 r )
			{
				return -( float )Math.exp( -Math.pow( 1.4f * Math.abs( r.z ) - 2.0f , 2.0 ) )
						 -( float )( 1.0f + Math.sin( r.z * 10.0f ) ) * 0.1f
						+ r.xy().mod();
			}
			@Override
			public Material getMat()
			{
				return null;
			}
		};
		double err = 0.0f;
		long t1 = System.currentTimeMillis();
		final int N = 40000;
		int c = 0;
		for( int i = 0; i < N; i++ )
		{
			c++;
			Ray test_ray = new Ray(
					new Float3( VectorFactory.getRandomCircle().mul( 1.0f ) , 4.0f ) ,
					new Float3( 0.0f, 0.0f, -1.0f ) );
			float d = ZeroFinder.find( ( x ) -> surface.getDist( test_ray.getPos( x ) ) , 200 , 10 ,
					1.0e-3f , 5.0f );
			if( d < 0.0f )
			{
				continue;
			}
			//float real = ( float )( 5.0 - Math.sqrt( 1.0 - test_ray.pos.xy().mod2() ) );\
			Float3 pos = test_ray.getPos( d );
			float cur_err = Math.abs( surface.getDist( pos ) );
			err += cur_err;
			//float sgn = Math.signum( test_ray.pos.xy().mod2() - 1.0f );
			//System.out.println( i + "->err:" + cur_err );
			assertTrue( cur_err < 1.0e-2f );
			Float3 n = new Collision( test_ray , d , surface ).norm;
			//Float3 nr = pos.norm();
			//assertTrue( n.xy().dot( pos.xy() ) > 0.0f );
			System.out.println( "success:" + ( double )i / N * 100.0 + "%" );
		}
		System.out.println( "avg time:" + ( double )( System.currentTimeMillis() - t1 ) / c * 1.0e3 + "us" );
		//0.0076527738571166995
		System.out.println( "avg err:" + err / N );
	}
}
