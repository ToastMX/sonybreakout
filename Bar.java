public class Bar {

	int xPos, yPos, xSize, ySize;
	boolean right, left, collidewithbar;
	int gamexSize;
	Point upleft, upright, downleft, downright; // 

	public Bar(int startXPos, int startYPos, int gamexSize){

		this.gamexSize = gamexSize;
		xPos = startXPos;
		yPos = startYPos;
		xSize = 100;
		ySize = 5;
		upleft = new Point(startXPos, startYPos);
		upright = new Point(startXPos, startYPos + xSize);
		downleft = new Point(startXPos + ySize, startYPos);
		downright = new Point(startXPos + ySize, startYPos + xSize);
	}

	public void move(){
		int mx = 0;
		if (right)
			mx = 5;
		else if (left)
			mx = -5;

		if (upleft.x <= 0)
			mx = 0;
		if ((upleft.x + xSize) >= gamexSize)
			mx = 0;
		Point.multiMove(mx, 0, upleft, upright, downleft, downright);
		xPos = upleft.x;
	}

}