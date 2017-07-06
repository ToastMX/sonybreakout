public class Bar {

	int xPos, yPos, xSize, ySize, vx;
	boolean right, left, collidewithbar;
	int gamexSize;
	Point upleft, upright, downleft, downright;
	

	public Bar(int startXPos, int startYPos, int gamexSize){

		this.gamexSize = gamexSize;
		xPos = startXPos;
		yPos = startYPos;
		xSize = 100;
		ySize = 5;
		vx = 5;
		upleft = new Point(startXPos, startYPos);
		upright = new Point(startXPos + xSize, startYPos );
		downleft = new Point(startXPos, startYPos+ ySize);
		downright = new Point(startXPos + xSize, startYPos + ySize);
	}
	
	public int x(){
		return upleft.x;
	}
	
	public int y(){
		return upleft.y;
	}

	public void move(){
		int vx = 0;
		if (right){
			vx = 5;
			if (upright.x >= gamexSize)
				vx = 0;
		}
		else if (left){
			vx = -5;
			if (upleft.x <= 0)
				vx = 0;
		}
		Point.multiMove(vx, 0, upleft, upright, downleft, downright);
		if (upright.x > gamexSize){
			Point.multiMove(gamexSize - upright.x, 0, upleft, upright, downleft, downright);
			System.out.println(upright.x);
		}
		xPos = upleft.x;
	}

}