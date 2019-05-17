package com.mycompany.a3.commands;


import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command
{
	public AboutCommand()
	{
		super("About");
	}
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			Dialog.show("About","Asteroids game v3.0 By: Warren Quattrocchi CSC 133 Sacramento State", "OK", null);
	}
}
