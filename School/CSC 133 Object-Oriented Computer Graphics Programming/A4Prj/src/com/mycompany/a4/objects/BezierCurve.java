package com.mycompany.a4.objects;

import java.util.Random;
import com.codename1.util.MathUtil;
import com.mycompany.a4.interfaces.ICollider;
import com.mycompany.a4.interfaces.IDrawable;
import com.mycompany.a4.interfaces.Imovable;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;

public class BezierCurve extends MovableObject implements Imovable, IDrawable,ICollider {
private Point2D[] v;
private Graphics g;
private Transform myRotation, myTranslation, myScale ;
private Random r;
private int fuel;

public BezierCurve(int color,int height, int width)
{

	setHeight(height);
	setWidth(width);
	v = new Point2D[4];
	setColor(color);
	fuel = 30;
	r = new Random();
	myRotation = Transform.makeIdentity();
	myTranslation = Transform.makeIdentity();
	myScale = Transform.makeIdentity();

	//initialize 4 random control points
	//starting with (0,0) and ending with (0,height)
		v[0] = new Point2D(0,0);
		v[1] = new Point2D(-(r.nextInt(150) + 1),r.nextInt(75) + 5);
		v[2] = new Point2D(r.nextInt(150) + 1,r.nextInt(1) + 75);
		v[3] = new Point2D(0,r.nextInt(75) + 75);	
		setSize((int)(v[2].getX() - v[1].getX()));
}
	void drawBezierCurve(Point2D[] v,Graphics g, Point2D pCmpRelPrnt)
	{
		Point2D[] leftVector  = new Point2D[4];
		Point2D[] rightVector = new Point2D[4];
			if(straightEnough(v))
			{
			   g.drawLine((int)(v[0].getX() + pCmpRelPrnt.getX()),(int) (v[0].getY() + pCmpRelPrnt.getY()),
					      (int) (v[3].getX()+ pCmpRelPrnt.getX()), (int)(v[3].getY()+ pCmpRelPrnt.getY()));
			}
			else{
				subDivideCurve(v, leftVector, rightVector);
				drawBezierCurve(leftVector,g, pCmpRelPrnt);
				drawBezierCurve(rightVector,g, pCmpRelPrnt);
			}
	}
	public void drawControlPoints(Point2D[] v, Graphics g, Point2D pCmpRelPrnt)
	{
		g.setColor(ColorUtil.WHITE);
		for(int i = 0; i <v.length - 1; i++)
			g.drawLine((int)(v[i].getX() + pCmpRelPrnt.getX()),(int)( v[i].getY()+ pCmpRelPrnt.getY()), 
						(int)(v[i+1].getX()+ pCmpRelPrnt.getX()),(int) (v[i+1].getY()+ pCmpRelPrnt.getY()));
	}
	private void subDivideCurve(Point2D[] q, Point2D[] r, Point2D[] s) 
	{
		r[0] = q[0];
		
		double x = ((q[1].getX() + q[0].getX())/2.0); 
		double y = ((q[1].getY() + q[0].getY())/2.0); 	
		r[1] = new Point2D(x,y);
		
		x = r[1].getX()/2;
		y = r[1].getY()/2;
		x = x + (q[1].getX() + q[2].getX())/4.0;
		y = y + (q[1].getY() + q[2].getY())/4.0;
		r[2] = new Point2D(x,y);
		
		s[3] = q[3];
		
		x = (q[2].getX() + q[3].getX())/2.0;
		y = (q[2].getY() + q[3].getY())/2.0;
		s[2] = new Point2D(x,y);
		
		x = (q[1].getX() + q[2].getX())/4.0 + s[2].getX()/2.0;
		y = (q[1].getY() + q[2].getY())/4.0 + s[2].getY()/2.0;
		s[1] = new Point2D(x,y);
		
		x = (r[2].getX() + s[1].getX())/2.0;
		y = (r[2].getY() + s[1].getY())/2.0;
		r[3] = new Point2D(x,y);
		
		s[0] = r[3];
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
	
	public int getFuel()
	{
		return fuel;
	}
	
	boolean straightEnough(Point2D[] t)
	{
		double d1 = lengthOf(t[0], t[1])
				  + lengthOf(t[1], t[2])
				  + lengthOf(t[2], t[3]);
		double d2 = lengthOf(t[0], t[3]);
		
		if(Math.abs(d1-d2) < .001f)
			return true;
		else
			return false;
	}
    private Double lengthOf(Point2D p1, Point2D p2)
    {
    	double length = Math.sqrt((( p2.getX() - p1.getX()) * (p2.getX() - p1.getX())) + 
    							(( p2.getY() - p1.getY()) * ( p2.getY() - p1.getY())));

        return length;
    }

	@Override
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) 
	{
		this.translate((float)getLocation().getX(),(float)getLocation().getY());
		this.rotate(-getDirection());
		
		Transform gXform = Transform.makeIdentity(); 
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); 
		gXform.translate((float)pCmpRelScrn.getX(),(float)pCmpRelScrn.getY());
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float)-pCmpRelScrn.getX(),(float)-pCmpRelScrn.getY()); 
		g.setTransform(gXform);
		drawControlPoints(v, g, pCmpRelPrnt);
		g.setColor(getColor());
		drawBezierCurve(v, g,pCmpRelPrnt);		
		g.setTransform(gOrigXform);
		resetTransform();
	}
	
	public void move(int fps)
	{
		super.move(fps);
		fuel--;
	}
	@Override
	public boolean collidesWith(ICollider otherObject) {
		boolean result = false;	
		int thisCenterX = (int)this.getLocation().getX();
		int thisCenterY = (int)this.getLocation().getY();
		int otherCenterX = (int)((GameObject)otherObject).getLocation().getX();
		int otherCenterY = (int)((GameObject)otherObject).getLocation().getY();
		int dx = thisCenterX - otherCenterX;
		int dy = thisCenterY - otherCenterY;
		int disBetweenCentersSqr = (dx*dx + dy*dy);
		int thisRadius = this.getSize()/2;
		int otherRadius = ((GameObject)otherObject).getSize()/2;	
		int radiiSqr = (thisRadius * thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);
		if(disBetweenCentersSqr <= radiiSqr)
			result =true;
		return result;	
	}
	@Override
	public void handleCollision(ICollider otherObject) {
		// TODO Auto-generated method stub
		
	}
	public void resetTransform()
	{
		myRotation.setIdentity();
		myTranslation.setIdentity();
		myScale.setIdentity();
	}	
}