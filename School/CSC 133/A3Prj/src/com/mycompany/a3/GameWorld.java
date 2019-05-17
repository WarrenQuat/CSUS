/*
 * Warren Quattrocchi
 * CSC 133
 * GameWorld Class
 */

package com.mycompany.a3;
import java.util.*;

import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IGameWorld;
import com.mycompany.a3.interfaces.IIterator;
import com.mycompany.a3.interfaces.Imovable;
import com.mycompany.a3.objects.*;

public class GameWorld extends Observable implements IGameWorld
{
	private GameWorldCollection gameObj;
	private Vector<ICollider> collisionVectorPS;
	private Vector<ICollider> collisionVectorAsteroid;
	private Vector<ICollider> collisionVectorNPS;
	private Vector<ICollider> trash;
	
	private Sound fire = new Sound("missilefire.wav");
	private Sound death = new Sound("death.wav");
	private Sound explosion = new Sound("explosion.wav");
	private BGSound bg = new BGSound("space.wav");

	private boolean gameOver, paused;
	private int lives;
	private int score;
	private int gameTime;
	private boolean shipSpawned;
	private boolean sound;
	private int height;
	private int width;
	private int totalNPS;
	private int fps;

	public void init()
	{
		// initialize score, lives, game time, and a gameObject vector
		// on creation of a new game
		paused = false;
		bg.play();
		death.setVolume(10);
		explosion.setVolume(20);
		setGameOver(false);
		score = 0;
		lives = 3;
		gameTime = 0;
		gameObj = new GameWorldCollection();
		shipSpawned = false;
		sound = true;
		totalNPS = 0;
		collisionVectorPS       = new Vector<ICollider>();
		collisionVectorNPS	    = new Vector<ICollider>();
		collisionVectorAsteroid = new Vector<ICollider>();
		trash					= new Vector<ICollider>();
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
			totalNPS ++;
			System.out.println("Non-Player ship added");
			notifyObservers();
	}
	public int getTotalNPS()
	{
		return totalNPS;
	}
	//Add a space station into the game world
	public void addSpaceStation()
	{
		//if a space station is already spawned, one will not be added

		gameObj.add(new SpaceStation(getHeight(), getWidth()));
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
					
					Missile mis = new Missile(false,getHeight(), getWidth());
					double x = ((PlayerShip)current).getLocation().getX();
					double y = ((PlayerShip)current).getLocation().getY() + 75;
					mis.setLocation(new Point2D(x, y));
					mis.setDirection(((PlayerShip)current).getLauncher().getDirection());
					mis.setSpeed((((PlayerShip)current).getLauncher().getSpeed() + 20));
					((PlayerShip)current).fireMissile();
					gameObj.add(mis);
					System.out.println("Friendly missile added");
					if(isSound() && !isPaused())
						fire.play();
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
	 * 
	 * collisions of all collidable objects will be checked
	 * and put into designated collision vectors
	 * then checked
	 */
	public void tick()
	{
		gameTime++;
		if(gameTime == 1)
			addPlayerShip();
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if (current instanceof Imovable)
				((MovableObject)current).move(fps);
			if (current instanceof SpaceStation)
				((SpaceStation)current).checkBlink(gameTime);
			if(current instanceof Missile)
				if(((Missile) current).getFuel() <= 0)
					iter.remove();
		}
		
		//Identify collision
		iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			ICollider curObj = (ICollider)iter.getNext();
			
			IIterator iter2 = gameObj.getIterator();
			while(iter2.hasNext())
			{
				ICollider otherObj = (ICollider)iter2.getNext();
				if(otherObj != curObj)
					if(curObj.collidesWith(otherObj))
						if (curObj instanceof PlayerShip)
						{
							collisionVectorPS.add(curObj);
							collisionVectorPS.add(otherObj);
						}
						else if(curObj instanceof NonPlayerShip)
						{
							collisionVectorNPS.add(curObj);
							collisionVectorNPS.add(otherObj);	
						}
						else if(curObj instanceof Asteroid)
						{
							if(collisionVectorAsteroid.isEmpty())
								collisionVectorAsteroid.add(curObj);
							collisionVectorAsteroid.add(otherObj);			
						}
			}
		}
		//Handle Collisions for playership
		if(!collisionVectorPS.isEmpty())
		{
			//return iterator for collion vector
			Iterator<ICollider> psIterator = collisionVectorPS.iterator();
			ICollider ship = collisionVectorPS.elementAt(0);
			while(psIterator.hasNext())
			{
				ICollider curObj = psIterator.next();
				if(curObj != ship)
					ship.handleCollision(curObj);
				
				//if an asteroid is detected
				if(curObj instanceof Asteroid)
					crashAsteroidPS(psIterator,curObj,ship);
				
				//if an enemy missile is detected
				else if(curObj instanceof Missile && ((Missile)curObj).isEnemy())
					PSShot(psIterator,curObj,ship);
				
				//if a non player ship is detected
				else if(curObj instanceof NonPlayerShip)
					this.crashNPS(psIterator,curObj,ship);
				
				else if(curObj instanceof SpaceStation)
					this.refillMissiles(psIterator,curObj,ship);
			}
			//clear the collision vector and spawn a new ship
			collisionVectorPS.clear();
			
		}
		if(!collisionVectorNPS.isEmpty())
		{
			//return iterator for collion vector
			Iterator<ICollider> npsIterator = collisionVectorNPS.iterator();
			ICollider nps = collisionVectorNPS.elementAt(0);
			while(npsIterator.hasNext())
			{
				ICollider curObj = npsIterator.next();
				if(curObj != nps)
					nps.handleCollision(curObj);
				if(curObj instanceof Missile && !((Missile)curObj).isEnemy())
					this.NPSShot(npsIterator,curObj,nps);
				else if(curObj instanceof Asteroid)
					this.crashAsteroidNPS(npsIterator,curObj,nps);
				else if(curObj instanceof SpaceStation)
					this.refillMissiles(npsIterator,curObj,nps);
			}
			collisionVectorNPS.clear();
		}
		if(!collisionVectorAsteroid.isEmpty())
		{
			Iterator<ICollider> astIterator = collisionVectorAsteroid.iterator();
			ICollider ast = collisionVectorAsteroid.elementAt(0);
			while(astIterator.hasNext())
			{
				ICollider curObj = astIterator.next();
				if(curObj != ast)
					ast.handleCollision(curObj);
				if(curObj instanceof Missile && !((Missile)curObj).isEnemy())
					this.asteroidShot(astIterator,curObj,ast);
				else if(curObj instanceof Asteroid && curObj != ast)
					this.asteroidCol(astIterator,curObj,ast);
			}
			collisionVectorAsteroid.clear();
		}
		//delete collided objects
		Iterator<ICollider> trashIterator = trash.iterator();
		while (trashIterator.hasNext())
		{
			ICollider curObj = trashIterator.next();
			remove(curObj);
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
				System.out.println("Heading -20");
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
				System.out.println("Heading +20");
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
				System.out.println("launcher heading +20");
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
				System.out.println("launcher heading -20");
				notifyObservers();
				return;
			}	
		}
		System.out.println("There is no playerShip spawned");
	}
	//refill the player ships missiles
	public void refillMissiles(Iterator<ICollider> psIterator, ICollider curObj, ICollider ship)
	{
		if(((SpaceStation)curObj).getVisibility())
		{
			if(ship instanceof PlayerShip)
				((PlayerShip)ship).reload();
			else if (ship instanceof NonPlayerShip)
				((NonPlayerShip)ship).reload();
			System.out.println("Ammo refilled");
		}else
			System.out.println("SpaceStation is blinking");
		psIterator.remove();
		
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

					Missile mis = new Missile(true,getHeight(), getWidth());
					double x = ((NonPlayerShip)current).getLocation().getX();
					double y = ((NonPlayerShip)current).getLocation().getY() + ((NonPlayerShip)current).getSize()/2;
					mis.setLocation(new Point2D(x, y));
					mis.setDirection(((NonPlayerShip)current).getLauncher().getDirection());
					mis.setSpeed(((NonPlayerShip)current).getSpeed() + 20);
					gameObj.add(mis);
					((NonPlayerShip)current).launch();
					System.out.println("enemy missile added");
					notifyObservers();
					return;
			}
		}		
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
	 * removes a collided asteroid and missile from the game
	 * increments score by 100
	 */
	public void asteroidShot(Iterator<ICollider> astIterator, ICollider curObj, ICollider ast)
	{
		trash.add(ast);
		astIterator.remove();
		trash.add(curObj);
		if(isSound())
			explosion.play();
		score = score + 100;
	}
	
	/*A friendly missile has hit a NonPlayerShip
	 * removes collided missile and nonplayership
	 * increments score by 500
	 */
	public void NPSShot(Iterator<ICollider> npsIterator, ICollider curObj, ICollider nps)
	{
		trash.add(nps);
		npsIterator.remove();
		trash.add(curObj);
		totalNPS--;
		score = score + 500;
	}
	
	/*A enemy missile has hit the PlayerShip
	 *removes a collided Missile and PlayerShip from the world
	 *decrements lives to check for gameover/spawn new ship
	 */
	public void PSShot(Iterator<ICollider> psIterator, ICollider curObj, ICollider ship)
	{
		trash.add(curObj);
		psIterator.remove();
		trash.add(ship);
		decLives();
	}
	
	/*A playerShip has been hit by an asteroid
	 *removes collided asteroid and PlayerShip
	 *decrements lives to check for gameover/spawn new ship
	 */
	public void crashAsteroidPS(Iterator<ICollider> psIterator, ICollider curObj, ICollider ship)
	{
    	trash.add(curObj);
		trash.add(ship);
		psIterator.remove();
		decLives();
	}

	/*the playership has crashed into a nonplayer ship
	 * removes collided nps and playerhsip from game world
	 * decrements lives to check for gameover/spawn new ship
	 */
	
	public void crashNPS(Iterator<ICollider> psIterator, ICollider curObj, ICollider ship)
	{
		trash.add(curObj);
		psIterator.remove();
		trash.add(ship);
		totalNPS --;
		decLives();
	}
	
	/*2 asteroids have collided
	 * removes 2 collided asteroids from the game world
	 */
	public void asteroidCol(Iterator<ICollider> astIterator, ICollider curObj, ICollider ast)
	{
		trash.add(curObj);
		trash.add(ast);
		astIterator.remove();
	}
	
	/*An asteroid has hit a NonPlayerShip
	 * removes collide asteroid and nps from game world
	 */
	public void crashAsteroidNPS(Iterator<ICollider> npsIterator, ICollider curObj, ICollider nps)
	{
		trash.add(nps);
		npsIterator.remove();
		trash.add(curObj);
		totalNPS --;
	}
	
	//decrement lives and check for gameover
	//if not game over, spawn a new ship
	public void decLives()
	{
		lives--;
		shipSpawned = false;	
		if(lives <= 0)
		{
			System.out.println("Game over");
			if(isSound() && !isPaused())
				death.play();
			setGameOver(true);
			notifyObservers();
			return;
		}
		this.addPlayerShip();
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
	
	public void setGameOver(boolean over)
	{
		gameOver = over;
	}
	public boolean isGameOver()
	{
		return gameOver;
	}
	public void setPaused(boolean pause)
	{
		paused = pause;
	}
	public boolean isPaused()
	{
		return paused;
	}
	
	public void remove(Object o)
	{
		IIterator iter = gameObj.getIterator();
		while(iter.hasNext())
		{
			GameObject current = (GameObject)iter.getNext();
			if(current == o)
				iter.remove();
		}
	}
	public void checkSound()
	{
		if(isSound() && !isPaused())
			bg.play();
		else
			bg.pause();
	}
	public void playSound()
	{
		if (!isPaused())
		bg.play();
	}
	public void pauseSound()
	{
		bg.pause();
	}
	
	public GameWorldCollection returnWorld()
	{
		return gameObj;
	}

	public void setFPS(int fps) 
	{
		this.fps = fps;
		
	}
}