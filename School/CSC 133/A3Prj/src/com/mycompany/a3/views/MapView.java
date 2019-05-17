/*
 * Warren Quattrocchi
 * CSC 133
 * MapView Class
 */

package com.mycompany.a3.views;
import java.util.*;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.interfaces.*;
import com.mycompany.a3.objects.GameObject;
import com.mycompany.a3.objects.GameWorldCollection;

/*
 * mapview keeps track of what is occuring in gameworld through 
 * the gameworld proxy 
 */
public class MapView extends Container implements Observer
{
	private Point2D loc;
	private IGameWorld gw;
	
	//style the container
	public MapView()
	{
		//this.gw = gw;
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		this.getStyle().setBgColor(0x141414);
		this.getStyle().setBgTransparency(255);
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		loc = new Point2D(getX(),getY());	
		GameWorldCollection gc = gw.returnWorld();
		IIterator iter = gc.getIterator();
		while(iter.hasNext())
		{
			GameObject go = (GameObject)iter.getNext();
			if(go instanceof IDrawable)
				((IDrawable) go).draw(g, loc);
		}	
	}
	
	public void pointerPressed(int x, int y)
	{
		if(gw.isPaused())
		{
			x = x - getParent().getAbsoluteX();
			y = y - getParent().getAbsoluteY();
			Point2D pPtrRelPrnt = new Point2D(x,y);
			Point2D pCmpRelPrnt = new Point2D(getX(), getY());
			GameWorldCollection gc = gw.returnWorld();
			IIterator iter = gc.getIterator();
			while(iter.hasNext())
			{
				GameObject go = (GameObject)iter.getNext();
				if(go instanceof ISelectable)
				{
					if( ((ISelectable) go).contains(pPtrRelPrnt, pCmpRelPrnt))	
					{
						((ISelectable) go).setSelected(true);
						System.out.println("selected");
					}else
						((ISelectable) go).setSelected(false);
				}
			}
		}
		repaint();
	}
	public void unselectAll()
	{
		GameWorldCollection gc = gw.returnWorld();
		IIterator iter = gc.getIterator();
		while(iter.hasNext())
		{
			GameObject go = (GameObject)iter.getNext();
			if(go instanceof ISelectable)
			{
				((ISelectable) go).setSelected(false);
			}
		}
	}
	
	//update is invoked whenever notifyobservers is called in gameworld
	public void update(Observable o, Object arg)
	{
		gw = (IGameWorld) arg;
		repaint();
		//mapview will print out each object using the gameworld copy sent to the proxy
		/*System.out.println("World height: " + ((IGameWorld)arg).getHeight() + " World width: " +((IGameWorld)arg).getWidth());
		GameWorldCollection gc = ((IGameWorld)arg).returnWorld();
		IIterator iter = gc.getIterator();
		System.out.println("*****************World info**********************");
		while(iter.hasNext())
		{
			System.out.println((GameObject) iter.getNext());
		}
		System.out.println("*****************World info**********************");
		*/
	}	
}

