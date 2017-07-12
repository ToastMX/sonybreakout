import java.util.ArrayList;

public class Item {

	static ArrayList<Item> listAll = new ArrayList<Item>();
	int xPos, yPos, xSize = 6, ySize = 25, vy = 2, effect;
	Point upleft, upright, downleft, downright;
	
	public Item(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.effect = (int) (Math.random()*6 + 1);
				
		upleft = new Point(xPos, yPos);
		upright = new Point(xPos + xSize, yPos);
		downleft = new Point(xPos, yPos + ySize);
		downright = new Point(xPos + xSize, yPos + ySize);
	}
	
	public static void moveall(){
		for (Item i : listAll){
			i.move();
		}
	}
	
	public void move(){
		yPos += vy;	
		upleft.setPoint(xPos, yPos);
		upright.setPoint(xPos + xSize, yPos);
		downleft.setPoint(xPos, yPos + ySize);
		downright.setPoint(xPos + xSize, yPos + ySize);
	}
	
	public void effect(Ball ball) {

		//TODO
		switch(effect){
		case 1: ball.setSize(ball.xSize*2, ball.ySize*2);
			break;
		case 2: ball.setSize((int)(ball.xSize*0.75), (int)(ball.ySize*0.75));
			break;
		case 3: ball.setSize((int)(ball.xSize*1.5), (int)(ball.ySize*1.5));
			break;
		case 4: ball.setSize((int)(ball.xSize*1.5), ball.ySize);
			break;
		case 5: ball.setSize(ball.xSize, (int)(ball.ySize*1.5));;
			break;
		case 6: //Nothing
			break;
		}
	}
	
}
