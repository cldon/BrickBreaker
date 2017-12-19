
public class Ammo extends ImageEntity{

	private double angleVel;
	private double angle;
	
	public Ammo(){
		super();
		super.load("muffin.png");
		angleVel=0;
		angle=Math.PI/2;
		velX=6;
		velY=-6;
	}
	
	public void setAngleVel(double x){
		angleVel=x;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public void setAngle(double x){
		angle=x;
	}
	
	public void update(){
		angle+=angleVel;
		
		if (angle>(10*Math.PI)/11)
			angle=(10*Math.PI)/11;
		if (angle<Math.PI/11)
			angle=Math.PI/11;
		
		if(x<0){
			if(velX<0)
				velX*=-1;
		}	
		if(y<21){
			if(velY<0)
				velY*=-1;
			/*if((int)(Math.random()*2)==0)
				velY+=.1;
			else	
				velY-=.1;*/
			
		}	
		if(x>=700-width())	
			if(velX>0)
				velX*=-1;
			/*if((int)(Math.random()*2)==0)
				velX+=.1;
			else	
				velX-=.1;*/
		
	}
	
	public void moveRandom(){
		x+=velX;
		y+=velY;
		update();
	}
}
