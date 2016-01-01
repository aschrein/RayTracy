package implementation;

import implementation.RotationBody.Func;
import linalg.Float2;
import linalg.Float3;
import model.Material;
import model.Surface;

public class Body3D implements Surface
{
	private final Material mat = new Material();
	public static interface Func3D
	{
		float get( Float3 r );
	}
	private final Func3D func;
	public Body3D( Func3D func )
	{
		mat.color.x = 0.912f;
		mat.color.y = 0.934f;
		mat.color.z = 0.9345f;
		this.func = func;
	}
	@Override
	public float getDist( Float3 pos )
	{
		return func.get( pos );
	}
	@Override
	public Material getMat()
	{
		return mat;
	}
}
