package com.mycompany.a4.shapes;



import com.codename1.ui.geom.Point2D;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class MissileFin 
{
	private Point2D top, bottomLeft, bottomRight ;
	private Transform myRotation, myTranslation, myScale ;
	public MissileFin  (int base, int height, boolean b) {
		if(b)
		{
			top = new Point2D (0, height/2);
			bottomLeft = new Point2D (-base/2, -height/2);
			bottomRight = new Point2D (0, -height/2);
		}else {
			top = new Point2D (0, height/2);
			bottomLeft = new Point2D (0, -height/2);
			bottomRight = new Point2D (base/2, -height/2);	
		}
		myRotation = Transform.makeIdentity();
		myTranslation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
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
			
			g.drawLine ((int)(pCmpRelPrnt.getX()+ top.getX()), (int)(pCmpRelPrnt.getY()+top.getY()),
					    (int)(pCmpRelPrnt.getX()+bottomLeft.getX()),(int)(pCmpRelPrnt.getY()+bottomLeft.getY()));  
			g.drawLine ((int)(pCmpRelPrnt.getX()+bottomLeft.getX()), (int)(pCmpRelPrnt.getY()+bottomLeft.getY()), 
					    (int)(pCmpRelPrnt.getX()+bottomRight.getX()), (int)(pCmpRelPrnt.getY()+bottomRight.getY()));
			g.drawLine ((int)(pCmpRelPrnt.getX()+bottomRight.getX()),(int)(pCmpRelPrnt.getY()+bottomRight.getY()),
					    (int)(pCmpRelPrnt.getX()+top.getX()), (int)(pCmpRelPrnt.getY()+top.getY()));
			g.setTransform(gOrigXform); 
		}
	public void resetTransform()
	{
		myRotation.setIdentity();
		myTranslation.setIdentity();
		myScale.setIdentity();
	}
	 } //end of Triangle class


