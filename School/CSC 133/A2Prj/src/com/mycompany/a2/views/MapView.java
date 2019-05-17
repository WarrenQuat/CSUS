/*
 * Warren Quattrocchi
 * CSC 133
 * MapView Class
 */

package com.mycompany.a2.views;
import java.util.*;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.plaf.Border;
import com.mycompany.a2.interfaces.*;
import com.mycompany.a2.objects.GameObject;
import com.mycompany.a2.objects.GameWorldCollection;

/*
 * mapview keeps track of what is occuring in gameworld through 
 * the gameworld proxy 
 */
public class MapView extends Container implements Observer
{
	//style the container
	public MapView()
	{
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		this.getStyle().setBgColor(ColorUtil.GRAY);
		this.getStyle().setBgTransparency(255);

	}
	
	//update is invoked whenever notifyobservers is called in gameworld
	public void update(Observable o, Object arg)
	{
		//mapview will print out each object using the gameworld copy sent to the proxy
		System.out.println("World height: " + ((IGameWorld)arg).getHeight() + " World width: " +((IGameWorld)arg).getWidth());
		GameWorldCollection gc = ((IGameWorld)arg).returnWorld();
		IIterator iter = gc.getIterator();
		System.out.println("*****************World info**********************");
		while(iter.hasNext())
		{
			System.out.println((GameObject) iter.getNext());
		}
		System.out.println("*****************World info**********************");
	}
}

