package com.mycompany.a4.commands;


import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class TickCommand extends Command
{
	private GameWorld gw;
	public TickCommand(GameWorld gw)
	{
		super("Update World");
		this.gw = gw;
		
	}
}
