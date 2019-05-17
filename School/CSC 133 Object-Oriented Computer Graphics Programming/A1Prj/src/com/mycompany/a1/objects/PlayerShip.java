/*
 * Warren Quattrocchi
 * CSC 133
 * PlayerShip Class
 */

package com.mycompany.a1.objects;
import com.codename1.charts.util.ColorUtil;
import com.mycompany.a1.interfaces.*;
import com.codename1.ui.geom.Point2D;


public class PlayerShip extends Ship implements Isteerable
{
	private SteerableLauncher launcher;
	
	/*
	 * Construct a player ship with origin at center of screen
	 * create a new Steerable launcher that will follow the speed and 
	 * location of the ship
	 */
	public PlayerShip()
	{
		setMissileCount(10);
		setColor(ColorUtil.BLUE);
		setLocation(new Point2D(512,384));
		setSpeed(0);
		setDirection(0);
		launcher = new SteerableLauncher();
	}
	
	//invokes move method in movable object
	//and moves launcher to location of ship
	public void move()
	{
		super.move();
		launcher.move(getLocation());
	}	
	
	//resets ship position to center of screen
	public void resetShip()
	{
		this.setLocation(new Point2D(512,384));
	}
	
	//returns launcher
	public MissileLauncher getLauncher()
	{
		return this.launcher;
	}
	
	////move ship left
	public void moveLeft()
	{
		setDirection(getDirection() + 10);
		if(getDirection() >= 360)
		{
			setDirection(getDirection()- 360);
		}
	}
	
	//move launcher(rotate)
	public void moveLauncher()
	{
		launcher.moveLeft();
	}
	
	//move ship right
	public void moveRight()
	{
		setDirection(getDirection() - 10);
		if(getDirection()< 0)
		{
			setDirection(360 - getDirection());
		}
	}
	
	//decrease speed of ship by 1
	public void decSpeed()
	{
		if(getSpeed() - 1 >=0)
		{
			setSpeed(getSpeed() -1);
			launcher.setSpeed(launcher.getSpeed() - 1);
		}
		
	}
	
	//increase speed of ship by 1
	public void incSpeed()
	{
		if(getSpeed() +1 <=10)
		{
			setSpeed(getSpeed() + 1);
			launcher.setSpeed(launcher.getSpeed() + 1);
		}
	}
	
	//fire a missile from the ship if missilecount >0
	public void fireMissile()
	{
		if(getMissileCount() > 0)
		{
			setMissileCount(getMissileCount() - 1);
		}
	}
	
	//refill missiles
	public void reload()
	{
		setMissileCount(10);
	}
	
	//returns missilecount
	
	//tostring method
	public String toString()
	{
		String s = "Player Ship:         ";
		s = s + "[speed: " + getSpeed() + "][direction: " + getDirection() + "]";
		s = s + "[color: "  						 +  ColorUtil.red(getColor()) + "," 
				 +  ColorUtil.green(getColor()) + ","
                 +  ColorUtil.blue(getColor())  
                 +  "] [location(x,y): (" + Math.round(getLocation().getX() * 10.0)/10.0 +", " 
                 +  Math.round(getLocation().getY() * 10.0)/10.0 + ")]";
		s = s + "[missiles: " + getMissileCount() + "] \n";
		s = s + "PS Missile Launcher: " + launcher.toString();
		return s;
	}
}
