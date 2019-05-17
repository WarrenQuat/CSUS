/*
 * Warren Quattrocchi
 * CSC 133
 * NonPlayerShip Class
 */

package com.mycompany.a4.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a4.interfaces.ICollider;
import com.mycompany.a4.interfaces.IDrawable;
import com.mycompany.a4.shapes.Triangle;

import java.util.Random;

public class NonPlayerShip extends Ship implements IDrawable, ICollider
{
	private int size;
	private Triangle t;
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
		t = new Triangle(getSize(), getSize(), getColor());
		launcher = new NonSteerableLauncher(getLocation(), size);
		launcher.setSpeed(this.getSpeed());
		launcher.setDirection(new Random().nextInt(358 +1) + 1);
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
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn)
	{
		g.setColor(this.getColor());
		t.translate(0, 0);
		t.translate((int)getLocation().getX(),(int)getLocation().getY());
		t.rotate(-getDirection());
		t.draw(g, pCmpRelPrnt, pCmpRelScrn);	
		/*int[] pointsx = {(int)(this.getLocation().getX() + pCmpRelPrnt.getX() - getSize()/2),
						 (int)(this.getLocation().getX() + pCmpRelPrnt.getX() + 0),
						 (int)(this.getLocation().getX() + pCmpRelPrnt.getX() + getSize()/2)};
		int[] pointsy = {(int)(this.getLocation().getY() + pCmpRelPrnt.getY() - getSize()/2),
						 (int)(this.getLocation().getY() + pCmpRelPrnt.getY() + getSize()/2),
						 (int)(this.getLocation().getY() + pCmpRelPrnt.getY() - getSize()/2)};
		g.fillPolygon(pointsx, pointsy, 3);
		*/
		
		launcher.draw(g, pCmpRelPrnt, pCmpRelScrn);
		t.resetTransform();
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
