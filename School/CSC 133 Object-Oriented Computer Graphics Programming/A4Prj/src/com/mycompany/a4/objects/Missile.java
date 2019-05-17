/*
 * Warren Quattrocchi
 * CSC 133
 * Missile Class
 */

package com.mycompany.a4.objects;

import java.util.Random;

import  com.codename1.charts.util.*;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a4.interfaces.*;
import com.mycompany.a4.shapes.MissileShape;

public class Missile extends MovableObject implements IDrawable, ICollider, ISelectable
{
	private boolean enemy;
	private int fuel;
	private boolean selected;
	private MissileShape ms;

	/*
	 * constructs a new missile with an initially random location
	 * sets fuel = 10
	 * boolean value determines if friendly or enemy missile
	 */
	public Missile(boolean enemy,int height, int width)
	{
		if(enemy)
			setColor(ColorUtil.GREEN);
		else
			setColor(ColorUtil.CYAN);
		//setLocation(new Point2D(0 + (1024.0 - 0) * new Random().nextDouble(),(0 + (768.00- 0) * new Random().nextDouble())));
		this.enemy = enemy;
		fuel = 30;
		setHeight(height);
		setSize(70);
		setWidth(width);
		ms = new MissileShape(getColor());
		selected= false;
	}
	public Missile(boolean enemy)
	{
		if(enemy)
			setColor(ColorUtil.GREEN);
		else
			setColor(ColorUtil.CYAN);
		setLocation(new Point2D(0 + (1024.0 - 0) * new Random().nextDouble(),(0 + (768.00- 0) * new Random().nextDouble())));
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn)
	{
		ms.translate((float)this.getLocation().getX(), (float)this.getLocation().getY());
		ms.rotate(-getDirection());
		ms.draw(g, pCmpRelPrnt, pCmpRelScrn);
		ms.resetTransform();
	}
	
	//calls move method from movable object class and decrements fuel
	public void move(int fps)
	{
		super.move(fps);
		fuel--;
	}
	
	public void refuel()
	{
		fuel = 30;
		System.out.println("Missile refueled");
	}
	
	//return ig enemy missile or friendly
	public boolean isEnemy()
	{
		return enemy;
	}
	
	//returns fuel
	public int getFuel()
	{
		return fuel;
	}
	
	//toString
	public String toString()
	{
		String s  ="";
		if (enemy == true)
			s =  s + "Enemy Missile:       " + "[Fuel: " + getFuel() + "]";
		else
			s = s +  "Player Misile:       " + "[Fuel: " + getFuel() + "]";
		
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSelected(boolean yesNo) {
		selected = yesNo;
		ms.setSelected(yesNo);
		
	}
	@Override
	public boolean isSelected() {
		return selected;
	}
	@Override
	public boolean contains(Point2D pPtrRelPrnt, Point2D pCmpRelPrnt) {
		int px = (int) pPtrRelPrnt.getX();
		int py = (int) pPtrRelPrnt.getY();
		int xLoc = (int) (pCmpRelPrnt.getX() + this.getLocation().getX());
		int yLoc = (int) (pCmpRelPrnt.getY() + this.getLocation().getY() - 70);
		
		if((px >= xLoc - getSize()/2) && (px <= xLoc + this.getSize()/2)
				&& (py >= yLoc - getSize()/2) && (py <= yLoc + this.getSize()/2))
			return true;
		else
			return false;
	}
}
