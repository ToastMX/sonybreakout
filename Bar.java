public class Bar {

	int xPos, yPos, xSize, ySize;
	boolean right, left, collidewithbar;
	int gamexSize;
	Point upleft, upright, downleft, downright;
	int vx = 5;

	Ball catchBall = null;

	public Bar(int startXPos, int startYPos, int gamexSize){

		this.gamexSize = gamexSize;
		xPos = startXPos;
		yPos = startYPos;
		xSize = 160;
		ySize = 6;
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
			vx = this.vx;
			if (upright.x >= gamexSize + xSize/2){
				vx = 0;
			}
		}
		else if (left){
			vx = this.vx * -1;
			if (upleft.x <= 0 - xSize/2){
				vx = 0;
			}
		}
		Point.multiMove(vx, 0, upleft, upright, downleft, downright);
		xPos = upleft.x;

		if (catchBall != null){
			ballmovewithbar(catchBall);
		}
	}

	public void ballmovewithbar(Ball ball){


		if (upleft.x + this.xSize/2 - ball.xSize <= 0){
			ball.xPos = 1;
		}
		else if (upright.x - this.xSize/2 + ball.xSize >= gamexSize){
			ball.xPos = gamexSize - 1 - ball.xSize;
		}
		else{
			ball.xPos = (int) (upleft.x + xSize/ 2) - ball.xSize/2;
		}

		ball.yPos = (int) (yPos - ball.ySize);

		ball.north = new Point(ball.xPos + ball.xSize/2, ball.yPos);
		ball.east = new Point (ball.xPos + ball.xSize, ball.yPos + ball.ySize/2);
		ball.south = new Point(ball.xPos + ball.xSize/2, ball.yPos + ball.ySize);
		ball.west = new Point(ball.xPos, ball.yPos + ball.ySize/2);
	}
}
