package com.mycompany.a4.interfaces;

public interface ICollider 
{
	public boolean collidesWith(ICollider otherObject);
	
	public void handleCollision(ICollider otherObject);
}
