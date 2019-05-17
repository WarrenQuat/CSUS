package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class CrashNPSCommand extends Command
{
	private GameWorld gw;
	public CrashNPSCommand(GameWorld gw)
	{
		super("Collide NPS and PS");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.crashNPS();
	}
}
