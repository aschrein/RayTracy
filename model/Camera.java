/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import linalg.Float2;
import linalg.Float3;
import linalg.Float4;
import linalg.Mat4;
import linalg.Ray;

/**
 *
 * @author anton
 */
public class Camera
{
	private final Float3 pos = new Float3();
	private final Float3 up = new Float3();
	private final Float3 left = new Float3();
	private final Float3 look = new Float3();
	private float itanx = 1.0f;
	private float itany = 1.0f;
	private float farz = 3.0f;
	private float nearz = 0.1f;
	public void setPos( Float3 p )
	{
		pos.copyIn( p );
	}
	public Float3 getPos()
	{
		return pos.copy();
	}
	public Camera setLookAt( Float3 p , Float3 l , Float3 up )
	{
		this.pos.copyIn( p );
		this.look.copyIn( p.sub( l ).norm() );
		this.left.copyIn( look.vecx( up ).norm() );
		this.up.copyIn( left.vecx( look ).norm() );
		return this;
	}
	public static Camera getLookAt( Float3 p , Float3 l , Float3 up , float itanx , float itany )
	{
		Camera out = new Camera();
		out.itanx = itanx;
		out.itany = itany;
		out.pos.copyIn( p );
		out.look.copyIn( l.sub( p ).norm() );
		out.left.copyIn( out.look.vecx( up ).norm() );
		out.up.copyIn( out.left.vecx( out.look ).norm() );
		return out;
	}
	public Ray getRay( Float2 tx )
	{
		return new Ray( pos.copy() , look.add( up.mul( tx.y * itany ).add( left.mul( tx.x * itanx ) ) ).norm() );
	}
	public Mat4 getMatrix()
	{
		return proj().mul( view() );
	}
	private Mat4 view()
	{
		return new Mat4(
				new Float4( left , -left.dot( pos ) ) ,
				new Float4( up , -up.dot( pos ) ) ,
				new Float4( look , -look.dot( pos ) ) ,
				new Float4( 0.0f , 0.0f , 0.0f , 1.0f ) );
	}
	private Mat4 proj()
	{
		return new Mat4(
				new Float4( itanx , 0.0f , 0.0f , 0.0f ) ,
				new Float4( 0.0f , itany , 0.0f , 0.0f ) ,
				new Float4( 0.0f , 0.0f , -2.0f / ( farz - nearz ) ,  0.0f ) ,
				new Float4( 0.0f , 0.0f , -( farz + nearz ) / ( farz - nearz ) , 1.0f ) );
	}
}
