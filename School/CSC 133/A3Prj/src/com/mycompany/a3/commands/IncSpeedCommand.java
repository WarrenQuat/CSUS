package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class IncSpeedCommand extends Command
{
	private GameWorld gw;
	public IncSpeedCommand(GameWorld gw)
	{
		super("increment speed");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.incSpeed();
	}
}