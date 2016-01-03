/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import linalg.Float3;
import linalg.Float4;
/**
 *
 * @author anton
 */
public class Screen
{
	private final Float4[] image;
	private final int width, height;
	private final Graphics2D ig2;
	private final BufferedImage bi;
	public Screen( int w , int h )
	{
		image = new Float4[ w * h ];
		bi = new BufferedImage( w , h , BufferedImage.TYPE_INT_ARGB );
		ig2 = bi.createGraphics();
		for( int i = 0; i < w * h; i++ )
		{
			image[ i ] = new Float4( 0.0f , 0.0f , 0.0f , 0.0f );
		}
		width = w;
		height = h;
	}
	public void clear()
	{
		ig2.setPaint( Color.WHITE );
		ig2.fillRect( 0 , 0 , width , height );
		for( int i = 0; i < width * height; i++ )
		{
			image[ i ] = new Float4( 0.0f , 0.0f , 0.0f , 0.0f );
		}
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public void draw( Graphics2D g , int w , int h )
	{
		for( int x = 0; x < width; x++ )
		{
			for( int y = 0; y < height; y++ )
			{
				int idx = x + y * height;
				ig2.setPaint( new Color(
						clamp( image[ idx ].x / image[ idx ].w ) ,
						clamp( image[ idx ].y / image[ idx ].w ) ,
						clamp( image[ idx ].z / image[ idx ].w ) ) );
				ig2.fillRect( x , y , 1 , 1 );
			}
		}
		g.drawImage( bi , new AffineTransform( ( float ) w / width , 0.0f , 0.0f , ( float ) h / height , 0.0f , 0.0f ) , null );
	}
	public static float clamp( float x )
	{
		return Math.max( 0.0f , Math.min( 1.0f , x ) );
	}
	public void set( Float3 c , int x , int y )
	{
		int idx = x + y * height;
		if( idx < width * height && idx >= 0 )
		{
			image[ idx ].copyIn( image[ idx ].add( new Float4( c , 1.0f ) ) );
		}
	}
}
