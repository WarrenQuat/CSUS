/*
 * Warren Quattrocchi
 * CSC 133
 * GameWorldProxy Class
 */

package com.mycompany.a4;
import java.util.*;

import com.mycompany.a4.interfaces.IGameWorld;
import com.mycompany.a4.objects.*;


/*
 * proxy class of gameworld
 * is initialized with a copy of the gameworld 
 * which is used to get information to send to observers
 * if an observers tries to interact with the gameworld it will return false
 */
public class GameWorldProxy extends Observable implements IGameWorld
{

	private GameWorld gw;

	public GameWorldProxy(GameWorld gw)
	{
		this.gw = gw;

	}
	public boolean addAsteroid()     
	{
		return false;
	}
	public boolean addPlayerShip()
	{
		return false;
	}
	
	public boolean isGameOver()
	{
		return gw.isGameOver();
	}
	public boolean isPaused()
	{
		return gw.isPaused();
	}
	
	public boolean addNPS()
	{
		return false;
	}	
	public boolean addSpaceStation()
	{
		return false;
	}	
	public boolean refillMissiles()
	{
		return false;
	}	
	public boolean tick()
	{
		return false;
	}	
	public boolean rotLeft()
	{
		return false;
	}	
	public boolean rotRight()
	{
		return false;
	}	
	public boolean incSpeed()
	{
		return false;
	}	
	public boolean decSpeed()
	{
		return false;
	}	
	public boolean rotLauncher()
	{
		return false;
	}	
	public boolean firePS()
	{
		return false;
	}	
	public boolean fireNPS()
	{
		return false;
	}
	public boolean hyperSpace()
	{
		return false;
	}	
	public boolean asteroidShot()
	{
		return false;
	}	
	public boolean NPSShot()
	{
		return false;
	}
	public boolean PSShot()
	{
		return false;
	}
	public boolean crashAsteroidPS()
	{
		return false;
	}
	public boolean crashNPS()
	{
		return false;
	}

	public boolean asteroidCol()
	{
		return false;
	}	
	public boolean crashAsteroidNPS()
	{
		return false;
	}	

	public int getScore()
	{
		return gw.getScore();
	}
	
	public int getTime()
	{
		return gw.getTime();
	}
	public int getWidth()
	{
		return gw.getWidth();
	}
	public int getHeight()
	{
		return gw.getHeight();
	}
	public boolean isSound()
	{
		return gw.isSound();
	}
	
	public int getLives()
	{
		return gw.getLives();
	}
	
	public int getMissileCount()
	{
		return gw.getMissileCount();
	}
	
	public GameWorldCollection returnWorld()
	{
		return gw.returnWorld();
	}
}
