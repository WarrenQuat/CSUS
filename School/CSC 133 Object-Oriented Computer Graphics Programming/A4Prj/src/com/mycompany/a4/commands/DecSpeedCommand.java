package com.mycompany.a4.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class DecSpeedCommand extends Command
{
	private GameWorld gw;
	public DecSpeedCommand(GameWorld gw)
	{
		super("decrement speed");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.decSpeed();
	}
}
