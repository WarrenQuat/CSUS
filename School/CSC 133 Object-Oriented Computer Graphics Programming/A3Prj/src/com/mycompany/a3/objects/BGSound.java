package com.mycompany.a3.objects;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable {
	 private Media m;
	 public BGSound(String fileName) {
	   try {
	    InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName); 
	    m =MediaManager.createMedia(is, "audio/wav", this);
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	 }
	 public void play()
	 {
		 m.setVolume(10);
		 m.setTime(0);
		 m.play();
	 }
	 	public void pause()
	 	{ 
	 		m.pause();
	 	} 
	 	public void run()
	 	{
	 		m.setTime(0);
	 		m.play();
	 	}
}
	 
