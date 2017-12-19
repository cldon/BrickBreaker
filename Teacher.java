
public class Teacher extends ImageEntity{

	SoundClip noise=new SoundClip();
	
	public Teacher(){
		super();
		setTeacher();
	}
	
	private void setTeacher(){
		int choose=(int)(Math.random()*9)+1;
		if(choose==1){
			super.load("Polliners.png");
			noise.load("POLLINERS.wav");
		}	
		if(choose==2){
			super.load("KaSey.png");
			noise.load("KATE.wav");
		}	
		if(choose==3){
			super.load("Nuzzo.png");
			noise.load("HEATHER.wav");
		}	
		if(choose==4){
			super.load("Susan.png");
			noise.load("SUSAN.wav");
		}	
		if(choose==5){
			super.load("Veronica.png");
			noise.load("VERONICA.wav");
		}	
		if(choose==6){
			super.load("Christopher.png");
			noise.load("CHRISTOPHER.wav");
		}	
		if(choose==7){
			super.load("Haves.png");
			noise.load("HAVES.wav");
		}	
		if(choose==8){
			super.load("Laura.png");
			noise.load("LAURA.wav");
		}	
		if(choose==9){
			super.load("Dak.png");
			noise.load("DAK.wav");
		}
	}
	
	public void playNoise(){
		noise.stop();
		noise.play();
	}
}
