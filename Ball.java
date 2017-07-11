public class Ball {
	//Test Push

<<<<<<< HEAD
	int xPos, yPos, xSize, ySize, Dir = 1, vx, vy, vxst = 2, vyst = 4;
												 //st = standard
=======
	int xPos, yPos, xSize, ySize, Dir = 1, vx, vy;
	int xTo, yTo; // target values for changeSize Methode
	
>>>>>>> 0fd3dd41e96fbc99c27407407322341149a36335
	// Himmelsrichtungen Punkte Ball
	Point north, east, south, west;
	

	public Ball(int startXPos, int startYPos){

		xPos = startXPos;
		yPos = startYPos;
		xSize = 26;
		ySize = 26;
		vx = vxst;
		vy = - vyst;

		north = new Point(xPos + xSize/2, yPos);
		east = new Point (xPos + xSize, yPos + ySize/2);
		south = new Point(xPos + xSize/2, yPos + ySize);
		west = new Point(xPos, yPos + ySize/2);
		
	}


	public void move() {			
		xPos += vx;
		yPos += vy;

		Point.multiMove(vx, vy, north, east, south, west);
		
	}
	
	public void changeSize(int xTo, int yTo){
		this.xTo = xTo;
		this.yTo = yTo;
	}
}
