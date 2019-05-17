/*
 * Warren Quattrocchi
 * CSC 133
 * GameObject Abstract Class
 */

package com.mycompany.a4.objects;
import com.codename1.ui.geom.Point2D;

public abstract class GameObject 
{
	private Point2D loc;
	private int color;
	private int size;
	
	//Create a gameObject with color and location specified by subclass

	
	//return color
	public int getColor()
	{
		return color;
	}
	
	//return location
	public Point2D getLocation()
	{
		return loc;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
	//set a new location
	/**
	 * 
	 * @param newLoc
	 */
	public void setLocation(Point2D newLoc)
	{
		loc = newLoc;
	}
}


