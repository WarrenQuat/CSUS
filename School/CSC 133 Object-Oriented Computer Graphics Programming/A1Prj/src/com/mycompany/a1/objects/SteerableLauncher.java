/*
 * Warren Quattrocchi
 * CSC 133
 * SteerableLauncher Class
 */

package com.mycompany.a1.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;


public class SteerableLauncher extends MissileLauncher
{

	//Create a new MissileLauncher with color and location 
	//corresponding to the PS or NPS
	public SteerableLauncher()
	{
		setSpeed(0);
		setColor(ColorUtil.BLUE);
		setLocation(new Point2D(512,384));
		setDirection(0);
	}
	
	//change direction of missile launcher by 10 degrees
	public void moveLeft()
	{
		setDirection(getDirection() + 10);
		if(getDirection() >= 360)
		{
			setDirection(getDirection()- 360);
		}
	}

	public void move(Point2D loc)
	{
		setLocation(loc);
	}
	
	public String toString()
	{
		String s = "[speed: " + getSpeed() + "][direction: " + getDirection() + "]";
		s = s + "[color: "  						 +  ColorUtil.red(getColor()) + "," 
				 +  ColorUtil.green(getColor()) + ","
                 +  ColorUtil.blue(getColor())  
                 +  "] [location(x,y): (" + Math.round(getLocation().getX() * 10.0)/10.0 +", " 
                 +  Math.round(getLocation().getY() * 10.0)/10.0 + ")]";
		return s;
	}
}
