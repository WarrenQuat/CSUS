package com.mycompany.a4.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class AddAsteroidCommand extends Command
{
	private GameWorld gw;
	
	public AddAsteroidCommand(GameWorld gw)
	{
		super("Add an Asteroid");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.addAsteroid();
	}

}