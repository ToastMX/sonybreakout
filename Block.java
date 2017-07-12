public class Block {

	int  xPos, yPos, xSize, ySize, state, row, column;
	static double itemDropProp = 0.9;
	
	static Block[][] all;

	public Block (int xPos, int yPos, int xSize, int ySize){
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;

		// 0:gone 1, 2, 3
		this.state = 1;
	}	

	public void hit(){
		state--;
		if(Math.random() < Block.itemDropProp){
			Item.listAll.add(new Item(xPos + xSize/2, yPos + ySize));
		}
	}
	
	

}
