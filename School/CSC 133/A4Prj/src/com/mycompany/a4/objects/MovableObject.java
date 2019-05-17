/*
 * Warren Quattrocchi
 * CSC 133
 * MovableObject Abstract Class
 */

package com.mycompany.a4.objects;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a4.interfaces.*;


public abstract class MovableObject extends GameObject implements Imovable
{
		private int speed;
		private int direction;
		private int height;
		private int width;
		
		
		//moves all movable objects depending on speed and heading
		public void move(int fps)
		{
			double x = getLocation().getX() + (((Math.cos(Math.toRadians(90 - getDirection()))))* (getSpeed() * (double) fps/70));
			double y = getLocation().getY() + (((Math.sin(Math.toRadians(90 - getDirection()))))* (getSpeed() * (double) fps/70));
			if(x >= width + 100)
				x = -100;
			else if(y >= height + 100)
				y = -100;
			else if(x <= -100)
				x = width + 100;
			else if(y <= -100)
				y = height + 100;
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
		public void setHeight(int height)
		{
			this.height = height;
		}
		public void setWidth(int width)
		{
			this.width = width;
		}
		
		public int getHeight()
		{
			return height;
		}
		
		public int getWidth()
		{
			return width;
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
