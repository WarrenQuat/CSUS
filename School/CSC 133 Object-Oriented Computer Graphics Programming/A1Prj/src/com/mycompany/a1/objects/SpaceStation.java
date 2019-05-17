/*
 * Warren Quattrocchi
 * CSC 133
 * SpaceStation Class
 */

package com.mycompany.a1.objects;
import com.codename1.charts.util.*;
import java.util.Random;
import com.codename1.ui.geom.Point2D;

public class SpaceStation extends FixedObject 
{
	private int blinkRate;
	private boolean visible;
	
	/*Constructs a new space station
	 * location at bottom middle of screen
	 * initially it will be visible
	 */
	public SpaceStation()
	{
		setColor(ColorUtil.YELLOW);
		setLocation(new Point2D(0 + (1024.0 - 0) * new Random().nextDouble(),(0 + (768.00- 0) * new Random().nextDouble())));
		blinkRate = new Random().nextInt(4) + 1;
		visible = true;
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
}
