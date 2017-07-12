import java.util.ArrayList;

public class Item {

	static ArrayList<Item> listAll = new ArrayList<Item>();
	int xPos, yPos, xSize = 6, ySize = 25, vy = 2, effect;
	Point upleft, upright, downleft, downright;
	
	public Item(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.effect = (int) Math.random()*6 + 1;
				
		
		upleft = new Point(xPos, yPos);
		upright = new Point(xPos + xSize, yPos);
		downleft = new Point(xPos, yPos + ySize);
		downright = new Point(xPos + xSize, yPos + ySize);
	}
	
	public static void moveall(){
		for (Item i : listAll){
			i.yPos += i.vy;
			i.pointsAdjust();
		}
	}
	
//	public void start(){
//		yPos += vy;
//		pointAdjust();
//	}
	
	public void pointsAdjust(){
		upleft.x = xPos;
		upleft.y = yPos;
		upright.x = xPos + xSize;
		upright.y = yPos;
		downleft.x = xPos;
		downleft.y = yPos + ySize;
		downright.x = xPos + xSize;
		downright.y = yPos + ySize;
	}
	
}
