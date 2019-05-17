/*
 * Warren Quattrocchi
 * CSC 133
 * IGameWorld interface
 */

package com.mycompany.a2.interfaces;

import com.mycompany.a2.objects.GameWorldCollection;

public interface IGameWorld 
{
	
	public int getScore();

	public int getTime();

	public int getLives();

	public boolean isSound();
	
	public int getWidth();
	
	public int getHeight();
	
	public int getMissileCount();
	
	public GameWorldCollection returnWorld();
}

