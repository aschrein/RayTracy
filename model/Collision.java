package model;

import linalg.Float3;
import linalg.Ray;

public class Collision
{
	public final Float3 pos;
	public final Float3 norm;
	public final Material mat;
	public Collision( Ray ray , float d , Surface surface )
	{
		super();
		this.pos = ray.getPos( d );
 		final float dr = 1.0e-3f;
		this.norm =
				new Float3(
				surface.getDist( this.pos.add( new Float3( dr , 0.0f , 0.0f ) ) ) -
				surface.getDist( this.pos.add( new Float3( -dr , 0.0f , 0.0f ) ) ) ,
				surface.getDist( this.pos.add( new Float3( 0.0f , dr , 0.0f ) ) ) -
				surface.getDist( this.pos.add( new Float3( 0.0f , -dr , 0.0f ) ) ) ,
				surface.getDist( this.pos.add( new Float3( 0.0f , 0.0f , dr ) ) ) -
				surface.getDist( this.pos.add( new Float3( 0.0f , 0.0f , -dr ) ) )
				 ).norm();
		this.mat = surface.getMat();
	}
}
