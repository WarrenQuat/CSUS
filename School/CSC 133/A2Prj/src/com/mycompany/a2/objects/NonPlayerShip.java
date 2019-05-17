/*
 * Warren Quattrocchi
 * CSC 133
 * NonPlayerShip Class
 */

package com.mycompany.a2.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;
import java.util.Random;

public class NonPlayerShip extends Ship
{
	private int size;
	private NonSteerableLauncher launcher;
	
	/*
	 * Constructs a new non player ship
	 * random location
	 * set color
	 * creates a new non player ship launcher(nonSteerable)
	 */
	public NonPlayerShip()
	{
		
	}
	public NonPlayerShip(int height, int width)
	{
		setMissileCount(2);
		setColor(ColorUtil.WHITE);
		setLocation(new Point2D(0 + (height - 0) * new Random().nextDouble(),(0 + (width- 0) * new Random().nextDouble())));
		setSpeed(new Random().nextInt(9 + 1) + 1);
		setDirection(new Random().nextInt(358 +1) + 1);
		size = new Random().nextInt(2) + 1;
		launcher = new NonSteerableLauncher(getLocation());
		launcher.setSpeed(this.getSpeed());
		launcher.setDirection(this.getDirection());
	}
	
	//moves ship and launcher
	public void move()
	{
		super.move();
		launcher.move(getLocation());
	}
	
	public void launch()
	{
		setMissileCount(getMissileCount() - 1);
	}
	
	//returns size of ship
	public String getSize()
	{
		if (size == 1)
			return "small";
		else
			return "Large";
	}
	
	//returns the launcher
	public MissileLauncher getLauncher()
	{
		return this.launcher;
	}
	
	//toString
	public String toString()
	{
		String s = "Non-Player Ship:     [Size: " + getSize() + "]";
		s = s + "[speed: " + getSpeed() + "][direction: " + getDirection() + "]";
		s = s + "[color: "  						 +  ColorUtil.red(getColor()) + "," 
				 +  ColorUtil.green(getColor()) + ","
                 +  ColorUtil.blue(getColor())  
                 +  "] [location(x,y): (" + Math.round(getLocation().getX() * 10.0)/10.0 +", " 
                 +  Math.round(getLocation().getY() * 10.0)/10.0 + ")]";
		s = s + "[missiles: " + getMissileCount() + "] \n";
		s = s + "NPS Missile Launcher:" + launcher.toString(); 
		
		return s;
	}
}
