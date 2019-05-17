/*
 * Warren Quattrocchi
 * CSC 133
 * SteerableLauncher Class
 */

package com.mycompany.a4.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a4.interfaces.IDrawable;
import com.mycompany.a4.shapes.Rectangle;


public class SteerableLauncher extends MissileLauncher implements IDrawable
{
	private Rectangle launcher;
	//Create a new MissileLauncher with color and location 
	//corresponding to the PS or NPS
	public SteerableLauncher(int height, int width)
	{
		setSpeed(0);
		setColor(0x3a1072);
		setLocation(new Point2D(width/2,height/2));
		setDirection(0);
		launcher = new Rectangle(5,80, getColor());
	}
	
	//change direction of missile launcher by 10 degrees
	public void moveRight()
	{
		setDirection(getDirection() + 15);
		if(getDirection() >= 360)
		{
			setDirection(getDirection()- 360);
		}
	}

	public void moveLeft()
	{
		setDirection(getDirection() - 15);
		if(getDirection()< 0)
		{
			setDirection(360 - -getDirection());
		}
	}
	
	public void move(Point2D loc)
	{
		setLocation(loc);
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn)
	{
		launcher.rotate(-getDirection());
		launcher.translate((float)getLocation().getX(),(float) getLocation().getY());
		
		launcher.draw(g, pCmpRelPrnt, pCmpRelScrn);
		launcher.resetTransform();
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
