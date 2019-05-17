package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class PSShotCommand extends Command
{
	private GameWorld gw;
	public PSShotCommand(GameWorld gw)
	{
		super("PS shot by NPS");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.PSShot();
	}
}