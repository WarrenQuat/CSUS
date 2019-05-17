/*
 * Warren Quattrocchi
 * CSC 133
 * Missile Class
 */

package com.mycompany.a2.objects;

import java.util.Random;

import  com.codename1.charts.util.*;
import com.codename1.ui.geom.Point2D;

public class Missile extends MovableObject
{
	private boolean enemy;
	private int fuel;

	/*
	 * constructs a new missile with an initially random location
	 * sets fuel = 10
	 * boolean value determines if friendly or enemy missile
	 */
	public Missile(boolean enemy)
	{
		setColor(ColorUtil.GREEN);
		setLocation(new Point2D(0 + (1024.0 - 0) * new Random().nextDouble(),(0 + (768.00- 0) * new Random().nextDouble())));
		this.enemy = enemy;
		fuel = 10;
	}
	
	//calls move method from movable object class and decrements fuel
	public void move()
	{
		super.move();
		fuel--;
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
}
