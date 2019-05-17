package com.mycompany.a2.commands;


import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class AstCollisionCommand extends Command
{
	private GameWorld gw;
	public AstCollisionCommand(GameWorld gw)
	{
		super("Collide 2 asteroids");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.asteroidCol();
	}
}
