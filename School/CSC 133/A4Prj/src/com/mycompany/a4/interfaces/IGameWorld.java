/*
 * Warren Quattrocchi
 * CSC 133
 * IGameWorld interface
 */

package com.mycompany.a4.interfaces;

import com.mycompany.a4.objects.GameWorldCollection;

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

	public boolean isGameOver();
	
	public boolean isPaused();
}

