import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

public class Main extends JFrame implements Runnable, KeyListener {
	
	BufferedImage backbuffer;
	Graphics2D g2d;
	Thread gameloop;
	private long start;
	boolean alive;
	private ImageEntity dir;
	private boolean showDir;
	private int addScore;
	private int score;
	private int ammo;
	
	Font myFont;
	
	private Ammo a;
	private Plank p;
	private Teacher[][] t;
	private ImageEntity b1;
	
	private double currVel;
	
	public Main() {
		super("Save the teachers!");
		
		backbuffer = new BufferedImage(900, 700, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		
		setSize(900, 700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		alive=false;
		
		addScore=3;
				
		score=0;
		ammo=3;
		
		showDir=true;
		
		g2d.setColor(Color.BLUE);
		
		myFont=new Font("Goudy Old Style", Font.PLAIN, 50);
	
		p=new Plank();
		p.setGraphics(g2d);
		
		dir=new ImageEntity();
		dir.setGraphics(g2d);
		dir.load("Directions.png");
		dir.setY(17);;
		
		a=new Ammo();
		a.setGraphics(g2d);
		a.setX(334);
		a.setY(700-a.height()-p.height());
		
		b1=new ImageEntity();
		b1.setGraphics(g2d);
		b1.load("b1.png");
		
		currVel=a.getVelX();
		
		t=new Teacher[4][10];
		for(int i=0; i<4; i++)
			for(int j=0; j<10; j++){
				t[i][j]=new Teacher();
				t[i][j].setGraphics(g2d);
			}	
		
		start= System.currentTimeMillis();

		gameloop = new Thread(this);
		gameloop.start();

		addKeyListener(this);
		setFocusable(true);
	}

	public void update(){


		
		int count=0;
		for(int i=0; i<4; i++)
			for(int j=0; j<10; j++)
				if (t[i][j]==null)
					count ++;
		
		if(count==40){
			for(int i=0; i<4; i++)
				for(int j=0; j<10; j++){
					t[i][j]=new Teacher();
					t[i][j].setGraphics(g2d);
				}
			currVel+=2;
			addScore+=3;
			a.setVelX(currVel);
			a.setVelY(currVel);
			a.setY(700-p.height()-a.height());
			a.setX(334);
			alive=false;
			p.setY(700-p.height());
			p.setX(258);
			a.setAngle(Math.PI/2);
		}
			
		
		if(showDir){
			dir.draw();
			g2d.setColor(new Color(63, 106, 183));
			g2d.fillRect(700, 0, 200, 700);
			g2d.setColor(Color.WHITE);
			g2d.setFont(myFont);
			g2d.drawString("Score: ", 705, 100);
			g2d.drawString(""+score, 705, 200);
			g2d.drawString("Muffin", 705, 300);
			g2d.drawString("Count: "+ ammo, 705, 350);
		}
		
		else{
			b1.draw();
			g2d.setColor(new Color(63, 106, 183));
			g2d.fillRect(700, 0, 200, 700);
			
			setFaces();
			drawFaces();
			a.draw();
			p.draw();
			
			g2d.setColor(Color.WHITE);
			g2d.setFont(myFont);
			g2d.drawString("Score: ", 705, 100);
			g2d.drawString(""+score, 705, 200);
			g2d.drawString("Muffin", 705, 300);
			g2d.drawString("Count: "+ ammo, 705, 350);
			
			if(ammo==0){
				g2d.setFont(new Font("Goudy Old Style", Font.PLAIN, 100));
				g2d.drawString("Game over!", 130, 550);
				stop();
			}
			
			if(alive){
				a.moveRandom();
				p.update();
				
				if(a.getY()>=705+a.height()){
					ammo--;
					a.setY(700-p.height()-a.height());
					a.setX(334);
					alive=false;
					p.setY(700-p.height());
					p.setX(258);
					a.setAngle((Math.PI)/2);
				}
				checkCollisions();
			}
	
			if(!alive){
				a.update();
				int x=350;
				int y=700-p.height()-a.height();
				g2d.setColor(Color.RED);	
				g2d.drawLine(x, y, (int)(x+100*Math.cos(a.getAngle())), (int)(y-100*Math.sin(a.getAngle())));
				a.setVelX(currVel*Math.cos(a.getAngle()));
				a.setVelY(-currVel*Math.sin(a.getAngle()));
			}
		}	
		repaint();
	}
	
	public void run(){
		Thread t = Thread.currentThread();
		long elapsed=System.currentTimeMillis()-start;
		long sleepTime=20-elapsed;
		if (sleepTime<=0)
			sleepTime=0;
		while(t == gameloop){
			try{				
				Thread.sleep(sleepTime);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			update();
		}
	}
	
	public void stop(){
			gameloop = null;
	}
	
	public void paint(Graphics g){
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	public void setFaces(){
		int x=0;
		int y=21;
		for(int i=0; i<4; i++){
			for(int j=0; j<10; j++){
				if(t[i][j]!=null){
					t[i][j].setX(x);
					t[i][j].setY(y);
				}	
				x+=70;
			}
			x=0;
			y+=100;
		}		
	}
	
	public void drawFaces(){
		for(int i=0; i<4; i++)
			for(int j=0; j<10; j++)
				if(t[i][j]!=null)
					t[i][j].draw();	
	}
	
	public void keyReleased(KeyEvent k){
		switch(k.getKeyCode()){
		case KeyEvent.VK_LEFT:
			p.setVelX(0);
			if(!alive)
				a.setAngleVel(0);
			break;
		case KeyEvent.VK_RIGHT:
			p.setVelX(0);
			if(!alive)
				a.setAngleVel(0);	
			break;		
		default:
			break;
		}
	}
	
	public void keyTyped(KeyEvent k){}
	
	public void keyPressed(KeyEvent k){
		int keyCode = k.getKeyCode();
		switch(keyCode){	
		case KeyEvent.VK_LEFT:
			p.setVelX(-6);
			if(!alive && !showDir)
				a.setAngleVel(.1);
			break;
		case KeyEvent.VK_RIGHT:
			p.setVelX(6);
			if(!alive && !showDir)
				a.setAngleVel(-.1);
			break;
		case KeyEvent.VK_SPACE:
			if(!alive && !showDir)
				alive=!alive;
			break;
		case KeyEvent.VK_1:
			showDir=!showDir;
			break;
		default:
			break;
		}
	}
	
	public void checkCollisions(){
		boolean change=false;
		if(a.getBounds().intersects(p.getBounds()))
			if(Math.abs(a.getY()+a.height())-(700-p.height())<=10){
				if (a.getVelY()>0)
					a.setVelY(-1*a.getVelY());
			}	
		for(int i=0; i<4; i++){
			for(int j=0; j<10; j++){
				if(t[i][j]!=null){
					if(a.getBounds().intersects(t[i][j].getBounds())){
							change=true;
							t[i][j].playNoise();
							t[i][j]=null;	
							score+=addScore;
							double val=Math.random();
							//if((a.getAngle()>(Math.PI/4) && a.getAngle()<(Math.PI/2))||(a.getAngle()>(3*Math.PI/4) && a.getAngle()<(Math.PI))){
							if(a.getVelX()<0 && Math.abs(a.getVelX())<Math.abs(a.getVelY())){
								a.setVelX(a.getVelX()-val);
								a.setVelY(a.getVelY()+val);
								
							}	
							else if(a.getVelX()>0 && Math.abs(a.getVelX())<Math.abs(a.getVelY())){
								a.setVelX(a.getVelX()+val);
								a.setVelY(a.getVelY()-val);
							}	
					}		
				}	
			}		
		}
		if (change==true)
			a.setVelY(-1*a.getVelY());		
	}
	
	public static void main(String args[]){
		new Main();
	}
	
}