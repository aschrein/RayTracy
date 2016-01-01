package implementation;
import implementation.RotationBody.Func;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import linalg.Float2;
import linalg.Float3;
import linalg.Ray;
import model.Camera;
import model.Material;
import model.Scene;
import model.Screen;
import model.Surface;
public class RayTracy
{
	static public void main( String[] args )
	{
		Screen screen = new Screen( 512 , 512 );
		Camera camera = Camera.getLookAt( new Float3( 0.0f, -4.0f, 1.0f ) ,
				new Float3( 0.0f, 0.0f, 0.0f ) ,
				new Float3( 0.0f, 0.0f, 1.0f ) , 1.2f , 1.2f );
		Scene scene = new Scene();
		//scene.addSurface( new Sphere() );
		scene.addSurface( new Surface()
		{
			Material mat = new Material();
			@Override
			public float getDist( Float3 r )
			{
				return -0.5f * ( float )Math.exp( -0.1f * Math.pow( 1.4f * Math.abs( r.z ), 2.0 ) )
						- ( float )( 1.0f + Math.cos( r.z * 10.0f ) ) * 0.1f + 
						+ r.xy().mod() * ( 0.8f + 0.2f * ( float )Math.cos( 10.0f * Math.atan2( r.y , r.x ) ) );
			}
			@Override
			public Material getMat()
			{
				mat.color.x = 0.2f;
				mat.color.y = 0.8f;
				mat.color.z = 0.34f;
				mat.fresnel = 2.0f;
				mat.roughness = 0.2f;
				mat.metalnes = 0.0f;
				mat.specular = 0.5f;
				return mat;
			}
		} );
		/*scene.addSurface( new Surface()
		{
			Material mat = new Material();
			@Override
			public float getDist( Float3 r )
			{
				return r.z + 1.0f;
			}
			@Override
			public Material getMat()
			{
				mat.color.y = 0.23f;
				mat.color.z = 0.1f;
				mat.roughness = 0.0f;
				mat.metalnes = 1.0f;
				return mat;
			}
		} );*/
		final JFrame main_form = new JFrame();
		main_form.add( new JComponent()
		{
			@Override
			public void paint( Graphics g )
			{
				Rectangle r = main_form.getBounds();
				this.setBounds( 0 , 0 , r.width , r.height );
				Graphics2D g2d = ( Graphics2D )g;
				// screen.clear();
				float u , v;
				// int y = ( int )( Math.random() * screen.getHeight() );
				long t1 = System.currentTimeMillis();
				for( int x = 0; x < screen.getWidth(); x++ )
				{
					u = ( ( float )x + 2.0f * ( float )Math.random() - 1.0f )
							/ screen.getWidth() * 2.0f - 1.0f;
					for( int y = 0; y < screen.getHeight(); y++ )
					{
						v = -( ( float )y + 2.0f * ( float )Math.random() - 1.0f )
								/ screen.getHeight() * 2.0f + 1.0f;
						Ray camera_ray = camera.getRay( new Float2( u , v ) );
						Float3 color = scene.getColor( camera_ray , 4 );
						screen.set( color , x , y );
					}
				}
				screen.draw( g2d , 512 , 512 );
				System.out.println( "repaint:" + ( System.currentTimeMillis() - t1 ) + "ms" );
				/*
				 * float u = ( float )Math.random() * 2.0f - 1.0f; float v = (
				 * float )Math.random() * 2.0f - 1.0f;
				 * 
				 * int x = ( int )( ( 0.5f * u + 0.5f ) * screen.getWidth() +
				 * 0.5f ); int y = ( int )( ( 0.5f * v + 0.5f ) *
				 * screen.getHeight() + 0.5f );
				 */
			}
		} );
		main_form
				.setDefaultCloseOperation( javax.swing.WindowConstants.EXIT_ON_CLOSE );
		main_form.setBounds( 0 , 0 , 512 + 20 , 512 + 40 );
		main_form.setVisible( true );
		while( main_form.isVisible() )
		{
			
			main_form.repaint();
			
		}
	}
}
