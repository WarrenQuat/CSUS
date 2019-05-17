/*
 * Warren Quattrocchi
 * CSC 133
 * GameWorldCollection Class
 * 	Inner class GameWorldIterator
 */

package com.mycompany.a2.objects;
import java.util.ArrayList;

import com.mycompany.a2.interfaces.ICollection;
import com.mycompany.a2.interfaces.IIterator;

public class GameWorldCollection implements ICollection
{
	
	private ArrayList<GameObject> gameObj;
	
	//initialize a new arraylist of game objects
	public GameWorldCollection()
	{
		gameObj = new ArrayList<GameObject>();
	}
	
	//add an object to the list
	public void add(Object o)
	{
		gameObj.add((GameObject) o);
	}

	//return the lists iterator
	public IIterator getIterator()
	{
		return new GameWorldIterator();
	}
	
	//iterator inner class
	private class GameWorldIterator implements IIterator
	{
		private int index;
		
		//initialize index at -1
		public GameWorldIterator()
		{
			index = -1;	
		}
		//return true while arraylist has more elements
		public boolean hasNext()
		{
			if(gameObj.size() <= 0)
				return false;
			if(index == gameObj.size() - 1)
				return false;	
			return true;
		}

		//return next object in the list
		//and increment index
		public Object getNext() 
		{
			index++;
			return(gameObj.get(index));
		}
		
		//remove item at curent index
		//decrement index
		public void remove()
		{
			gameObj.remove(index);
			index--;
		}			
	}
}
