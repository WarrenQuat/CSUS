package com.mycompany.a4.commands;


import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class HyperspaceCommand extends Command
{
	private GameWorld gw;
	public HyperspaceCommand(GameWorld gw)
	{
		super("Hyperspace");
		this.gw = gw;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.hyperSpace();
	}
}
