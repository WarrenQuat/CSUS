package com.mycompany.a2.commands;


import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class AsteroidShotCommand extends Command
{
	private GameWorld gw;
	public AsteroidShotCommand(GameWorld gw)
	{
		super("Asteroid Shot by PS");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.asteroidShot();
	}
}