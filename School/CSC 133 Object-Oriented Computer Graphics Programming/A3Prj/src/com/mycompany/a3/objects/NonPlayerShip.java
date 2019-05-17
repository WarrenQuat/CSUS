/*
 * Warren Quattrocchi
 * CSC 133
 * NonPlayerShip Class
 */

package com.mycompany.a3.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;

import java.util.Random;

public class NonPlayerShip extends Ship implements IDrawable, ICollider
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
		setColor(ColorUtil.rgb(255, 0, 0));
		setLocation(new Point2D(0 + (width - 0) * new Random().nextDouble(),(0 + (height- 0) * new Random().nextDouble())));
		setSpeed(new Random().nextInt(9 + 1) + 1);
		setDirection(new Random().nextInt(358 +1) + 1);
		setHeight(height);
		setWidth(width);
		size = new Random().nextInt(40) + 1;
		if(size >= 20)
			size = 75;
		else
			size = 150;
		this.setSize(size);
		launcher = new NonSteerableLauncher(getLocation(), size);
		launcher.setSpeed(this.getSpeed());
		launcher.setDirection(this.getDirection());
	}
	
	//moves ship and launcher
	public void move(int fps)
	{
		super.move(fps);
		launcher.move(getLocation());
	}
	
	public void launch()
	{
		setMissileCount(getMissileCount() - 1);
	}
	
	//returns size of ship
	
	//returns the launcher
	public MissileLauncher getLauncher()
	{
		return this.launcher;
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt)
	{
		g.setColor(this.getColor());
		int[] pointsx = {(int)(this.getLocation().getX() + pCmpRelPrnt.getX() - getSize()/2),
				 		(int)(this.getLocation().getX() + pCmpRelPrnt.getX() + 0),
				 		(int)(this.getLocation().getX() + pCmpRelPrnt.getX() + getSize()/2)};
		int[] pointsy = {(int)(this.getLocation().getY() + pCmpRelPrnt.getY() - getSize()/2),
				 		(int)(this.getLocation().getY() + pCmpRelPrnt.getY() + getSize()/2),
				 		(int)(this.getLocation().getY() + pCmpRelPrnt.getY() - getSize()/2)};
		g.drawPolygon(pointsx, pointsy, 3);
		launcher.draw(g, pCmpRelPrnt);
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
	@Override
	public boolean collidesWith(ICollider otherObject) {
		boolean result = false;
		
		int thisCenterX = (int)this.getLocation().getX()+ this.getSize()/2 ;
		int thisCenterY = (int)this.getLocation().getY() + this.getSize()/2;
		int otherCenterX = (int)((GameObject)otherObject).getLocation().getX() + ((GameObject)otherObject).getSize()/2;
		int otherCenterY = (int)((GameObject)otherObject).getLocation().getY() + ((GameObject)otherObject).getSize()/2;
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
		if (otherObject instanceof PlayerShip)
			System.out.println("a non player ship and a player ship have collided");
		else if(otherObject instanceof Missile)
			if(!((Missile)otherObject).isEnemy())
				System.out.println("A friendly missile has struck an enemy ship");
		else if(otherObject instanceof SpaceStation)
			if(((SpaceStation)otherObject).getVisibility())
				System.out.println("an enemy ship has been refilled");
		
	}
	public void reload() {
		setMissileCount(2);
		
	}
}
