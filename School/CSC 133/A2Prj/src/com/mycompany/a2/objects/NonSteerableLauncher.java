/*
 * Warren Quattrocchi
 * CSC 133
 * NonSteerableLauncher Class
 */

package com.mycompany.a2.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;

public class NonSteerableLauncher extends MissileLauncher
{

	public NonSteerableLauncher(Point2D loc) 
	{
		setColor(ColorUtil.WHITE);
		setLocation(loc);
	}
	
	public void move(Point2D loc)
	{
		setLocation(loc);
	}
	
	public String toString()
	{
		String s =  "[speed: " + getSpeed() + "][direction: " + getDirection() + "]";
		s = s + "[color: "  						 +  ColorUtil.red(getColor()) + "," 
				 +  ColorUtil.green(getColor()) + ","
                 +  ColorUtil.blue(getColor())  
                 +  "] [location(x,y): (" + Math.round(getLocation().getX() * 10.0)/10.0 +", " 
                 +  Math.round(getLocation().getY() * 10.0)/10.0 + ")]";
		return s;
	}
}
