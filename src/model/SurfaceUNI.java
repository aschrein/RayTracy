/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import linalg.Float3;
/**
 *
 * @author anton
 */
public class SurfaceUNI implements Surface
{
	private final Surface s1;
	private final Surface s2;
	private final float r;
	public SurfaceUNI( Surface s1 , Surface s2 , float r )
	{
		this.s1 = s1;
		this.s2 = s2;
		this.r = r;
	}
	
	@Override
	public float getDist( Float3 pos )
	{
		float r1 = s1.getDist( pos );
		float r2 = s2.getDist( pos );
		return FieldMath.smoothMin( r1 , r2 , r );
	}
	@Override
	public Material getMat( Float3 pos )
	{
		float r1 = s1.getDist( pos );
		float r2 = s2.getDist( pos );
		float l = FieldMath.getPart( r2 , r1 , r );
		return s1.getMat( pos ).lerp( s2.getMat( pos ) , l );
	}
}
