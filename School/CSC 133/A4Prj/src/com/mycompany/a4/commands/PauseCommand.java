package com.mycompany.a4.commands;


import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.Game;
import com.mycompany.a4.GameWorld;

public class PauseCommand extends Command
{
	private Game g;
	public PauseCommand(Game g)
	{
		super("Pause Game");
		this.g = g;
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			if(g.isPaused())
			{
			 g.resume();
			}
			else
			{
			 g.pause();
			}
	}
}
