public class Ball {
	//Test Push

	int xPos, yPos, xSize, ySize, Dir = 1, vx, vy;
	// Himmelsrichtungen Punkte Ball
	Point north, east, south, west;

	public Ball(int startXPos, int startYPos){

		xPos = startXPos;
		yPos = startYPos;
		xSize = 25;
		ySize = 25;
		vx = 3;
		vy = -3;

		north = new Point(xPos + xSize/2, yPos);
		east = new Point (xPos + xSize, yPos + ySize/2);
		south = new Point(xPos + xSize/2, yPos + ySize);
		west = new Point(xPos, yPos + ySize/2);}

	public void move() {			
		xPos += vx;
		yPos += vy;

		Point.multiMove(vx, vy, north, east, south, west);
	}
}
