/*
 * Warren Quattrocchi
 * CSC 133
 * GameWorld Class
 */

package com.mycompany.a1;
import java.util.*;
import com.mycompany.a1.interfaces.Imovable;
import com.mycompany.a1.objects.*;

public class GameWorld {
	private ArrayList<GameObject>[] gameObj;
	private int lives;
	private int score;
	private int gameTime;
	
	@SuppressWarnings("unchecked")
	public void init()
	{
		//initialize score, lives, and game time on creation of a new game
		score = 0;
		lives = 3;
		gameTime = 0;

			/*initialize an array of array lists
			for storing game objects currently in world			
			Asteroids:                index 0
			Player ship:              index 1
			Non player ship:          index 2
			enemy missile:            index 3
			friendly missile:         index 4
			Space station:            index 5
			*/		
			gameObj = new ArrayList[6];
			for(int i = 0; i<gameObj.length; i++)
			{
				gameObj[i] = new ArrayList<GameObject>();
			}
	}	
	//Add a new Asteroid into the game world
	public void addAsteroid()     
	{
		gameObj[0].add(new Asteroid());
		System.out.println("Asteroid added");
	}
		//add a new PlayerShip into the world
	public void addPlayerShip()
	{
		//if a PlayerShip is already spawned, a new one will not be added
		if (gameObj[1].size() == 0)
		{
			gameObj[1].add(new PlayerShip());
			System.out.println("PlayerShip added");
		}else{
			System.out.println("A player ship is already spawned");
		}
		
	}
	//Add a NonPlayerShip into the game world
	public void addNPS()
	{
		gameObj[2].add(new NonPlayerShip());
		System.out.println("Non-Player ship added");
	}	
	//Add a space station into the game world
	public void addSpaceStation()
	{
		//if a space station is already spawned, one will not be added
		if (gameObj[5].size() == 0)
		{
			gameObj[5].add(new SpaceStation());
			System.out.println("SpaceStation added");
		}else{
			System.out.println("A space station is already spawned");
		}
	}	
	//refill missiles
	public void refillMissiles()
	{
		//missiles cannot be refilled if no PlayerShip is spawned
		if(gameObj[1].size() > 0 && gameObj[5].size() > 0)
		{
			if(gameObj[1].get(0) instanceof PlayerShip)
			{
				if(((SpaceStation)gameObj[5].get(0)).getVisibility())
				{
					((PlayerShip) gameObj[1].get(0)).reload();
					System.out.println("Missiles replenished");
				}else
					System.out.println("The spacestation is currently off");
			}
		}else{
			System.out.println("there is not currently a player ship or a Space Station spawned");
		}
	}	
	/*
	 * The world will be updated
	 * All GameObjects will invoke their move method
	 * Game time will be incremented by 1
	 */
	public void update()
	{
		gameTime++;
		for(int i = 0; i<gameObj.length; i++)
		{
			for(int j = 0; j < gameObj[i].size(); j++)
			{
				/*If the object implements movable
				 * call the move method in each object
				 */
				if(gameObj[i].get(j) instanceof Imovable)
					((MovableObject)gameObj[i].get(j)).move();
				/*call CheckBlink() method in SpaceStation
				 * if the time % blinkRate == 0
				 * the visibility with be switched
				 */
				if(gameObj[i].get(j) instanceof SpaceStation)
					((SpaceStation)gameObj[i].get(j)).checkBlink(gameTime);
				/*check if missiles are out of fuel
				and remove if fuel = 0
				*/
				if(gameObj[i].get(j) instanceof Missile)
					if(((Missile)gameObj[i].get(j)).getFuel() <= 0)
						gameObj[i].remove(j);
			}
		}		
		System.out.println("World updated");
	}	
	//rotate the ship left
	public void rotLeft()
	{
		//only rotate if a PlayerShip is currently spawned
		if(gameObj[1].size() > 0)
		{
			((PlayerShip)gameObj[1].get(0)).moveLeft();
			System.out.println("Heading +10 degrees");
		}else {
			System.out.println("there is not currently a player ship spawned");
		}
	}	
	//rotate the ship right
	public void rotRight()
	{
		//only rotate if a PlayerShip is currently spawned
		if(gameObj[1].size() > 0)
		{
			((PlayerShip)gameObj[1].get(0)).moveRight();
			System.out.println("Heading -10 degrees");
		}else {
			System.out.println("there is not currently a player ship spawned");
		}
	}	
	//increase the speed of the PlayerShip by 10
	public void incSpeed()
	{
		//only increase if a PlayerShip is currently spawned
		if(gameObj[1].size() > 0)
		{
			((PlayerShip)gameObj[1].get(0)).incSpeed();
			System.out.println("Speed +10");
		}else
			System.out.println("A player ship is not currently spawned");
	}	
	//Decrease the speed of the PlayerShip by 10
	public void decSpeed()
	{
		//only decrease if a PlayerShip is currently spawned
		if(gameObj[1].size() > 0)
		{
			((PlayerShip)gameObj[1].get(0)).decSpeed();
			System.out.println("Speed -10");
		}else
			System.out.println("A player ship is not currently spawned");
	}	
	//rotate the ships launcher by 10 degrees
	public void rotLauncher()
	{
		//only rotate if a PlayerShip, and therefore a MissileLauncher is spawned
		if(gameObj[1].size() > 0)
		{
			((PlayerShip)gameObj[1].get(0)).moveLauncher();
			System.out.println("Launcher rotated 10 degrees");
		}else
			System.out.println("A player ship is not currently spawned");
	}	
	//fire a Missile from the PlayerShip
	public void firePS()
	{
		if(gameObj[1].size() > 0 && ((PlayerShip)gameObj[1].get(0)).getMissileCount() > 0)
		{
			/*Create a new Missile
			 * and set its location equal 
			 * to the PlayerShip and speed greater than
			 * that of the PlayerShip
			 */
			Missile mis = new Missile(false);
			mis.setDirection(((PlayerShip)gameObj[1].get(0)).getLauncher().getDirection());
			mis.setLocation(gameObj[1].get(0).getLocation());
			mis.setSpeed(((PlayerShip)gameObj[1].get(0)).getSpeed() + 4);	
			//call fireMissile method from PlayerShip to decrement missile count
			((PlayerShip)gameObj[1].get(0)).fireMissile();	
			//add Missile to the game world
			gameObj[4].add(mis);
			System.out.println("PS has fired a misile");	
		}else
			System.out.println("A player ship is not currently spawned or no missiles are left");
	}	
	//Fire a Missile from a NonPlayerShip
	public void fireNPS()
	{
		//Only fire if a NonPlayerShip is spawned
		if(gameObj[2].size() > 0)
		{
			/*Create a new Missile
			 * and set its location equal 
			 * to a randomly chosen NonPlayerShip
			 * and speed greater than the NPS
			 */
			
			int r = new Random().nextInt(gameObj[2].size());
			if(((NonPlayerShip)gameObj[2].get(r)).getMissileCount() > 0)
			{
				Missile mis = new Missile(true);
				mis.setDirection(((NonPlayerShip)gameObj[2].get(r)).getDirection());
				mis.setLocation(gameObj[2].get(r).getLocation());
				mis.setSpeed(((NonPlayerShip)gameObj[2].get(r)).getSpeed() + 4);
				//add Missile to the game world
				gameObj[3].add(mis);
				System.out.println("NPS has fired a misile");
				((NonPlayerShip)gameObj[2].get(r)).launch();
			}else
				System.out.println("the chosen NPS is out of missiles");
		}else 
			System.out.println("A NPS is not currently spawned");		
	}
	//Returns the ship to it's original position
	//at the center of the screen
	public void hyperSpace()
	{
		//only return the ship if a PlayerShip is currently spawned
		if(gameObj[1].size() > 0)
		{
			//invoke resetShip() method from PlayerShip
			((PlayerShip)gameObj[1].get(0)).resetShip();
			System.out.println("PS location reset");
		}
		else
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
		if(gameObj[0].size() > 0 && gameObj[4].size() > 0)
		{
			int ast = new Random().nextInt(gameObj[0].size());
			int mis = new Random().nextInt(gameObj[4].size());
			gameObj[0].remove(ast);
			gameObj[4].remove(mis);
			score += 100;
			System.out.println("A friendly missile has struck an asteroid! +100 points");
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
		if(gameObj[2].size() > 0 && gameObj[4].size() > 0)
		{
			int nps = new Random().nextInt(gameObj[2].size());
			int mis = new Random().nextInt(gameObj[4].size());
			gameObj[2].remove(nps);
			gameObj[4].remove(mis);
			score += 500;
			System.out.println("A player misile has struck a NPS! +500 points");
		}else
			System.out.println("An NPS and a friendly missile are not spawned");
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
		if(gameObj[1].size() > 0 && gameObj[3].size() > 0)
		{
			int mis = new Random().nextInt(gameObj[3].size());
			gameObj[1].remove(0);
			gameObj[3].remove(mis);
			lives--;
			System.out.println("An enemy missile has struck a PS -1 life");
			if(lives >0) 
				System.out.println("you have " + lives + " left");
			else
			{
				System.out.println("Game over");
				gameOver();
			}
		}else
			System.out.println("A player ship and an enemy missile are not spawned");		
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
		if(gameObj[1].size() > 0 && gameObj[0].size() > 0)
		{
			int ast = new Random().nextInt(gameObj[0].size());
			gameObj[0].remove(ast);
			gameObj[1].remove(0);
			lives--;
			System.out.println("An asteroid has struck the players ship -1 life");
			if(lives >0) 
				System.out.println("you have " + lives + " lives left");
			else
			{
				System.out.println("Game over");
				gameOver();
			}
		}else
			System.out.println("A player ship and an asteroid are not spawned");

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
		if(gameObj[2].size() > 0 && gameObj[1].size() > 0)
		{
			int nps = new Random().nextInt(gameObj[2].size());
			gameObj[2].remove(nps);
			gameObj[1].remove(0);
			lives--;
			System.out.println("The player has crashed into a NPS -1 life");
			if(lives >0) 
				System.out.println("you have " + lives + " lives left");
			else
			{
				System.out.println("Game over");
				gameOver();
			}
		}else
			System.out.println("A non player ship and a player ship are not spawned");
	}
	
	/*2 asteroids have collided
	 * removes 2 random asteroids from the game world
	 */
	public void asteroidCol()
	{
		//collision can only occur if at least 2 asteroids are spawned
		if(gameObj[0].size() >=2)
		{
			int ast = new Random().nextInt(gameObj[0].size());
			gameObj[0].remove(ast);
			ast = new Random().nextInt(gameObj[0].size());
			gameObj[0].remove(ast);
			System.out.println("2 asteroids have collided and been removed from the world");
		}else
			System.out.println("2 asteroids are not spawned");
	}	
	/*An asteroid has hit a NonPlayerShip
	 * removes 2 random NPS's from the game world
	 */
	public void crashAsteroidNPS()
	{
		//collision can only occur if at least 1 asteroid and
		//one NPS are spawned
		if(gameObj[2].size() > 0 && gameObj[0].size() > 0)
		{
			int ast = new Random().nextInt(gameObj[0].size());
			gameObj[0].remove(ast);
			int nps = new Random().nextInt(gameObj[2].size());
			gameObj[2].remove(nps);
			System.out.println("An asteroid and a NPS have collided and been removed from the game");
		}else
			System.out.println("An asteroid and a non player ship are not spawned");
	}	
	/*Runs through the collection of GameObjects
	 * prints out the ToString() method of every object
	 * and displays it on screen
	 */
	public void printWorld()
	{
		System.out.println("***********************World Info*************************");
		for(int i = 0; i<gameObj.length; i++)
		{
			for(int j = 0; j < gameObj[i].size(); j++)
				System.out.println(gameObj[i].get(j).toString());
		}
		System.out.println("***********************World Info*************************");
	}	
	/*displays Information on the current players game
	 *including lives, score, missiles left, and score
	 */
	public void print()
	{
		if(gameObj[1].size() > 0)
		{
			System.out.println("*********************Player Info*******************************");
			System.out.println("[Lives: " + lives + "][Score: " + score + 
							"][Time: " + gameTime + "]");
			System.out.println("*********************Player Info*******************************");
		}else
			System.out.println("Spawn a player ship to view game info");
	}
	
	public void gameOver()
	{
		init();
	}
}
