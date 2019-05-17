package com.mycompany.a2.commands;
import com.mycompany.a2.*;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RefillCommand extends Command
{
	private GameWorld gw;
	
	public RefillCommand(GameWorld gw)
	{
		super("Refill Ammo");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			gw.refillMissiles();
	}

}
