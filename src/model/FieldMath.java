/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author anton
 */
public class FieldMath
{
	public static float clamp( float a , float b , float k )
	{
		return a < b ? b : a > k ? k : a;
	}
	public static float mix( float a , float b , float k )
	{
		return a * ( 1.0f - k ) + b * k;
	}
	public static float smoothMin( float a , float b , float k )
	{
		float h = clamp( 0.5f + 0.5f * ( b - a ) / k , 0.0f , 1.0f );
		return mix( b , a , h ) - k * h * ( 1.0f - h );
	}
	public static float getPart( float a , float b , float k )
	{
		float h = clamp( 0.5f + 0.5f * ( b - a ) / k , 0.0f , 1.0f );
		return h - h * ( 1.0f - h );
	}
}
