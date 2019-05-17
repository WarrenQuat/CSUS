package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class FireMissileCommand extends Command
{
	private GameWorld gw;
	public FireMissileCommand(GameWorld gw)
	{
		super("Fire missile");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			 gw.firePS();
	}
}
