/*
 * Warren Quattrocchi
 * CSC 133
 * FixedObject Abstract Class
 */
package com.mycompany.a1.objects;

public abstract class FixedObject extends GameObject
{
	//ID for fixed objects
	private static String ID;
	//counter to create sequential id's
	private int counter = 0;
	
	//FixedObject constructor
	public FixedObject()
	{
		createID();
	}
	
	//Creates a sequential ID 
	public void createID()
	{
		FixedObject.ID = String.valueOf(counter++);
	}
	
	//returns ID
	public String getID()
	{
		return ID;
	}
}
