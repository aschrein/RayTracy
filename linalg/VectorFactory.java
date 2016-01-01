package linalg;

import model.Material;

public class VectorFactory
{
	public static Float3 getRandomSphere()
	{
		double phi = Math.random() * 2.0 * Math.PI;
		float cost = 2.0f * ( float )Math.random() - 1.0f;
		float sint = ( float )Math.sqrt( 1.0 - cost * cost );
		return new Float3(
				sint * ( float )Math.sin( phi ) ,
				sint * ( float )Math.cos( phi ) ,
				cost );
	}
	public static Float2 getRandomCircle()
	{
		double phi = Math.random() * 2.0 * Math.PI;
		float r = ( float )Math.sqrt( Math.random() );
		return new Float2(
				r * ( float )Math.sin( phi ) ,
				r * ( float )Math.cos( phi ) );
	}
	public static Float3 getRandomHalfSphere()
	{
		double phi = Math.random() * 2.0 * Math.PI;
		float cost = ( float )Math.random();
		float sint = ( float )Math.sqrt( 1.0 - cost * cost );
		return new Float3(
				sint * ( float )Math.sin( phi ) ,
				sint * ( float )Math.cos( phi ) ,
				cost );
	}
	public static Float3 getRandomHalfSphereCos()
	{
		double phi = Math.random() * 2.0 * Math.PI;
		float cost = ( float )Math.sin( Math.random() * Math.PI * 0.5 );
		float sint = ( float )Math.sqrt( 1.0 - cost * cost );
		return new Float3(
				sint * ( float )Math.sin( phi ) ,
				sint * ( float )Math.cos( phi ) ,
				cost );
	}
	public static Float3 produce( Float3 n , Float3 tg , Float3 bn , Float3 k )
	{
		return n.mul( k.z ).add( tg.mul( k.x ) ).add( bn.mul( k.y ) );
	}
	public static Float3 reflectl( Float3 n , Float3 v )
	{
		return v.sub( n.mul( n.dot( v ) * 2.0f ) );
	}
	public static Float3 lerp( Float3 a , Float3 b , float k )
	{
		return a.mul( 1.0f - k ).add( b.mul( k ) );
	}
	public static Float3 reflect( Float3 v , Float3 n , float roughness )
	{
		Float3 tg = v.sub( n.mul( n.dot( v ) ) ).norm();
		Float3 bn = n.vecx( tg ).norm();
		Float3 reflect = reflectl( n , v );
		Float3 random = VectorFactory.getRandomHalfSphereCos();
		return lerp( reflect , VectorFactory.produce( n , tg , bn , random ) , roughness );
	}
}
