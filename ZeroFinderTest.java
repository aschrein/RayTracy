import static org.junit.Assert.*;
import model.ZeroFinder;
import org.junit.Test;


public class ZeroFinderTest
{
	@Test
	public void test()
	{
		float d = ZeroFinder.find( ( x ) -> ( float )( Math.sin( x ) * Math.exp( x * 0.5f + 1.0 ) ) , 200 , 10 ,
				1.0e-3f , 5.0f );
		System.out.println( d );
		assertTrue( Math.abs( d - Math.PI ) < 1.0e-3f );
	}
}
