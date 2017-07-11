public class Block {

	int  xPos, yPos, xSize, ySize, state, row, column;
	static int rectCount;
	

	public Block (int xPos, int yPos, int xSize, int ySize){
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		
		// 0:gone 1, 2, 3
		this.state = 3;
		
		// random state:
		int mini = 1; 
		int  maxi = 100;
		int randVal = mini + (int)(Math.random() * ((maxi - mini) + 1));
		if (randVal < 90)
			this.state = 1;
		else if (randVal < 99)
			this.state = 2;
		else
			this.state = 3;
		
		
	}	

}
