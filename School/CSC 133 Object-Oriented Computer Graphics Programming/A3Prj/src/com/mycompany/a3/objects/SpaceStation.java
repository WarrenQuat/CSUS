/*
 * Warren Quattrocchi
 * CSC 133
 * SpaceStation Class
 */

package com.mycompany.a3.objects;
import com.codename1.charts.util.*;
import java.util.Random;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;

public class SpaceStation extends FixedObject implements IDrawable,ICollider
{
	private int blinkRate;
	private boolean visible;
	
	/*Constructs a new space station
	 * location at bottom middle of screen
	 * initially it will be visible
	 */
	public SpaceStation(int height, int width)
	{
		setColor(ColorUtil.YELLOW);
		this.setSize(100);
		setLocation(new Point2D(100 + ((width- 100) - 100) * new Random().nextDouble(),(100 + ((height - 100) - 100) * new Random().nextDouble())));
		blinkRate = (new Random().nextInt(4) + 1) * 10;
		visible = true;
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt)
	{
		g.setColor(this.getColor());
		if(!getVisibility())
			g.drawRect((int)(this.getLocation().getX() + pCmpRelPrnt.getX() - getSize()/4), (int)(this.getLocation().getY() + pCmpRelPrnt.getY() - getSize()) , getSize() * 2,getSize()/2);
		else
			g.fillRect((int)(this.getLocation().getX() + pCmpRelPrnt.getX() - getSize()/4), (int)(this.getLocation().getY() + pCmpRelPrnt.getY() - getSize()) , getSize() * 2,getSize()/2);
	}
	//returns blink rate
	public int getBlinkRate()
	{
		return blinkRate;
	}
	
	//change blink rate
	public void setBlinkRate(int newRate)
	{
		blinkRate = newRate;
	}
	
	//check blink will call this method
	//if time%blinkrate == 0
	public void changeVisibility()
	{
		if (visible)
			visible = false;
		else
			visible = true;
	}
	public boolean getVisibility()
	{
		return visible;
	}
	//called after every tick of time
	public void checkBlink(int time)
	{
		if (time%blinkRate == 0)
			changeVisibility();
	}
	//toString
	public String toString()
	{
		String s = "SpaceStation:        [blink rate: " + blinkRate + "]"
					+ "[location(x,y):"  + Math.round(getLocation().getX() * 10.0)/10.0 +", " 
                            +  Math.round(getLocation().getY() * 10.0)/10.0 + ")]"
                            + "[Visible: " + visible + "][ID: " + getID() + "]";
		return s;
	}

	@Override
	public boolean collidesWith(ICollider otherObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleCollision(ICollider otherObject) {
		// TODO Auto-generated method stub
		
	}
}
