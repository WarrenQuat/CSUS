/*
 * Warren Quattrocchi
 * CSC 133
 * Game Class
 */

package com.mycompany.a2;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Border;
import com.mycompany.a2.commands.*;
import com.mycompany.a2.objects.ModButton;
import com.mycompany.a2.views.*;
import com.codename1.ui.Toolbar;


public class Game extends Form 
{
	private GameWorld 	gw;
	private MapView 	mv;    
	private PointsView 	pv; 
	private Container 	buttons,sideButtons;
	
	private ModButton 	addPS, addAsteroid, addNPS, addSpaceStation, 
						refill,tick,astCollision,crashAsteroidPS,asteroidShot,
				   		quit,about,undo, save, newGame,crashAsteroidNPS,
				   		crashNPS,psShot,npsShot,fireEnemyMissile;
	private CheckBox	sound;
	private Command		addPSCmd,addAsteroidCmd,addNPSCmd,addSpaceStationCmd,
						refillCmd,tickCmd,astCollisionCmd,crashAsteroidPSCmd,
						asteroidShotCmd,crashAsteroidNPSCmd,crashNPSCmd,psShotCmd,
						npsShotCmd,fireEnemyMissileCmd,quitCmd, aboutCmd,newgameCmd,
						saveCmd, undoCmd,soundCmd,moveLLeft,moveLRight,hyperSpaceCmd,
						turnLeftCmd, turnRightCmd, incSpeedCmd, decSpeedCmd, fireMisCmd;
	
	private Label commands,file;
	private Toolbar tb;
	
	public Game() {
		gw = new GameWorld();
		mv = new MapView();
		pv = new PointsView(gw);
		buttons = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		buttons.getAllStyles().setPadding(12, 12,12,12);
		sideButtons = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		sideButtons.getAllStyles().setPadding(12, 12,12,12);
		gw.addObserver(mv);
		gw.addObserver(pv);
		gw.init();
		tb= this.getToolbar();
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.NORTH, pv);
		this.add(BorderLayout.CENTER, mv);
		this.add(BorderLayout.WEST, buttons);
		this.setTitle("Asteroids Game");
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
		commands			= new Label("Commands");
		file 				= new Label("File");
		addPSCmd			= new AddPSCommand(gw);
		addAsteroidCmd		= new AddAsteroidCommand(gw);
		addNPSCmd			= new AddNPSCommand(gw);
		addSpaceStationCmd  = new AddSSCommand(gw);
		refillCmd			= new RefillCommand(gw);
		tickCmd				= new TickCommand(gw);
		astCollisionCmd		= new AstCollisionCommand(gw);
		crashAsteroidPSCmd	= new CrashAsteroidPSCommand(gw);
		crashAsteroidNPSCmd	= new CrashAsteroidNPSCommand(gw);
		asteroidShotCmd		= new AsteroidShotCommand(gw);
		psShotCmd			= new PSShotCommand(gw);
		npsShotCmd			= new NPSShotCommand(gw);
		fireEnemyMissileCmd = new FireEnemyMissileCommand(gw);
		crashNPSCmd			= new CrashNPSCommand(gw);
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

		this.show();
		
		play();
			
	}
	
	private void play()
	{	
	
		
		//add all commands to command menu
		buttons.addAll(commands,addPS, addAsteroid, addNPS, addSpaceStation,
					   refill,tick,astCollision,crashAsteroidPS, crashAsteroidNPS,
					   asteroidShot,crashNPS,psShot,npsShot,fireEnemyMissile);

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
		tb.addCommandToOverflowMenu(refillCmd);
		tb.addCommandToOverflowMenu(tickCmd);
		tb.addCommandToOverflowMenu(astCollisionCmd);
		tb.addCommandToOverflowMenu(crashAsteroidPSCmd);
		tb.addCommandToOverflowMenu(crashAsteroidNPSCmd);
		tb.addCommandToOverflowMenu(asteroidShotCmd);
		tb.addCommandToOverflowMenu(psShotCmd);
		tb.addCommandToOverflowMenu(npsShotCmd);
		tb.addCommandToOverflowMenu(fireEnemyMissileCmd);
		tb.addCommandToOverflowMenu(crashNPSCmd);
		
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

		//add key listeners
		addKeyListener('j', hyperSpaceCmd);
		addKeyListener(-93,turnLeftCmd);
		addKeyListener(-94, turnRightCmd);
		addKeyListener(-91, incSpeedCmd);
		addKeyListener(-92, decSpeedCmd);
		addKeyListener(-90, fireMisCmd);
		addKeyListener(44, moveLLeft);
		addKeyListener(46, moveLRight);
		
		this.repaint();
		gw.setHeight(mv.getHeight());
		gw.setWidth(mv.getWidth());
		this.show();

	}
}
