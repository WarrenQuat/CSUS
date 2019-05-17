package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class QuitCommand extends Command
{

	public QuitCommand()
	{
		super("Quit Game");
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			if(Dialog.show("Quit", "Are you sure you would like to quit?","Yes", "No")) 
			{
				System.exit(0);
			}
	}
}
