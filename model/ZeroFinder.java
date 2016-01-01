package model;
import linalg.Ray;
public class ZeroFinder
{
	public static interface Func1D
	{
		float get( float x );
	}
	private static float findZero( Func1D func , float x0 , float x1 ,
			int max_samples , int cur_sample )
	{
		float mid = ( x0 + x1 ) * 0.5f;
		/*
		 * if( cur_sample == max_samples ) { return surface.getDist( ray.getPos(
		 * mid ) ) < 1.0e-1f ? mid : -1.0f; }
		 */
		if( cur_sample >= max_samples )
		{
			return mid;
		}
		float midv = func.get( mid );
		float EPS = 1.0e-5f;
		if( Math.abs( midv ) < EPS )
		{
			return mid;
		}
		float a , b;
		float nx0 , nx1;
		if( ( a = func.get( x0 ) ) * ( b = midv ) <= 0.0f )
		{
			nx0 = x0;
			nx1 = mid;
			/*
			 * if( Math.abs( a ) < EPS ) /{ return nx0; }
			 */
		} else if( ( a = midv ) * ( b = func.get( x1 ) ) <= 0.0f )
		{
			nx0 = mid;
			nx1 = x1;
			/*
			 * if( Math.abs( b ) < EPS ) { return nx1; }
			 */
		} else
		{
			return -1.0f;
		}
		/*float dr = 1.0e-5f;
		float der0 = ( func.get( nx0 + dr ) - func.get( nx0 - dr ) )
				/ ( 2.0f * dr );
		float der1 = ( func.get( nx1 + dr ) - func.get( nx1 - dr ) )
				/ ( 2.0f * dr );
		float d0 = 0.0f;
		float d1 = 0.0f;
		if( a * der0 < -1.0e-4f )
		{
			d0 = a / der0;
			float x = nx0 + d0;
			if( Math.abs( func.get( x ) ) < EPS )
			{
				return x;
			}
		} else if( b * der1 > 1.0e-4f )
		{
			d1 = b / der1;
			float x = nx0 + d0;
			if( Math.abs( func.get( x ) ) < EPS )
			{
				return x;
			}
		}*/
		return findZero( func , nx0 , nx1 , max_samples , cur_sample + 1 );
	}
	public static float find( Func1D func , int march_count ,
			int samples_count , float x1 , float x2 )
	{
		float mdr = ( x2 - x1 ) / march_count;
		float dr = mdr;
		for( float r = x1; r < x2; r += dr )
		{
			float d;
			dr = Math.max( mdr , func.get( r ) );
			if( ( d = findZero( func , r , r + dr , samples_count , 1 ) ) > 0.0f )
			{
				return d;
			}
		}
		return -1.0f;
	}
}
