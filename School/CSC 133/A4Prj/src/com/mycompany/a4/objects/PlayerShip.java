/*
 * Warren Quattrocchi
 * CSC 133
 * PlayerShip Class
 */

package com.mycompany.a4.objects;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a4.interfaces.*;
import com.mycompany.a4.shapes.Triangle;


public class PlayerShip extends Ship implements Isteerable, IDrawable, ICollider
{
	private SteerableLauncher launcher;
	//Triangle t;
	private Triangle t;
	
	/*
	 * Construct a player ship with origin at center of screen
	 * create a new Steerable launcher that will follow the speed and 
	 * location of the ship
	 */
	public PlayerShip()
	{

	}

	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn)
	{
		g.setColor(this.getColor());
		t.translate((int)getLocation().getX(),(int)getLocation().getY());
		t.rotate(-getDirection());
		t.draw(g, pCmpRelPrnt, pCmpRelScrn);	
		g.drawArc((int)((this.getLocation().getX() + pCmpRelPrnt.getX() - 75)), (int)((this.getLocation().getY() + pCmpRelPrnt.getY()) -75),150,150, 360, 360 );
		
		launcher.draw(g, pCmpRelPrnt, pCmpRelScrn);
		t.resetTransform();
		
		
	}
	
	public PlayerShip(int height, int width)
	{
		setMissileCount(10);
		setColor(ColorUtil.BLUE);
		setLocation(new Point2D(width/2,height/2));
		setSpeed(0);
		setDirection(0);
		setHeight(height);
		setWidth(width);
		this.setSize(150);
		launcher = new SteerableLauncher(height,width);
		t = new Triangle(getSize(), getSize(), getColor());


	}
	
	//invokes move method in movable object
	//and moves launcher to location of ship
	public void move(int fps)
	{
		super.move(fps);
		launcher.move(getLocation());
	}	
	
	//resets ship position to center of screen
	public void resetShip()
	{
		this.setLocation(new Point2D(getWidth()/2,getHeight()/2));
	}
	
	//returns launcher
	public MissileLauncher getLauncher()
	{
		return this.launcher;
	}
	
	////move ship left
	public void moveLeft()
	{
		setDirection(getDirection() - 15);
		if(getDirection()< 0)
		{
			setDirection(360 - -getDirection());
		}
	}

	//move launcher(rotate)
	public void moveLauncherLeft()
	{
		launcher.moveLeft();
	}
	public void moveLauncherRight()
	{
		launcher.moveRight();
	}
	
	//move ship right
	public void moveRight()
	{
		setDirection(getDirection() + 15);
		if(getDirection() >= 360)
		{
			setDirection(getDirection()- 360);
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
		if(getSpeed() +1 <=20)
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
			//fire.play();
		}
	}
	
	//refill missiles
	public void reload()
	{
		setMissileCount(10);
	}
	
	//returns missilecount
	public int getMissileCount()
	{
		return super.getMissileCount();
	}
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

	public boolean collidesWith(ICollider otherObject) 
	{
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
		if (otherObject instanceof NonPlayerShip)
			System.out.println("a non player ship and a player ship have collided");
		else if(otherObject instanceof Missile)
			if(((Missile)otherObject).isEnemy())
				System.out.println("An enemy missile has struck the player ship");
		else if(otherObject instanceof SpaceStation)
			if(((SpaceStation)otherObject).getVisibility())
				System.out.println("the playerShip has been refilled");
		
	}
}
