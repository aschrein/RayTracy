package model;

import linalg.Float3;

public class Material
{
	public final Float3 color = new Float3( 1.0f , 1.0f , 1.0f );
	public float roughness = 1.0f;
	public float metalnes = 0.0f;
	public float specular = 0.0f;
	public float fresnel = 0.0f;
	public float transparency = 0.0f;
	public float optical_density = 1.0f;
	public float subsurface_density = 999.0f;
	public final Float3 emission_color = new Float3( 0.0f , 0.0f , 0.0f );
	public final Float3 subsurface_color = new Float3();
	public Material lerp( Material mat , float l )
	{
		Material out = new Material();
		out.color.copyIn( color.mul( 1.0f - l ).add( mat.color.mul( l ) ) );
		out.emission_color.copyIn( emission_color.mul( 1.0f - l ).add( mat.emission_color.mul( l ) ) );
		out.subsurface_color.copyIn( subsurface_color.mul( 1.0f - l ).add( mat.subsurface_color.mul( l ) ) );
		out.roughness = roughness * ( 1.0f - l ) + mat.roughness * l;
		out.specular = specular * ( 1.0f - l ) + mat.specular * l;
		out.fresnel = fresnel * ( 1.0f - l ) + mat.fresnel * l;
		out.transparency = transparency * ( 1.0f - l ) + mat.transparency * l;
		out.optical_density = optical_density * ( 1.0f - l ) + mat.optical_density * l;
		out.subsurface_density = subsurface_density * ( 1.0f - l ) + mat.subsurface_density * l;
		out.metalnes = metalnes * ( 1.0f - l ) + mat.metalnes * l;
		return out;
	}
}
