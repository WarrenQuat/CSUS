/*
 * Warren Quattrocchi
 * CSC 133
 * Game Class
 */

package com.mycompany.a4;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.*;
import com.codename1.ui.util.UITimer;
import com.mycompany.a4.commands.*;
import com.mycompany.a4.objects.ModButton;
import com.mycompany.a4.views.*;
import com.codename1.ui.Toolbar;


public class Game extends Form implements Runnable
{
	private GameWorld 	gw;
	private MapView 	mv;    
	private PointsView 	pv; 
	private Container 	buttons,sideButtons;
	private UITimer		timer;
	
	private ModButton 	addPS, addAsteroid, addNPS, addSpaceStation, 
						refill,tick,astCollision,crashAsteroidPS,asteroidShot,
				   		quit,about,undo, save, newGame,crashAsteroidNPS,
				   		crashNPS,psShot,npsShot,fireEnemyMissile,pause, refuel;
	private CheckBox	sound;
	private Command		addPSCmd,addAsteroidCmd,addNPSCmd,addSpaceStationCmd,
						refillCmd,tickCmd,astCollisionCmd,crashAsteroidPSCmd,
						asteroidShotCmd,crashAsteroidNPSCmd,crashNPSCmd,psShotCmd,
						npsShotCmd,fireEnemyMissileCmd,quitCmd, aboutCmd,newgameCmd,
						addCurveCmd,saveCmd, undoCmd,soundCmd,moveLLeft,moveLRight,hyperSpaceCmd,
						turnLeftCmd, turnRightCmd, incSpeedCmd, decSpeedCmd, fireMisCmd,pauseCmd, refuelCmd;
	
	
	private Label commands,file;
	private Toolbar tb;
	private boolean paused;
	private int globalSpeed;
	
	public Game() {

		paused = false;
		gw = new GameWorld();
		mv = new MapView();
		pv = new PointsView(gw);
		buttons = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		buttons.getAllStyles().setPadding(12, 12,12,12);
		sideButtons = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		sideButtons.getAllStyles().setPadding(12, 12,12,12);
		gw.addObserver(mv);
		gw.addObserver(pv);
		tb = this.getToolbar();
		globalSpeed = 40;
		gw.setFPS(globalSpeed);
		Toolbar.setGlobalToolbar(true);
		
		
		//initialize all buttons
		addPS          		= new ModButton();
		addAsteroid    		= new ModButton();
		addNPS          	= new ModButton();
		addSpaceStation	 	= new ModButton();
		sound          	 	= new CheckBox();
		sound.setSelected(true);
		refill  			= new ModButton();
		tick 				= new ModButton();
		undo				= new ModButton();
		newGame 			= new ModButton();
		save				= new ModButton();
		about				= new ModButton();
		quit				= new ModButton();
		astCollision 		= new ModButton();
		crashAsteroidPS 	= new ModButton();
		crashAsteroidNPS	= new ModButton();
		asteroidShot    	= new ModButton();
		crashNPS 			= new ModButton();
		psShot 				= new ModButton();
		npsShot 			= new ModButton();
		fireEnemyMissile	= new ModButton();
		pause 				= new ModButton();
		refuel				= new ModButton();
		commands			= new Label("Commands");
		file 				= new Label("File");
		addPSCmd			= new AddPSCommand(gw);
		addAsteroidCmd		= new AddAsteroidCommand(gw);
		addNPSCmd			= new AddNPSCommand(gw);
		addSpaceStationCmd  = new AddSSCommand(gw);
		tickCmd				= new TickCommand(gw);
		fireEnemyMissileCmd = new FireEnemyMissileCommand(gw);
		soundCmd			= new ToggleSoundCommand(gw);
		saveCmd				= new SaveCommand(gw);
		aboutCmd			= new AboutCommand();
		quitCmd				= new QuitCommand();
		undoCmd				= new UndoCommand(gw);
		newgameCmd			= new NewGameCommand(gw);
		moveLLeft			= new RotLauncherLeftCommand(gw);
		moveLRight			= new RotLauncherRightCommand(gw);
		hyperSpaceCmd		= new HyperspaceCommand(gw);
		turnLeftCmd			= new TurnLeftCommand(gw);
		turnRightCmd		= new TurnRightCommand(gw);
		incSpeedCmd			= new IncSpeedCommand(gw);
		decSpeedCmd			= new DecSpeedCommand(gw);
		fireMisCmd			= new FireMissileCommand(gw);
		pauseCmd			= new PauseCommand(this);
		refuelCmd			= new RefuelCommand(gw);
		addCurveCmd  		= new CurveCommand(gw);
		this.show();
		
		play();
			
	}
	
	private void play()
	{	
	
		
		//add all commands to command menu
		buttons.addAll(commands,addPS, addAsteroid, addNPS, addSpaceStation,
					   refill,astCollision,crashAsteroidPS, crashAsteroidNPS,
					   asteroidShot,crashNPS,psShot,npsShot,fireEnemyMissile,pause,refuel);

		//set style of command container
		commands.getAllStyles().setFgColor(ColorUtil.BLACK);
		buttons.getAllStyles().setBgTransparency(100);
		buttons.getAllStyles().setBgColor(ColorUtil.GRAY);
		this.getToolbar().getAllStyles().setBgColor(ColorUtil.BLUE);
		this.getToolbar().getAllStyles().setBgTransparency(255);
		
		//add file menu and commnds to left menu
		tb.addComponentToLeftSideMenu(file);
		tb.addComponentToLeftSideMenu(sound);
		tb.addComponentToLeftSideMenu(newGame);
		tb.addComponentToLeftSideMenu(save);
		tb.addComponentToLeftSideMenu(about);
		tb.addComponentToLeftSideMenu(quit);
		tb.addComponentToLeftSideMenu(undo);
		
		//add all commands to the overflow menu
		tb.addCommandToOverflowMenu(addPSCmd);
		tb.addCommandToOverflowMenu(addAsteroidCmd);
		tb.addCommandToOverflowMenu(addNPSCmd);
		tb.addCommandToOverflowMenu(addSpaceStationCmd);
		tb.addCommandToOverflowMenu(fireEnemyMissileCmd);
		
		//set the commands of file menu
		newGame.setCommand(newgameCmd);
		undo.setCommand(undoCmd);
		quit.setCommand(quitCmd);
		about.setCommand(aboutCmd);
		save.setCommand(saveCmd);
		sound.setCommand(soundCmd);
		
		//set commands in the command menu
		addPS.setCommand(addPSCmd);	
		addNPS.setCommand(addNPSCmd);	
		addAsteroid.setCommand(addAsteroidCmd);
		addSpaceStation.setCommand(addSpaceStationCmd);
		refill.setCommand(refillCmd);
		tick.setCommand(tickCmd);
		astCollision.setCommand(astCollisionCmd);
		crashAsteroidPS.setCommand(crashAsteroidPSCmd);
		crashAsteroidNPS.setCommand(crashAsteroidNPSCmd);
		crashNPS.setCommand(crashNPSCmd);
		asteroidShot.setCommand(asteroidShotCmd);
		psShot.setCommand(psShotCmd);
		npsShot.setCommand(npsShotCmd);
		fireEnemyMissile.setCommand(fireEnemyMissileCmd);
		pause.setCommand(pauseCmd);
		refuel.setCommand(refuelCmd);

		//add key listeners
		addKeyListener('j', hyperSpaceCmd);
		addKeyListener(-93,turnLeftCmd);
		addKeyListener(-94, turnRightCmd);
		addKeyListener(-91, incSpeedCmd);
		addKeyListener(-92, decSpeedCmd);
		addKeyListener(-90, fireMisCmd);
		addKeyListener(44, moveLLeft);
		addKeyListener(46, moveLRight);
		addKeyListener('w', addCurveCmd);
		
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.NORTH, pv);
		this.add(BorderLayout.CENTER, mv);
		this.add(BorderLayout.WEST, buttons);
		this.setTitle("Asteroids Game");
	
		timer = new UITimer(this);
		timer.schedule(globalSpeed, true, this);
		refuel.setEnabled(false);
		this.repaint();
		this.show();
		mv.initializeSize();
		gw.init();
		gw.setHeight(mv.getHeight());
		gw.setWidth(mv.getWidth());
		

	}
		
	public void run()
	{
			gw.tick();
			
			/*RNG for adding new ship
			 * firing a new missile
			 * and adding a new asteroid
			 */
			int astRoll = Game.genRandInt(0,500);
			int misRoll = Game.genRandInt(0,1000);
			
			if(gw.getTotalNPS() < 15)
			{
				int roll = Game.genRandInt(0,1000);
				if(roll >= 50 && roll <=60)
					gw.addNPS();
			}
			
			if(misRoll >= 50 && misRoll <=60)
			{
				gw.fireNPS();	
			}
			
			if(astRoll >= 50 && astRoll <=60)
			{
				gw.addAsteroid();	
			}
			if(gw.isGameOver()) 
			{
				pause();
				if(Dialog.show("Game Over","Game over Score: " + gw.getScore(), "New Game", "Quit"))
				{
					gw.init();
					resume();
				}
				else
					System.exit(0);
			}
	}
	//Disable all commands except for unpause and refuel
	//cancel the timer and turn off sound
	public void pause()
	{
		gw.pauseSound();
		gw.setPaused(true);
		paused = true;
		timer.cancel();
		pause.setText("Resume Game");
		removeKeyListener('j', hyperSpaceCmd);
		removeKeyListener(-93,turnLeftCmd);
		removeKeyListener(-94, turnRightCmd);
		removeKeyListener(-91, incSpeedCmd);
		removeKeyListener(-92, decSpeedCmd);
		removeKeyListener(-90, fireMisCmd);
		removeKeyListener(44, moveLLeft);
		removeKeyListener(46, moveLRight);
		removeKeyListener('w', addCurveCmd);
		addPS.setEnabled(false);
		addAsteroid.setEnabled(false);
		addNPS.setEnabled(false);
		addSpaceStation.setEnabled(false);
		fireEnemyMissile.setEnabled(false);
		refuel.setEnabled(true);
		
	}
	//resume timer and re enable commands for play mode
	public void resume()
	{
		gw.setPaused(false);
		if(gw.isSound())
			gw.playSound();
		paused = false;
		timer.schedule(globalSpeed, true, this);
		pause.setText("Pause Game");
		addKeyListener('j', hyperSpaceCmd);
		addKeyListener(-93,turnLeftCmd);
		addKeyListener(-94, turnRightCmd);
		addKeyListener(-91, incSpeedCmd);
		addKeyListener(-92, decSpeedCmd);
		addKeyListener(-90, fireMisCmd);
		addKeyListener(44, moveLLeft);
		addKeyListener(46, moveLRight);
		addKeyListener('w', addCurveCmd);
		addPS.setEnabled(true);
		addAsteroid.setEnabled(true);
		addNPS.setEnabled(true);
		addSpaceStation.setEnabled(true);
		fireEnemyMissile.setEnabled(true);
		refuel.setEnabled(false);
		mv.unselectAll();
	}
	public boolean isPaused()
	{
		return paused;
	}
	
	//random number generator
	public static int genRandInt(int min, int max)
	{
		Random r = new Random();
		int x = r.nextInt((max - min) + 1) + min;
		return x;
	}
}
