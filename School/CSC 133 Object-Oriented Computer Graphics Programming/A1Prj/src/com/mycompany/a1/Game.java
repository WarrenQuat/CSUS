/*
 * Warren Quattrocchi
 * CSC 133
 * Game Class
 */

package com.mycompany.a1;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;
import java.lang.StringIndexOutOfBoundsException;

public class Game extends Form {
	private GameWorld gw;
	
	public Game() {
		gw = new GameWorld();
		gw.init();
		play();
	}
	
	@SuppressWarnings("rawtypes")
	private void play()
	{
	Label myLabel=new Label("Enter a Command:"); this.addComponent(myLabel);
				final TextField myTextField=new TextField();
				this.addComponent(myTextField);
				this.show();
				myTextField.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt) 
					{
						try {
						String sCommand=myTextField.getText().toString();
						myTextField.clear();
						switch (sCommand.charAt(0)){
						case 's':
							gw.addPlayerShip();	
							break;
						case 'm':
							gw.printWorld();
							break;
						case 'a':
							gw.addAsteroid();
							break;
						case 'y':
							gw.addNPS();
							break;
						case 'b':
							gw.addSpaceStation();
							break;
						case 'n':
							gw.refillMissiles();
							break;
						case 't':
							gw.update();
							break;
						case 'l':
							gw.rotLeft();
							break;
						case 'r':
							gw.rotRight();
							break;
						case 'i': 
							gw.incSpeed();
							break;
						case 'd':
							gw.decSpeed();
							break;
						case '<':
							gw.rotLauncher();
							break;
						case 'f':
							gw.firePS();
							break;
						case 'L':
							gw.fireNPS();
							break;
						case 'j':
							gw.hyperSpace();
							break;
						case 'k':
							gw.asteroidShot();
							break;
						case 'e':
							gw.NPSShot();
							break;
						case 'E':
							gw.PSShot();
							break;
						case 'c':
							gw.crashAsteroidPS();
							break;
						case 'h':
							gw.crashNPS();
							break;
						case 'x':
							gw.asteroidCol();
							break;
						case 'I':
							gw.crashAsteroidNPS();
							break;
						case 'p':
							gw.print();
							break;
						case 'q':
							//Create a new label prompting user if they are sure they would like to quit
							Label myLabel1=new Label("Are you sure you would like to quit (y/n)"); addComponent(myLabel1);
							final TextField myTextField=new TextField();
							addComponent(myTextField);
							show();
							myTextField.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent evt) 
								{
									String sCommand=myTextField.getText().toString();
									myTextField.clear();
									switch (sCommand.charAt(0)){
									case 'y':
										System.exit(0);
										break;
									case 'Y':
										System.exit(0);
										break;
									default:
										//remove the label/textfield 
										removeComponent(myTextField);
										removeComponent(myLabel1);
										show();
										break;
								}
								}
							}
						);
							break;
							
						default:
							System.out.println("invalid command");
							break;
						}
						}catch(StringIndexOutOfBoundsException e)
						{
							System.err.println("null string error");
				//add code to handle rest of the commands
						} //switch
					} //actionPerformed
				} //new ActionListener()
			); //addActionListener
	}
	public void quit()
	{
		
	}
}
