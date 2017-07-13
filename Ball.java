public class Ball {

	int xPos, yPos, xSize, ySize, dir = 1, vx, vy, vxst = 2, vyst = 2*vxst;
												 //st = standard
	
	// Himmelsrichtungen Punkte Ball
	Point north, east, south, west;
	

	public Ball(int startXPos, int startYPos, int setXSize, int setYSize){

		xPos = startXPos;
		yPos = startYPos;
		xSize = setXSize;
		ySize = setYSize;
		vx = 0;
		vy = 0;

		north = new Point(xPos + xSize/2, yPos);
		east = new Point (xPos + xSize, yPos + ySize/2);
		south = new Point(xPos + xSize/2, yPos + ySize);
		west = new Point(xPos, yPos + ySize/2);
	}
	
	public void move() {			
		this.move(vx, vy);
	}
	
	public void move(int x, int y) {			
		xPos += vx;
		yPos += vy;

		Point.multiMove(vx, vy, north, east, south, west);
	}
	
	public void setSize(int xTo, int yTo){
		xSize = xTo;
		ySize = yTo;
		north.setPoint(xPos + xSize/2, yPos);
		east.setPoint(xPos + xSize, yPos + ySize/2);
		south.setPoint(xPos + xSize/2, yPos + ySize);
		west.setPoint(xPos, yPos + ySize/2);
	}
}
