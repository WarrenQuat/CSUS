/*
 * Warren Quattrocchi
 * CSC 133
 * NonSteerableLauncher Class
 */

package com.mycompany.a3.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.interfaces.IDrawable;

public class NonSteerableLauncher extends MissileLauncher implements IDrawable
{
	private int size;
	public NonSteerableLauncher(Point2D loc,int size) 
	{
		setColor(0xc47c17);
		setLocation(loc);
		this.size = size;
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt)
	{
		g.setColor(this.getColor());
		g.drawRect((int)(this.getLocation().getX() + pCmpRelPrnt.getX())  -(10/2), (int)(this.getLocation().getY() + pCmpRelPrnt.getY()) + (size/2) , 10, 30);
		//g.drawRect((int)(this.getLocation().getX() + pCmpRelPrnt.getX()) -(10/2), (int)(this.getLocation().getY() + pCmpRelPrnt.getY()) - (100 + 30), 10, 30);
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
