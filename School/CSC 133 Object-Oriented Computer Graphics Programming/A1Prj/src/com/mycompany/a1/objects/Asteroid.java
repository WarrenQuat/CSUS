/*
 * Warren Quattrocchi
 * CSC 133
 * Asteroid Class
 */

package com.mycompany.a1.objects;
import com.mycompany.a1.interfaces.*;
import com.codename1.ui.geom.Point2D;
import com.codename1.charts.util.*;
import java.util.Random;

public class Asteroid extends MovableObject implements Imovable
{
	private int size;
	
	/*
	 * constructs a new asteroid with a random location and a random size between 6 and 30
	 */
	public Asteroid()
	{
		setColor(ColorUtil.LTGRAY);
		setLocation(new Point2D(0 + (1024.0 - 0) * new Random().nextDouble(),(0 + (768.00- 0) * new Random().nextDouble())));
		size = new Random().nextInt((30-6) + 1) + 6; 
	}
	
	//invoke movableobject move method
	public void move()
	{
		super.move();
	}
	
	//tostring method
	public String toString()
	{
		String s = "Asteroid:            [size: " + size + "]";
		s = s + "[speed: " + getSpeed() + "][direction: " + getDirection() + "]";
	    s = s + "[color: "  						 +  ColorUtil.red(getColor()) + "," 
				 +  ColorUtil.green(getColor()) + ","
                 +  ColorUtil.blue(getColor())  
                 +  "] [location(x,y): (" + Math.round(getLocation().getX() * 10.0)/10.0 +", " 
                 +  Math.round(getLocation().getY() * 10.0)/10.0 + ")]";
		return s;
	}
}
