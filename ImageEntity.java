import java.awt.*;
import java.awt.geom.*;
import java.net.*;

public class ImageEntity extends GameEntity {
	//fields
	protected Image image;
	protected AffineTransform at;
	protected Graphics2D g2d;
	protected double rotation;

	//constructors
	public ImageEntity(){
		super();
		image = null;
		g2d = null;
		rotation = 0;
		at = new AffineTransform();
	}
	
	public ImageEntity(double x, double y, Image i, Graphics2D graph){
		super(x, y);
		image = i;
		g2d = graph;
		rotation = 0;
		at = new AffineTransform();
	}
	
	//accessors
	public Image getImage(){return image;}
	public double getRotation(){return rotation;}
	public void setRotation(double r){rotation = r;}
	public int width(){
		if(image != null)
			return image.getWidth(null);
		else
			return 0;
	}
	public int height(){
		if(image != null)
			return image.getHeight(null);
		else
			return 0;
	}
	public double getCenterX(){
		return getX() + width()/2;
	}
	public double getCenterY(){
		return getY() + height()/2;
	}
	
	//mutators
	public void setImage(Image i){image = i;}
	public void setGraphics(Graphics2D g){g2d = g;}
	
	//loading image from file
	public void load(String filename){
		Toolkit tk = Toolkit.getDefaultToolkit();
		image = tk.getImage(getURL(filename));
		while(getImage().getWidth(null) <= 0);
	}
	
	private URL getURL(String filename){
		URL url = null;
		try{
			url = this.getClass().getResource(filename);
		}
		catch(Exception e){}
		return url;
	}
	
	//draw
	private void transform(){
		at.setToIdentity();
		at.translate((int)x+width()/2, (int)y + height()/2);
		at.rotate(rotation);
		at.translate(-width()/2,  -height()/2);
	}
	
	public void draw(){
		transform();
		g2d.drawImage(image, at, null);
	}
	
	//bounding box
	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle((int)x, (int)y, width(), height());
		return r;
	}	
}
