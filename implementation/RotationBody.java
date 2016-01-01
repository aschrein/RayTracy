package implementation;

import linalg.Float2;
import linalg.Float3;
import model.Material;
import model.Surface;

public class RotationBody implements Surface
{
	private final Material mat = new Material();
	public static interface Func
	{
		float get( Float2 r );
	}
	private final Func func;
	public RotationBody( Func func )
	{
		mat.color.x = 0.612f;
		mat.color.y = 0.234f;
		mat.color.z = 0.5345f;
		this.func = func;
	}
	@Override
	public float getDist( Float3 pos )
	{
		return func.get( new Float2( pos.xy().mod() , pos.z ) );
	}
	@Override
	public Material getMat()
	{
		return mat;
	}
}
