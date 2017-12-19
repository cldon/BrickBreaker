import javax.sound.sampled.*;
import java.net.*;
import java.io.*;

public class SoundClip {
	private AudioInputStream sample;
	private Clip clip;
	private boolean loop;
	private int repeat;
	private String filename;
	
	//constructors
	public SoundClip(){
		loop = false;
		repeat = 0;
		filename = "";
		
		try{
			clip = AudioSystem.getClip();
		}
		catch(LineUnavailableException e){}
	}
	
	public SoundClip(String audiofile){
		loop = false;
		repeat = 0;
		filename = audiofile;
		
		try{
			clip = AudioSystem.getClip();
		}
		catch(LineUnavailableException e){}
		
		load(filename);
	}
	
	//accessors
	public Clip getClip(){ return clip;}
	public boolean getLooping(){ return loop;}
	public int getRepeat(){ return repeat;}
	public String getFilename(){ return filename;}
	
	//mutators
	public void setLooping(boolean l){ loop = l;}
	public void setRepeat(int r){ repeat = r;}
	public void setFilename(String f){ filename = f;}
	
	//loading the sound file
	public boolean isLoaded(){ return sample != null;}
	
	public boolean load(String audiofile){
		try{
			filename = audiofile;
			sample = AudioSystem.getAudioInputStream(getURL(filename));
			clip.open(sample);
			return true;
		}
		catch(IOException e){return false;}
		catch(UnsupportedAudioFileException e){ return false;}
		catch(LineUnavailableException e){return false;}
	}
	
	//play the file
	public void play(){
		if(!isLoaded()) return;
		
		clip.setFramePosition(0);		//reset the clip
		
		if(loop)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		else
			clip.loop(repeat);
	}
	
	public void stop(){
		clip.stop();
	}
	
	//helpers
	private URL getURL(String filename){
		URL url = null;
		try{
			url = this.getClass().getResource(filename);
		}
		catch(Exception e){}
		return url;
	}
}
