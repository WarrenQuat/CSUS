/*
 * Warren Quattrocchi
 * CSC 133
 * MovableObject Abstract Class
 */

package com.mycompany.a1.objects;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a1.interfaces.*;


public abstract class MovableObject extends GameObject implements Imovable
{
		private int speed;
		private int direction;
		
		//moves all movable objects depending on speed and heading
		public void move()
		{
			Point2D newLoc = getLocation();
			double y = newLoc.getY();
			double x = newLoc.getX();
			x = x + ((Math.cos(90 - getDirection()))* getSpeed());
			y = y + ((Math.sin(90 - getDirection()))* getSpeed());
			Point2D loc = new Point2D(x,y);
			setLocation(loc);	
		}
		//Returns direction
		public int getDirection()
		{
			return direction;
		}
		
		//sets direction of movable object
		/**
		 * 
		 * @param dir
		 */
		public void setDirection(int dir)
		{
			direction = dir;
		}
		
		//returns speed
		public int getSpeed()
		{
			return speed;
		}
		
		//sets speed
		/**
		 * 
		 * @param speed
		 */
		public void setSpeed(int speed)
		{
			this.speed = speed;
		}
}
