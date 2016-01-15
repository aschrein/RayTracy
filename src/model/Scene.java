package model;

import java.util.ArrayList;
import java.util.List;
import linalg.Float3;
import linalg.Ray;
import linalg.VectorFactory;

public class Scene
{
	private final List< Surface > surfaces = new ArrayList();
	public void addSurface( Surface obj )
	{
		surfaces.add( obj );
	}
	//parallel safe, no state changed
	private Float3 getColor( Ray ray , int max_depth , int cur_depth )
	{
		if( cur_depth == max_depth )
		{
			return new Float3( 0.0f , 0.0f , 0.0f );
		}
		Surface nearest_surface = null;
		float nearest_dist = 100.0f;
		for( Surface surface : surfaces )
		{
			float d = ZeroFinder.find( ( x ) -> surface.getDist( ray.getPos( x ) ) );
			if( d > 0.0f && d < nearest_dist )
			{
				nearest_dist = d;
				nearest_surface = surface;
			}
		}
		if( nearest_surface != null )
		{
			Collision col = new Collision( ray , nearest_dist , nearest_surface );
			/*return getColor( new Ray( col.pos , VectorFactory.reflect( ray.dir , col.norm , 0.5f ) )
			, max_depth , cur_depth + 1 );*/
			//Float3 spec = VectorFactory.reflect( ray.dir , col.norm , 0.0f );
			Material mat = nearest_surface.getMat( col.pos );
			Float3 spec = VectorFactory.reflect( ray.dir , col.norm , mat.roughness );
			Float3 diff = VectorFactory.reflect( ray.dir , col.norm , 1.0f );
			float k = 1.0f + ray.dir.dot( col.norm );
			float fresnel = ( float )Math.pow( k , 2.0 ) * mat.fresnel + ( 1.0f - mat.fresnel );
			
			Float3 cap_spec = getColor( new Ray( col.pos , spec ) , max_depth , cur_depth + 1 ).mul( fresnel );
			Float3 cap_diff = mat.color.componentMul(
					getColor( new Ray( col.pos , diff ) , max_depth , cur_depth + 1 ) );
			Float3 metal = mat.color.componentMul(
					getColor( new Ray( col.pos , spec ) , max_depth , cur_depth + 1 ) );
			return mat.emission_color.add( VectorFactory.lerp(
						VectorFactory.lerp( cap_diff , cap_spec , mat.specular )
						, metal
					, mat.metalnes ) );
		} else
		{
			return new Float3( 0.4f , 0.02f , 0.05f ).mul( 0.6f - 0.4f * Math.abs( ray.dir.z ) )
						.add( new Float3( 0.19f , 0.85f , 0.93f ).mul( 0.5f + 0.4f * ray.dir.z ) )
						.add( new Float3( 0.09f , 0.275f , 0.93f ).mul( ( float )Math.pow( Math.max(  0.0f , ray.dir.z ) , 3.0f ) ) )
						.add( new Float3( 0.79f , 0.375f , 0.03f ).mul( ( float )Math.pow( Math.max(  0.0f , 1.0f - Math.abs( ray.dir.z ) ) , 3.0f ) ) );
		}
	}
	public Float3 getColor( Ray ray , int max_depth )
	{
		return getColor( ray , max_depth , 1 );
	}
}
