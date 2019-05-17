/*
 * Warren Quattrocchi
 * CSC 133
 * SteerableLauncher Class
 */

package com.mycompany.a3.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.interfaces.IDrawable;


public class SteerableLauncher extends MissileLauncher implements IDrawable
{

	//Create a new MissileLauncher with color and location 
	//corresponding to the PS or NPS
	public SteerableLauncher(int height, int width)
	{
		setSpeed(0);
		setColor(0x3a1072);
		setLocation(new Point2D(width/2,height/2));
		setDirection(0);
	}
	
	//change direction of missile launcher by 10 degrees
	public void moveLeft()
	{
		setDirection(getDirection() + 20);
		if(getDirection() >= 360)
		{
			setDirection(getDirection()- 360);
		}
	}

	public void moveRight()
	{
		setDirection(getDirection() - 20);
		if(getDirection() < 0)
		{
			setDirection(350- getDirection());
		}
	}
	
	public void move(Point2D loc)
	{
		setLocation(loc);
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt)
	{
		g.setColor(this.getColor());
		g.drawRect((int)(this.getLocation().getX() + pCmpRelPrnt.getX())  -(10/2), (int)(this.getLocation().getY() + pCmpRelPrnt.getY()) + 75, 10, 30);
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
