package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AddPSCommand extends Command
{
	private GameWorld gw;
	
	public AddPSCommand(GameWorld gw)
	{
		super("Add Player Ship");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.addPlayerShip();
	}

}
