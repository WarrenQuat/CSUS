package com.mycompany.a3.interfaces;

import com.codename1.ui.geom.Point2D;

public interface ISelectable 
{
	public void setSelected (boolean yesNo);
	
	public boolean isSelected();
	
	public boolean contains(Point2D pPtrRelPrnt, Point2D pCmpRelPrnt);
	
}
