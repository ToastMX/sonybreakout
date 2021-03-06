import java.util.ArrayList;

public class Item {

	static ArrayList<Item> listAll = new ArrayList<Item>();
	int xPos, yPos, xSize = 6, ySize = 25, vy = 2, effect;
	static int effectVariations = 6;
	Point upleft, upright, downleft, downright;
	
	public Item(int xPos, int yPos){
		Item.listAll.add(this);
		
		this.xPos = xPos;
		this.yPos = yPos;
		effect = (int) (Math.random()*effectVariations + 1);
				
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
	
}
