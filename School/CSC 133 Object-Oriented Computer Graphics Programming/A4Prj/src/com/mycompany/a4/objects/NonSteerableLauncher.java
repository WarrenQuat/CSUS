/*
 * Warren Quattrocchi
 * CSC 133
 * NonSteerableLauncher Class
 */

package com.mycompany.a4.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a4.interfaces.IDrawable;
import com.mycompany.a4.shapes.Rectangle;

public class NonSteerableLauncher extends MissileLauncher implements IDrawable
{
	private Rectangle launcher;
	public NonSteerableLauncher(Point2D loc,int size) 
	{
		setColor(0xc47c17);
		setLocation(loc);
		launcher = new Rectangle(5,80, getColor());
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn)
	{
		
		launcher.translate((float)getLocation().getX(),(float) getLocation().getY());
		launcher.rotate(-getDirection());
		launcher.draw(g, pCmpRelPrnt, pCmpRelScrn);
		launcher.resetTransform();
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
