package com.mycompany.a4.shapes;



import com.codename1.ui.geom.Point2D;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Rectangle 
{
	private Point2D topLeft,topRight, bottomLeft, bottomRight ;
	private int myColor ;
	private boolean selected;
	private Transform myRotation, myTranslation, myScale ;
	public Rectangle  (int base, int height,int color) {
		topLeft = new Point2D (-base/2, height/2);
		topRight = new Point2D (base/2, height/2);
		bottomLeft = new Point2D (-base/2, -height/2);
		bottomRight = new Point2D (base/2, -height/2);
		myColor = color;
		myRotation = Transform.makeIdentity();
		myTranslation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		selected = false;
	}
	public void rotate (float degrees) {
		
		myRotation.rotate ((float)Math.toRadians(degrees),0,0);
	}
	public void translate (float tx, float ty) {
		myTranslation.translate (tx, ty);
	}
	public void scale (float sx, float sy) {
		
		myScale.scale (sx, sy);
	}
	public void draw (Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) {
		  // set the drawing color for the triangle
		if(!selected)
			g.setColor(myColor);
		else
			g.setColor(ColorUtil.WHITE);
		 	
			Transform gXform = Transform.makeIdentity(); 
			g.getTransform(gXform);
			Transform gOrigXform = gXform.copy(); 
			gXform.translate((float)pCmpRelScrn.getX(),(float)pCmpRelScrn.getY());
			gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
			gXform.concatenate(myRotation);
			gXform.scale(myScale.getScaleX(), myScale.getScaleY());
			gXform.translate((float)-pCmpRelScrn.getX(),(float)-pCmpRelScrn.getY()); 
			g.setTransform(gXform);
			//draw the lines as before
			
			g.drawLine ((int)(pCmpRelPrnt.getX()+ topLeft.getX()), (int)(pCmpRelPrnt.getY()+topLeft.getY()),
					    (int)(pCmpRelPrnt.getX()+bottomLeft.getX()),(int)(pCmpRelPrnt.getY()+bottomLeft.getY()));  
			g.drawLine ((int)(pCmpRelPrnt.getX()+bottomLeft.getX()), (int)(pCmpRelPrnt.getY()+bottomLeft.getY()), 
					    (int)(pCmpRelPrnt.getX()+bottomRight.getX()), (int)(pCmpRelPrnt.getY()+bottomRight.getY()));
			g.drawLine ((int)(pCmpRelPrnt.getX()+bottomRight.getX()),(int)(pCmpRelPrnt.getY()+bottomRight.getY()),
					    (int)(pCmpRelPrnt.getX()+topRight.getX()), (int)(pCmpRelPrnt.getY()+topRight.getY()));
			g.drawLine ((int)(pCmpRelPrnt.getX()+topRight.getX()),(int)(pCmpRelPrnt.getY()+topRight.getY()),
				    	(int)(pCmpRelPrnt.getX()+topLeft.getX()), (int)(pCmpRelPrnt.getY()+topLeft.getY()));
			g.setTransform(gOrigXform); 
		}
	public void resetTransform()
	{
		myRotation.setIdentity();
		myTranslation.setIdentity();
		myScale.setIdentity();
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
		
	}
}