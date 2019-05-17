/*
 * Warren Quattrocchi
 * CSC 133
 * Ship Abstract Class
 */

package com.mycompany.a3.objects;

public abstract class Ship extends MovableObject
{
	//missile count shared by both types of ship
	private int missileCount;
	
	//return missile count
	public int getMissileCount()
	{
		return missileCount;
	}
	
	//set misile count
	public void setMissileCount(int count)
	{
		missileCount = count;
	}
}
