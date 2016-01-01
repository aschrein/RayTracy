package implementation;

import linalg.Float3;
import model.Material;
import model.Surface;

public class Sphere implements Surface
{
	private final Material mat = new Material();
	public Sphere()
	{
		mat.color.x = 0.8f;
		mat.color.y = 0.9f;
		mat.color.z = 0.2635f;
		mat.fresnel = 2.0f;
		mat.roughness = 0.2f;
		mat.metalnes = 0.0f;
		mat.specular = 1.0f;
	}
	@Override
	public float getDist( Float3 pos )
	{
		return pos.mod() - 1.0f;
	}

	@Override
	public Material getMat()
	{
		return mat;
	}
}
