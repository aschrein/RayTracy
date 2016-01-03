package implementation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import model.SurfaceSUB;
import model.SurfaceUNI;
public class RayTracy
{
	static public void main( String[] args ) throws InterruptedException
	{
		final Screen screen = new Screen( 512 , 512 );
		final Camera camera = Camera.getLookAt( new Float3( 0.0f , -10.0f , 2.0f ) ,
				new Float3( 0.0f , 0.0f , 0.0f ) ,
				new Float3( 0.0f , 0.0f , 1.0f ) , 3.6f , 3.6f );
		final Scene scene = new Scene();
		scene.addSurface( new Surface()
		{
			private final Material mat = new Material();
			@Override
			public float getDist( Float3 pos )
			{
				return pos.sub( new Float3( 2.0f , 1.5f , 1.0f ) ).mod() - 0.3f;
			}
			@Override
			public Material getMat( Float3 r )
			{
				mat.emission_color.x = 3.14f;
				mat.emission_color.y = 3.234f;
				mat.emission_color.z = 3.09f;
				return mat;
			}
		} );
		scene.addSurface(
				new SurfaceUNI(
						new SurfaceUNI(
								new SurfaceSUB(
										new Surface()
										{
											Material mat = new Material();
											@Override
											public float getDist( Float3 r )
											{
												return r.z
												+ 1.0f;
											}
											@Override
											public Material getMat( Float3 r )
											{
												mat.color.y = 0.23f;
												mat.color.z = 0.1f;
												mat.roughness = 0.2f;
												mat.fresnel = 0.8f;
												mat.specular = 0.7f;
												mat.metalnes = 0.0f;
												return mat;
											}
										} , new Surface()
										{
											private final Material mat = new Material();
											@Override
											public float getDist( Float3 pos )
											{
												return pos.sub( new Float3( 0.0f , 0.0f , 0.0f ) ).mod() - 3.5f;
											}
											@Override
											public Material getMat( Float3 r )
											{
												mat.emission_color.x = 1.514f;
												mat.emission_color.y = 0.0934f;
												mat.emission_color.z = 0.09f;
												return mat;
											}
										} , 0.1f ) , new Surface()
										{
											Material mat = new Material();
											@Override
											public float getDist( Float3 r )
											{
												return r.sub( new Float3( 0.0f , 0.0f , -2.0f ) ).mod()
												- 2.0f;
											}
											@Override
											public Material getMat( Float3 r )
											{
												mat.emission_color.x = 1.14f;
												mat.emission_color.y = 0.234f;
												mat.emission_color.z = 0.09f;
												mat.color.y = 0.23f;
												mat.color.z = 0.1f;
												mat.roughness = 0.2f;
												mat.fresnel = 0.8f;
												mat.specular = 0.7f;
												mat.metalnes = 0.0f;
												return mat;
											}
										} , 0.5f
						) , new Surface()
						{
							Material mat = new Material();
							@Override
							public float getDist( Float3 r )
							{
								return -0.5f * ( float ) Math.exp( -0.1f * Math.pow( 1.4f * Math.abs( r.z ) , 2.0 ) )
								* ( float ) ( 1.0f + Math.cos( r.z * 10.0f ) * 0.3f )
								+ ( float )Math.sqrt( r.x * r.x + r.y * r.y );
							}
							@Override
							public Material getMat( Float3 r )
							{
								mat.emission_color.x = 0.014f;
								mat.emission_color.y = 0.0934f;
								mat.emission_color.z = 0.19f;
								mat.color.x = 0.6f;
								mat.color.y = 0.8f;
								mat.color.z = 0.94f;
								mat.fresnel = 0.9f;
								mat.roughness = 0.2f;
								mat.metalnes = 0.0f;
								mat.specular = 0.7f;
								return mat;
							}
						} , 0.2f )
		);
		final JFrame main_form = new JFrame();
		//column index list for use in stream api
		final List< Integer> col = new ArrayList();
		for( int i = 0; i < screen.getHeight(); i++ )
		{
			col.add( i );
		}
		Thread render_thread = new Thread( new Runnable()
		{
			@Override
			public void run()
			{
				while( main_form.isVisible() )
				{
					long t1 = System.currentTimeMillis();
					for( int x = 0; x < screen.getWidth(); x++ )
					{
						final int ix = x;
						//sample position within pixel range for antialiasing
						final float u = ( ( float ) x + 0.5f * ( float ) Math.random() - 0.5f )
								/ screen.getWidth() * 2.0f - 1.0f;
						//only write to screen
						//synchronized( screen )
						{
							col.parallelStream().forEach( ( y ) ->
							{
								float v = -( ( float ) y + 0.5f * ( float ) Math.random() - 0.5f )
										/ screen.getHeight() * 2.0f + 1.0f;
								Ray camera_ray = camera.getRayLens( new Float2( u , v ) , 10.3f , 0.5f );
								//N - max_depth
								Float3 color = scene.getColor( camera_ray , 4 );
								screen.set( color , ix , y );
							} );
						}
					}
					System.out.println( "sample whole screen:" + ( System.currentTimeMillis() - t1 ) + "ms" );
				}
			}
		} );
		main_form.add( new JComponent()
		{
			@Override
			public void paint( Graphics g )
			{
				Rectangle r = main_form.getBounds();
				this.setBounds( 0 , 0 , r.width , r.height );
				Graphics2D g2d = ( Graphics2D ) g;
				//only reads from screen
				//synchronized( screen )
				{
					screen.draw( g2d , 512 , 512 );
				}
			}
		} );
		main_form
				.setDefaultCloseOperation( javax.swing.WindowConstants.EXIT_ON_CLOSE );
		main_form.setBounds( 0 , 0 , 512 + 20 , 512 + 40 );
		main_form.setVisible( true );
		render_thread.start();
		while( main_form.isVisible() )
		{
			main_form.repaint();
			Thread.sleep( 100 );
		}
	}
}
