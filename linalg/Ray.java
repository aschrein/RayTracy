package linalg;
public class Ray
{
	public final Float3 pos;
	public final Float3 dir;
	public int target_pixel_x , target_pixel_y;
	public Ray( Float3 pos , Float3 dir )
	{
		super();
		this.pos = pos;
		this.dir = dir;
	}
	public Ray()
	{
		pos = new Float3();
		dir = new Float3();
	}
	public Float3 getPos( float x )
	{
		return pos.add( dir.mul( x ) );
	}
}
