package model;
import linalg.Ray;
public class ZeroFinder
{
	public static interface Func1D
	{
		float get( float x );
	}
	private static float findZero( Func1D func , float x0 , float x1 , float a , float b , int max_depth , int cur_depth )//, int samples )
	{
		/*if( cur_depth >= max_depth )
		 {
		 return ( x0 + x1 ) * 0.5f;
		 }
		 float dr = 1.0e-5f;
		 float der0 = ( func.get( x0 + dr ) - func.get( x0 - dr ) )
		 / ( 2.0f * dr );
		 der0 = der0 > -1.0f ? -1.0f : der0;
		 float der1 = ( func.get( x1 + dr ) - func.get( x1 - dr ) )
		 / ( 2.0f * dr );
		 der1 = der1 > -1.0f ? -1.0f : der1;
		 float a = func.get( x0 ), b = func.get( x1 );
		 float nx0 = x0 - a / der0;
		 float nx1 = x1 - b / der1;
		 if(  )
		 //return x0 - ( x1 - x0 ) / ( b - a ) * a;
		 x0 = nx0 > x0 && nx0 < x1 ? nx0 : x0;
		 x1 = nx1 > x0 && nx1 < x1 ? nx1 : x1;
		 return findZero( func , x0 , x1 , max_depth , cur_depth + 1 );*/
		/*float mid = ( x0 + x1 ) * 0.5f;
		 if( cur_depth >= max_depth )
		 {
		 return mid;
		 }
		 float midv = func.get( mid );
		 float EPS = 1.0e-5f;
		 if( Math.abs( midv ) < EPS )
		 {
		 return mid;
		 }
		 float dx = ( x1 - x0 ) / samples;
		 float a = func.get( x0 );
		 for( float x = x0 + dx; x <= x1 + dx; x += dx )
		 {
		 float b = func.get( x );
		 if( a * b <= 0.0f )
		 {
		 return findZero( func , x - dx , x , max_depth , cur_depth + 1 , 4 );
		 }
		 a = b;
		 }
		 return -1.0f;*/
		//float a = func.get( x0 ), b = func.get( x1 );
		float mid = x0 - ( x1 - x0 ) / ( b - a ) * a;//( x0 + x1 ) * 0.5f;
		float midv = func.get( mid );
		float EPS = 1.0e-5f;
		if( cur_depth >= max_depth || Math.abs( midv ) < EPS )
		{
			return mid;
		}
		float nx0 = x0, nx1 = x1;
		/*if( Math.abs( a ) < EPS )
		 {
		 return nx0;
		 }
		 if( Math.abs( b ) < EPS )
		 {
		 return nx1;
		 }*/
		if( a * midv <= 0.0f )
		{
			nx0 = x0;
			nx1 = mid;
			b = midv;
		} else if( midv * b <= 0.0f )
		{
			nx0 = mid;
			nx1 = x1;
			a = midv;
		}
		/*float dr = 1.0e-5f;
		 float der0 = ( func.get( nx0 + dr ) - func.get( nx0 - dr ) )
		 / ( 2.0f * dr );
		 float der1 = ( func.get( nx1 + dr ) - func.get( nx1 - dr ) )
		 / ( 2.0f * dr );*/
		/*if( der0 > -1.0f )
		 {
		 der0 = -1.0f;
		 }
		 if( der1 > -1.0f )
		 {
		 der1 = -1.0f;
		 }*/
		/*float mx0 = nx0 - 0.5f * a / der0;
		 float mf0 = func.get( mx0 );
		 if( a * mf0 <= 0.0f && mx0 < nx1 )
		 {
		 return findZero( func , nx0 , mx0 , max_depth , cur_depth + 1 );
		 }
		 float mx1 = nx1 - 0.5f * b / der1;
		 float mf1 = func.get( mx1 );
		 if( mf1 * b <= 0.0f && mx1 > nx0 )
		 {
		 return findZero( func , mx1 , nx1 , max_depth , cur_depth + 1 );
		 }
		 if( mx1 > mx0 && mx0 > nx0 && mx0 < nx1 && mx1 < nx1 && mx1 < nx0 )
		 {
		 return findZero( func , mx0 , mx1 , max_depth , cur_depth + 1 );
		 } else*/
		{
			return findZero( func , nx0 , nx1 , a , b , max_depth , cur_depth + 1 );
		}
	}
	private static float der( Func1D func , float x )
	{
		float dr = 1.0e-5f;
		return ( func.get( x + dr ) - func.get( x - dr ) )
				/ ( 2.0f * dr );
	}
	public static float find( Func1D func )
	{
		float x1 = 20.0f;
		float x0 = 1.0e-4f;
		float dx = 5.0e-3f;
		float a = func.get( x0 );
		for( float x = x0 + dx; x <= x1 + dx; x += dx )
		{
			float b = func.get( x );
			if( a * b <= 0.0f )
			{
				return findZero( func , x - dx , x , a , b , 5 , 1 );
			}
			a = b;
			dx = Math.min( 1.0e-1f , Math.max( 1.0e-3f , 100.0f * Math.abs( b / der( func , x ) ) ) );
		}
		/*dx = 10.0e-1f;
		 for( float x = x0 + dx; x <= x1 + dx; x += dx )
		 {
		 float b = func.get( x );
		 if( a * b <= 0.0f )
		 {
		 return findZero( func , x - dx , x , 4 , 1 );
		 }
		 a = b;
		 }
		 dx = 1.0e-1f;
		 for( float x = x0 + dx; x <= x1 + dx; x += dx )
		 {
		 float b = func.get( x );
		 if( a * b <= 0.0f )
		 {
		 return findZero( func , x - dx , x , 4 , 1 );
		 }
		 a = b;
		 }*/
		return -1.0f;
	}
}
