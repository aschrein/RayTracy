package model;

import linalg.Float3;

public class Material
{
	public final Float3 color = new Float3( 1.0f , 1.0f , 1.0f );
	public float roughness = 1.0f;
	public float metalnes = 0.0f;
	public float specular = 0.0f;
	public float fresnel = 0.0f;
	public float emission = 0.0f;
	public float transparency = 0.0f;
	public float optical_density = 1.0f;
	public float subsurface_density = 999.0f;
	public final Float3 subsurface_color = new Float3();
}
