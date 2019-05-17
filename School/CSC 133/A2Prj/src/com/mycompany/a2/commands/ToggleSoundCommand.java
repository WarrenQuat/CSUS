package com.mycompany.a2.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class ToggleSoundCommand extends Command
{
	private GameWorld gw;
	public ToggleSoundCommand(GameWorld gw)
	{
		super("Sound");
		this.gw = gw;
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.toggleSound();
	}

}