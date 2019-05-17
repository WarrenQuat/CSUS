package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.interfaces.IIterator;
import com.mycompany.a3.objects.GameObject;
import com.mycompany.a3.objects.GameWorldCollection;
import com.mycompany.a3.objects.Missile;

public class RefuelCommand extends Command
{
	private GameWorld gw;
	
	public RefuelCommand(GameWorld gw)
	{
		super("Refuel missiles");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getKeyEvent() != -1)
			if(gw.isPaused())
			{
				GameWorldCollection gc = gw.returnWorld();
				IIterator iter = gc.getIterator();
				while(iter.hasNext())
				{
					GameObject go = (GameObject)iter.getNext();
					if(go instanceof Missile && ((Missile)go).isSelected())
						((Missile)go).refuel();
				}
			}
	}

}