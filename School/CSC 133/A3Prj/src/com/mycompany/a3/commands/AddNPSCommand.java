package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AddNPSCommand extends Command
{
	private GameWorld gw;
	
	public AddNPSCommand(GameWorld gw)
	{
		super("Add a Non-Player Ship");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.addNPS();
	}

}