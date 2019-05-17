package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class NewGameCommand extends Command
{
	private GameWorld gw;
	public NewGameCommand(GameWorld gw)
	{
		super("New Game");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			System.out.println("New game button pressed");
	}
}
