
public class Plank extends ImageEntity {

	public Plank(){
		super();
		super.load("Warriners 02.png");
		setY(700-height());
		setX(258);
	}
	
	public void update(){
		x+=velX;
		
		if(x<=0)
			x=0;
		if(x>=700-width())
			x=700-width();
	}
}
