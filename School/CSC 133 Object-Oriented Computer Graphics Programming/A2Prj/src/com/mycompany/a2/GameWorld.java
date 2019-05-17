/*
 * Warren Quattrocchi
 * CSC 133
 * GameWorld Class
 */

package com.mycompany.a2;
import java.util.*;

import com.mycompany.a2.interfaces.IGameWorld;
import com.mycompany.a2.interfaces.IIterator;
import com.mycompany.a2.interfaces.Imovable;
import com.mycompany.a2.objects.*;

public class GameWorld extends Observable implements IGameWorld
{
	private GameWorldCollection gameObj;
	private int lives;
	private int score;
	private int gameTime;
	private boolean shipSpawned;
	private boolean ssSpawned;
	private boolean sound;
	private int height;
	private int width;

	public void init()
	{
		// initialize score, lives, game time, and a gameObject vector
		// on creation of a new game
		score = 0;
		lives = 3;
		gameTime = 0;
		gameObj = new GameWorldCollection();
		shipSpawned = false;
		ssSpawned = false;
		sound = true;
		notifyObservers();	
	}
	
	//Add a new Asteroid into the game world
	public void addAsteroid()     
	{
		gameObj.add(new Asteroid(getHeight(), getWidth()));
		System.out.println("Asteroid added");
		notifyObservers();
	}
	
	//add a new PlayerShip into the world
	public void addPlayerShip()
	{
		//if a PlayerShip is already spawned, a new one will not be added
		if(shipSpawned)
		{
				System.out.println("A player ship is already spawned");
				return;
		}
		gameObj.add(new PlayerShip(getHeight(), getWidth()));
		System.out.println("Player ship added");
		shipSpawned = true;
		notifyObservers();
	}
			
	//Add a NonPlayerShip into the game world
	public void addNPS()
	{
		gameObj.add(new NonPlayerShip(getHeight(), getWidth()));
		System.out.println("Non-Player ship added");
		notifyObservers();
	}
	
	//Add a space station into the game world
	public void addSpaceStation()
	{
		//if a space station is already spawned, one will not be added
		if(ssSpawned)
		{
				System.out.println("A Space Station is already spawned");
				return;
		}
		gameObj.add(new SpaceStation(getHeight(), getWidth()));
		ssSpawned = true;
		System.out.println("Space station added");
		notifyObservers();
	}
	
	//refill missiles
	public void firePS()
	{
		//missiles cannot be refilled if no PlayerShip is spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				if(((PlayerShip)current).getMissileCount() > 0)
				{
					Missile mis = new Missile(false);
					mis.setLocation(((PlayerShip)current).getLauncher().getLocation());
					mis.setDirection(((PlayerShip)current).getLauncher().getDirection());
					mis.setSpeed(((PlayerShip)current).getLauncher().getSpeed() + 4);
					((PlayerShip)current).fireMissile();
					gameObj.add(mis);
					System.out.println("Friendly missile added");
					notifyObservers();
					return;
				}else{
					System.out.println("There are no missiles left");
					return;
				}
			}
		}
		System.out.println("There is not currently a player ship spawned");
	}
	
	/*
	 * The world will be updated
	 * All GameObjects will invoke their move method
	 * Game time will be incremented by 1
	 */
	public void tick()
	{
		gameTime++;
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if (current instanceof Imovable)
				((MovableObject)current).move();
			if (current instanceof SpaceStation)
				((SpaceStation)current).checkBlink(gameTime);
			if(current instanceof Missile)
				if(((Missile) current).getFuel() <= 0)
					iter.remove();
		}
		notifyObservers();
	}
	
	//rotate the ship left
	public void rotLeft()
	{
		//only rotate if a PlayerShip is currently spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext ())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				((PlayerShip)current).moveLeft();
				System.out.println("Heading +10");
				notifyObservers();
				return;
			}	
		}
		System.out.println("There is no playerShip spawned");
	}
	
	//rotate the ship right
	public void rotRight()
	{
		//only rotate if a PlayerShip is currently spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				((PlayerShip)current).moveRight();
				System.out.println("Heading -10");
				notifyObservers();
				return;
			}	
		}
		System.out.println("There is no playerShip spawned");
	}
	
	//increase the speed of the PlayerShip by 10
	public void incSpeed()
	{
		//only increase if a PlayerShip is currently spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				((PlayerShip)current).incSpeed();			
				System.out.println("Speed increased by 1");
				notifyObservers();
				return;
			}	
		}
		System.out.println("There is no playerShip spawned");
	}
	
	//Decrease the speed of the PlayerShip by 10
	public void decSpeed()
	{
		//only decrease if a PlayerShip is currently spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				((PlayerShip)current).decSpeed();
				System.out.println("Speed decreased by 1");
				notifyObservers();
				return;
			}	
		}
		System.out.println("There is no playerShip spawned");
	}
	
	//rotate the ships launcher by 10 degrees
	public void rotLauncherLeft()
	{
		//only rotate if a PlayerShip, and therefore a MissileLauncher is spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				((PlayerShip)current).moveLauncherLeft();
				System.out.println("launcher heading +10");
				notifyObservers();
				return;
			}	
		}
		System.out.println("There is no playerShip spawned");
	}
	
	public void rotLauncherRight()
	{
		//only rotate if a PlayerShip, and therefore a MissileLauncher is spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				((PlayerShip)current).moveLauncherRight();
				System.out.println("launcher heading -10");
				notifyObservers();
				return;
			}	
		}
		System.out.println("There is no playerShip spawned");
	}
	//fire a Missile from the PlayerShip
	public void refillMissiles()
	{
		if(shipSpawned && ssSpawned)
		{
			IIterator iter = gameObj.getIterator();
			while(iter.hasNext())
			{
				GameObject current = (GameObject)iter.getNext();
				if(current instanceof PlayerShip)
					((PlayerShip)current).reload();
				notifyObservers();
				System.out.println("Ammo refilled");
				return;
			}
		}else
			System.out.println("there is no playerShip or Space Station spawned");
	}
	
	//Fire a Missile from a NonPlayerShip
	public void fireNPS()
	{
		//Only fire if a NonPlayerShip is spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof NonPlayerShip && ((NonPlayerShip)current).getMissileCount() > 0)
			{
					Missile mis = new Missile(true);
					mis.setLocation(((NonPlayerShip)current).getLocation());
					mis.setDirection(((NonPlayerShip)current).getLauncher().getDirection());
					mis.setSpeed(((NonPlayerShip)current).getSpeed() + 4);
					gameObj.add(mis);
					((NonPlayerShip)current).launch();
					System.out.println("enemy missile added");
					notifyObservers();
					return;
			}
		}
		System.out.println("There is not currently a non player ship with missiles left");		
	}
	
	//Returns the ship to it's original position
	//at the center of the screen
	public void hyperSpace()
	{
		//only return the ship if a PlayerShip is currently spawned
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
			//invoke resetShip() method from PlayerShip
				((PlayerShip)current).resetShip();
				System.out.println("PS location reset");
				notifyObservers();
				return;
			}
		}
		System.out.println("There is no playership spawned");
	}
	
	/*A friendly missile has hit an asteroid
	 * removes a random asteroid and missile from the game
	 * increments score by 100
	 */
	public void asteroidShot()
	{
		//collision can only occur if at least 1 asteroid
		//and one missile are currently spawned
		int astAmt = 0;
		int misAmt = 0;
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof Asteroid)
			{
				astAmt++;
			}
			if(current instanceof Missile)
			{
				if(!((Missile)current).isEnemy())
					misAmt++;
			}
		}
		if(astAmt > 0 && misAmt > 0)
		{
			remove(new Asteroid());
			removeMissile(new Missile(false), false);
			System.out.println("A friendly missile has struck an asteroid +100 points!");
			score = score + 100;
			notifyObservers();
		}else
			System.out.println("An asteroid and a friendly missile are not spawned");
	}
	
	/*A friendly missile has hit a NonPlayerShip
	 * removes a random missile and a random NPS from the game
	 * increments score by 500
	 */
	public void NPSShot()
	{
		//can only occur if a friendly missile and 
		//a NPS are spawned
		int npsAmt = 0;
		int misAmt = 0;
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof NonPlayerShip)
			{
				npsAmt++;
			}
			if(current instanceof Missile)
			{
				if(!((Missile)current).isEnemy())
					misAmt++;
			}
		}
		if(npsAmt > 0 && misAmt > 0)
		{
			remove(new NonPlayerShip());
			removeMissile(new Missile(false),false);
			System.out.println("A friendly missile has struck a NPS +500 points!");
			score = score + 500;
			notifyObservers();
		}else
			System.out.println("A friendly missile and an NPS are not spawned");
	}
	
	/*A enemy missile has hit the PlayerShip
	 *removes a random Missile and the PlayerShip from the world
	 *decrements lives by 1
	 *if lives > 0, a new PlayerShip is spawned
	 *if lives == 0, game is over
	 */
	public void PSShot()
	{
		//collision can only occur if an enemy missile and a PlayerShip are spawned
		int psAmt = 0;
		int misAmt = 0;
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				psAmt++;
			}
			if(current instanceof Missile)
			{
				if(((Missile)current).isEnemy())
					misAmt++;
			}
		}
		if(psAmt > 0 && misAmt > 0)
		{
			remove(new PlayerShip());
			removeMissile(new Missile(true), true);
			System.out.println("An enemy missile has struck the PS, lives -1");
			decLives();
			notifyObservers();
		}else
			System.out.println("An enemy missile and a PlayerShip are not spawned");	
	}
	
	/*A playerShip has been hit by an asteroid
	 *removes a random asteroid and the PlayerShip
	 *decrements lives
	 *if lives > 0, a new PlayerShip is spawned
	 *if lives == 0, game is over
	 */
	public void crashAsteroidPS()
	{
		//collision can only occur if at least one asteroid and
		//a PlayerShip are spawned
		int astAmt = 0;
		int psAmt = 0;
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
				psAmt++;
			if(current instanceof Asteroid)
				astAmt++;
		}
		if(psAmt > 0 && astAmt > 0)
		{
			remove(new PlayerShip());
			remove(new Asteroid());
			System.out.println("The PlayerShip has crashed into an asteroid lives -1");
			decLives();
			notifyObservers();
		}else
			System.out.println("An asteroid and a playerShip are not spawned");
	}
	
	/*A PlayerShip has hit a NonPlayerShip
	 *removes a random NonPlayerShip and the PlayerShip from the game
	 *decrement lives by 1
	 *if lives > 0 spawn new PlayerShip
	 *if lives == 0, game is over
	 */	
	public void crashNPS()
	{
		//collision can only occur if at least one NPS and
		//a PlayerShip is spawned
		int psAmt = 0;
		int npsAmt = 0;
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
			{
				psAmt++;
			}
			if(current instanceof NonPlayerShip)
			{
					npsAmt++;
			}
		}
		if(npsAmt > 0 && psAmt > 0)
		{
			remove(new NonPlayerShip());
			remove(new PlayerShip());;
			System.out.println("An enemy ship has struck the PS, lives -1");
			decLives();
			notifyObservers();
		}else
			System.out.println("An NPS and a PlayerShip are not spawned");	
	}
	
	/*2 asteroids have collided
	 * removes 2 asteroids from the game world
	 */
	public void asteroidCol()
	{
		int astAmt = 0;
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof Asteroid)
			{
				astAmt++;
			}
		}
		if(astAmt > 1)
		{
			remove(new Asteroid());
			remove(new Asteroid());
			System.out.println("2 asteroids have collided and been removed from the world");
			notifyObservers();
		}else
			System.out.println("2 asteroids are not spawned");
	}
	
	/*An asteroid has hit a NonPlayerShip
	 * removes 2 NPS's from the game world
	 */
	public void crashAsteroidNPS()
	{
		//collision can only occur if at least 1 asteroid and
		//one NPS are spawned
		int npsAmt = 0;
		int astAmt = 0;
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof NonPlayerShip)
				npsAmt++;
			if(current instanceof Asteroid)
				astAmt++;
		}
		if(npsAmt > 0 && astAmt > 0)
		{
			remove(new NonPlayerShip());
			remove(new Asteroid());
			System.out.println("An asteroid has struck a nonPlayerShip");
			decLives();
			notifyObservers();
		}else
			System.out.println("An asteroid and a nonPlayerShip are not spawned");
	}
	
	//decrement lives
	public void decLives()
	{
		lives--;
		shipSpawned = false;
		if(lives <= 0)
		{
			System.out.println("Game over");
			init();
		}
	}
	
	//set changed and send gameworldproxy to the observers
	public void notifyObservers()
	{
		setChanged();
		notifyObservers(new GameWorldProxy(this));
	}
	
	//toggle sound, each click will switch current value
	public void toggleSound()
	{
		if (sound)
			sound = false;
		else
			sound = true;
		notifyObservers();
	}
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public int getScore() {
		return score;
	}

	public int getTime() {
		return gameTime;
	}

	public int getLives() {
		return lives;
	}

	public boolean isSound() {
		return sound;
	}

	public int getMissileCount()
	{
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current instanceof PlayerShip)
				return ((PlayerShip) current).getMissileCount();
		}
		return 0;
				
	}
	//remove a missile from the gameworld sends a boolean on missile type(enemy/friendly)
	public void removeMissile(Object o,boolean enemy)
	{
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current.getClass().isInstance(o))
			{
				if(((Missile)current).isEnemy() == enemy)
				{
					iter.remove();
					return;
				}
			}
		}
	}
	
	//remove an object from the gameworld
	public void remove(Object o)
	{
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current.getClass().isInstance(o))
			{
				iter.remove();
				return;
			}
		}
	}
	
	public GameWorldCollection returnWorld()
	{
		return gameObj;
	}
}