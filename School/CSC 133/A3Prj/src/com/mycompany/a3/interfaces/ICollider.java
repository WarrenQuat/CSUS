package com.mycompany.a3.interfaces;

public interface ICollider 
{
	public boolean collidesWith(ICollider otherObject);
	
	public void handleCollision(ICollider otherObject);
}
