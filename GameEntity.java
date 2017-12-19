
public class GameEntity {
	//fields
	protected boolean alive;
	protected double x, y;
	protected double velX, velY;
	
	//constructors
	public GameEntity(){
		alive = true;
		x = 0;
		y = 0;
		velX = 0;
		velY = 0;
	}
	
	public GameEntity(double xpos, double ypos){
		alive = true;
		x = xpos;
		y = ypos;
		velX = 0;
		velY = 0;
	}
	
	//accessors
	public boolean isAlive(){return alive;}
	public double getX(){return x;} 
	public double getY(){return y;}
	public double getVelX(){return velX;}
	public double getVelY(){return velY;}
	
	//mutators
	public void setAlive(boolean newAlive){alive = newAlive;}
	public void setX(double newX){x = newX;}
	public void setY(double newY){y = newY;}
	public void setVelX(double newVelX){velX = newVelX;}
	public void setVelY(double newVelY){velY = newVelY;}
	
	public void update(){
		x += velX;
		y += velY;
	}
}
