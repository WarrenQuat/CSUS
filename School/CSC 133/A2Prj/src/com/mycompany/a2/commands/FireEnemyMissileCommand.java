package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class FireEnemyMissileCommand extends Command
{
	private GameWorld gw;
	public FireEnemyMissileCommand(GameWorld gw)
	{
		super("Fire enemy missile");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.fireNPS();
	}
}
