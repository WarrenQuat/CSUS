/*
 * Warren Quattrocchi
 * CSC 133
 * PointsView Class
 */

package com.mycompany.a3.views;
import java.util.*;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.*;
import com.mycompany.a3.interfaces.IGameWorld;
import com.codename1.ui.Label;

public class PointsView extends Container implements Observer
{
	private GameWorld gw;
	private Label score, lives, time,sound,missile;
	private Label acScore, acLives, acTime,acSound,acMissile;
	public PointsView(GameWorld gw)
	{
		//initialize all labels and add to container
		this.gw = gw;
		this.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		this.getAllStyles().setBgColor(0X75929C);
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLACK));
		this.getAllStyles().setBgTransparency(255);
		
		score = new Label("Score: ");
		score.getAllStyles().setFgColor(ColorUtil.BLACK);
		acScore = new Label("0");
		acScore.getAllStyles().setFgColor(ColorUtil.BLACK);
		acScore.getAllStyles().setPaddingRight(5);
		
		missile = new Label("Missiles: ");
		missile.getAllStyles().setFgColor(ColorUtil.BLACK);
		acMissile = new Label("0");
		acMissile.getAllStyles().setFgColor(ColorUtil.BLACK);
		acMissile.getAllStyles().setPaddingRight(5);
		
		lives = new Label("Lives: ");
		lives.getAllStyles().setFgColor(ColorUtil.BLACK);
		acLives = new Label("0");
		acLives.getAllStyles().setFgColor(ColorUtil.BLACK);
		acLives.getAllStyles().setPaddingRight(5);
		
		time = new Label("Time: ");
		time.getAllStyles().setFgColor(ColorUtil.BLACK);
		acTime = new Label("0");
		acTime.getAllStyles().setFgColor(ColorUtil.BLACK);
		acTime.getAllStyles().setPaddingRight(5);
		
		sound = new Label("Sound: ");
		sound.getAllStyles().setFgColor(ColorUtil.BLACK);
		acSound = new Label("on");
		acSound.getAllStyles().setFgColor(ColorUtil.BLACK);
		acSound.getAllStyles().setPaddingRight(5);
	
		this.addAll(score,acScore,missile,acMissile,lives,acLives,time,acTime,sound,acSound);
		
	}
	//update all labels with methods from gameworkproxy(arg)
	public void update(Observable o, Object arg)
	{
			acScore.setText(Integer.toString(((IGameWorld)arg).getScore()));
			acLives.setText(Integer.toString(((IGameWorld)arg).getLives()));
			acTime.setText (Integer.toString(((IGameWorld)arg).getTime()));
			acMissile.setText (Integer.toString(((IGameWorld)arg).getMissileCount()));
			if(((IGameWorld)arg).isSound())
				acSound.setText("On");
			if(!((IGameWorld)arg).isSound())
				acSound.setText("Off");
			repaint();
		}
	}
