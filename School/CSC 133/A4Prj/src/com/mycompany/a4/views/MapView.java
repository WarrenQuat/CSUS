/*
 * Warren Quattrocchi
 * CSC 133
 * MapView Class
 */

package com.mycompany.a4.views;
import java.util.*;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.Transform.NotInvertibleException;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.plaf.Border;
import com.mycompany.a4.interfaces.*;
import com.mycompany.a4.objects.GameObject;
import com.mycompany.a4.objects.GameWorldCollection;

/*
 * mapview keeps track of what is occuring in gameworld through 
 * the gameworld proxy 
 */
public class MapView extends Container implements Observer
{
	private Point2D loc,pPrevDragLoc;
	private IGameWorld gw;
	private float winLeft, winRight, winBottom, winTop,winWidth, winHeight;
	Transform worldToND, ndToDisplay, theVTM, inverseVTM;
	
	//style the container
	public MapView()
	{
		//this.gw = gw;
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		this.getStyle().setBgColor(0x141414);
		this.getStyle().setBgTransparency(255);
	    pPrevDragLoc = new Point2D(-1, -1);
			
	}
	
	public void initializeSize()
	{
		winLeft = 0;
		winBottom = 0;
		winRight = this.getWidth()/2;
		winTop = this.getHeight()/2;
		winWidth = winRight - winLeft;
		winHeight = winTop - winBottom;	
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		//construct transforms
		worldToND = buildWorldToNDXform(winRight- winLeft, winTop-winBottom, winLeft, winBottom);
		ndToDisplay = buildNDToDisplayXform(this.getWidth(), this.getHeight());
		theVTM =  ndToDisplay.copy();
		theVTM.concatenate(worldToND);
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.translate(getAbsoluteX(),getAbsoluteY());
		gXform.concatenate(theVTM);
		gXform.translate(-getAbsoluteX(),-getAbsoluteY());
		g.setTransform(gXform);
		
		loc = new Point2D(getX(),getY());	
		Point2D pCmpRelScrn = new Point2D(getAbsoluteX(),getAbsoluteY());
		GameWorldCollection gc = gw.returnWorld();
		IIterator iter = gc.getIterator();
		while(iter.hasNext())
		{
			GameObject go = (GameObject)iter.getNext();
			if(go instanceof IDrawable)
				((IDrawable) go).draw(g, loc,pCmpRelScrn);
		}	
		g.resetAffine();
	}
	
	public void pointerPressed(int x, int y)
	{
		if(gw.isPaused())
		{
			inverseVTM = Transform.makeIdentity();
			try {
				theVTM.getInverse(inverseVTM);
			} catch (NotInvertibleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			float [] fPtr = new float [] {x - getParent().getAbsoluteX(), y - getParent().getAbsoluteY()};
			inverseVTM.transformPoint(fPtr, fPtr);
			
			Point2D pPtrRelPrnt = new Point2D(fPtr[0],fPtr[1] + 30);
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
			repaint();
		}
	}
	@Override
	public void pointerDragged(int x, int y)
	{
		if (pPrevDragLoc.getX() != -1)
		{
			if (pPrevDragLoc.getX() < x)
				panHorizontal(5); 
		    else if (pPrevDragLoc.getX() > x)
		    	panHorizontal(-5);
		    if (pPrevDragLoc.getY() < y)	
		    	panVertical(-5);
		    else if (pPrevDragLoc.getY() > y)
		    	panVertical(5);
		}
		pPrevDragLoc.setX(x);
		pPrevDragLoc.setY(y);
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
	
	private Transform buildWorldToNDXform(float winWidth, float winHeight, float winLeft, float winBottom)
	{
		Transform  tmpXfrom = Transform.makeIdentity();
		tmpXfrom.scale( (1/winWidth) , (1/winHeight) );
		tmpXfrom.translate(-winLeft,-winBottom);
		return tmpXfrom;
	}
	
	private Transform buildNDToDisplayXform (float displayWidth, float displayHeight)
	{
		Transform tmpXfrom = Transform.makeIdentity();
		tmpXfrom.translate(0, displayHeight);
		tmpXfrom.scale(displayWidth/2, -displayHeight/2);
		return tmpXfrom;
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
	public void zoom(float factor) {
		//positive factor would zoom in (make the worldWin smaller), suggested value is 0.05f
		//negative factor would zoom out (make the worldWin larger), suggested value is -0.05f
		//...[calculate winWidth and winHeight]
		float newWinLeft = winLeft + winWidth*factor;
		float newWinRight = winRight - winWidth*factor;
		float newWinTop = winTop - winHeight*factor;
		float newWinBottom = winBottom + winHeight*factor;
		float newWinHeight = newWinTop - newWinBottom;
		float newWinWidth = newWinRight - newWinLeft;
		//in CN1 do not use world window dimensions greater loat newWinHeight = newWinTop - newWinBottom; than 1000!!!
		if (newWinWidth <= (float) 1000 && newWinHeight <= (float) 1000 && newWinWidth > (float) 0 && newWinHeight > (float) 0 ){
			winLeft = newWinLeft;
			winRight = newWinRight;
			winTop = newWinTop;
			winBottom = newWinBottom;
			winWidth = newWinWidth;
			winHeight = newWinHeight;
		}
		else
			System.out.println("Cannot zoom further!");
		this.repaint();

	}
	public void panHorizontal(double delta) {
		//positive delta would pan right (image would shift left), suggested value is 5  
		//negative delta would pan left (image would shift right), suggested value is -5  
		winLeft += delta;
		winRight += delta;
		this.repaint();
	}
	public void panVertical(double delta) {
		//positive delta would pan up (image would shift down), suggested value is 5  
		//negative delta would pan down (image would shift up), suggested value is -5  
		winBottom += delta;
		winTop += delta;
		this.repaint();
	}
	
	/* Override pinch() in CustomContainer to allow zooming with pinching*/
	@Override
	public boolean pinch(float scale){
		if(scale < 1.0){
			//Zooming Out: two fingers come closer together (on actual device), right mouse 	
			//click + drag towards the top left corner of screen (on simulator)
			zoom(-0.005f);
		}else if(scale>1.0){
			//Zooming In: two fingers go away from each other (on actual device), right mouse 			
			//click + 	drag away from the top left corner of screen (on simulator)
			zoom(0.005f);
		}
		return true;
	} 
}

