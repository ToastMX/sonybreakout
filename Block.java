public class Block {

	int  xPos, yPos, xSize, ySize, state, row, column;
	static int rectCount;

	public Block (int xPos, int yPos, int xSize, int ySize){
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		
		// 1: normal 0:gone
		this.state = 1;
	}	

}
