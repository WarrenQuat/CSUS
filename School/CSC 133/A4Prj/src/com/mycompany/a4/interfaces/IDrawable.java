package com.mycompany.a4.interfaces;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public interface IDrawable 
{
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn);
}
