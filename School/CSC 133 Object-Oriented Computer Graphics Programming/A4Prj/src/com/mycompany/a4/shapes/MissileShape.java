package com.mycompany.a4.shapes;



import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;

public class MissileShape 
{
	private Transform myTranslation, myRotation, myScale ;
	private MissileFin leftFin, rightFin;
	private Triangle top;
	private MissileBody body;
	private int finEffect, color;
	private boolean selected;
	
	public MissileShape(int color)
	{
		myTranslation = Transform.makeIdentity();
		myRotation = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		leftFin = new MissileFin(20,20,true);
		rightFin = new MissileFin(20,20,false);
		body = new MissileBody(20, 40,color);
		leftFin.translate(-15,-10);
		rightFin.translate(15,-10);
		finEffect = 1;
		this.color = color;
		selected  = false;
	}
	
	public void rotate(float degrees)
	{
		myRotation.rotate ((float)Math.toRadians(degrees),0,0);
	}
	
	public void translate(float tx, float ty)
	{
		myTranslation = Transform.makeTranslation(tx, ty);
	}
	public void scale(float sx, float sy)
	{
		myScale = Transform.makeScale(sx, sy);
	}
	public void draw (Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) 
	{
		Transform gXform = Transform.makeIdentity(); 
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); 
		gXform.translate((float)pCmpRelScrn.getX(),(float)pCmpRelScrn.getY());
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate((float)-pCmpRelScrn.getX(),(float)-pCmpRelScrn.getY()); 
		g.setTransform(gXform);
		leftFin.translate(-finEffect,0);
		rightFin.translate(finEffect, 0);
		if(!selected)
			g.setColor(color);
		else
			g.setColor(ColorUtil.WHITE);
		leftFin.draw(g, pCmpRelPrnt, pCmpRelScrn);
		rightFin.draw(g, pCmpRelPrnt, pCmpRelScrn);
		body.draw(g, pCmpRelPrnt, pCmpRelScrn);
		g.setTransform(gOrigXform);
		leftFin.translate(finEffect,0);
		rightFin.translate(-finEffect, 0);
		finEffect+=5;
		if(finEffect >5)
			finEffect = 0;
	}
	
	public void setSelected(boolean yesNo)
	{
		selected = yesNo;
	}
	public void resetTransform()
	{
		myRotation.setIdentity();
		myTranslation.setIdentity();
		myScale.setIdentity();
	}
}
