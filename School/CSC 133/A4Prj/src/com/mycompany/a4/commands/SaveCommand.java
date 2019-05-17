package com.mycompany.a4.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class SaveCommand extends Command
{
	private GameWorld gw;
	public SaveCommand(GameWorld gw)
	{
		super("Save Game");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			System.out.println("Save button pressed");
	}
}
