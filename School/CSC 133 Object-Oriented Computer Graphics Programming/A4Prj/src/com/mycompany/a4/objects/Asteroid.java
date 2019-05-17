/*
 * Warren Quattrocchi
 * CSC 133
 * Asteroid Class
 */

package com.mycompany.a4.objects;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a4.interfaces.*;
import com.mycompany.a4.shapes.Rectangle;
import com.codename1.charts.util.*;
import java.util.Random;

public class Asteroid extends MovableObject implements Imovable, IDrawable, ICollider, ISelectable
{
	private int size;
	private int rotInc;
	private boolean selected;
	private Rectangle r;
	/*
	 * constructs a new asteroid with a random location and a random size between 6 and 30
	 */
	public Asteroid()
	{
		
	}
	
	public Asteroid(int height, int width)
	{
		selected = false;
		setColor(ColorUtil.GRAY);
		setLocation(new Point2D(0 + (width - 0) * new Random().nextDouble(),(0 + (height- 0) * new Random().nextDouble())));
		setSpeed(new Random().nextInt(10) + 1);
		setDirection(new Random().nextInt(359));
		setHeight(height);
		setWidth(width);
		size = new Random().nextInt((25) + 1) + 10; 
		this.setSize(size * 4);
		rotInc= 0;
		r = new Rectangle(getSize(),getSize(), getColor());
	}
	
	//invoke movableobject move method
	public void move(int fps)
	{
		super.move(fps);
		rotInc ++;
		if(rotInc >360)
			rotInc =0;
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn)
	{
		r.translate((int)getLocation().getX(),(int)getLocation().getY());
		r.rotate(rotInc);
		r.draw(g, pCmpRelPrnt, pCmpRelScrn);	
		r.resetTransform();
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

	@Override
	public boolean collidesWith(ICollider otherObject) {
		boolean result = false;	
		int thisCenterX = (int)this.getLocation().getX();
		int thisCenterY = (int)this.getLocation().getY();
		int otherCenterX = (int)((GameObject)otherObject).getLocation().getX();
		int otherCenterY = (int)((GameObject)otherObject).getLocation().getY();
		int dx = thisCenterX - otherCenterX;
		int dy = thisCenterY - otherCenterY;
		int disBetweenCentersSqr = (dx*dx + dy*dy);
		int thisRadius = this.getSize()/2;
		int otherRadius = ((GameObject)otherObject).getSize()/2;	
		int radiiSqr = (thisRadius * thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);
		if(disBetweenCentersSqr <= radiiSqr)
			result =true;
		return result;	
	}

	@Override
	public void handleCollision(ICollider otherObject) {
		if(otherObject instanceof Missile)
			if(!((Missile)otherObject).isEnemy())
				System.out.println("A friendly missile has struck and asteroid");
		else if(otherObject instanceof Asteroid)
				System.out.println("2 asteroids have collided");
		
	}
	@Override
	public void setSelected(boolean yesNo) {
		selected = yesNo;
		r.setSelected(yesNo);
		
	}
	@Override
	public boolean isSelected() {
		return selected;
	}
	@Override
	public boolean contains(Point2D pPtrRelPrnt, Point2D pCmpRelPrnt) {
		int px = (int) pPtrRelPrnt.getX() ;
		int py = (int) pPtrRelPrnt.getY() + getSize();
		int xLoc = (int) (pCmpRelPrnt.getX() + this.getLocation().getX());
		int yLoc = (int) (pCmpRelPrnt.getY() + this.getLocation().getY());
		
		if((px >= xLoc - getSize()/2) && (px <= xLoc + this.getSize()/2)
			&& (py >= yLoc - getSize()/2) && (py <= yLoc + this.getSize()/2))
			return true;
		else
			return false;
	}
}

