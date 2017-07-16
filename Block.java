import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Block {

	int  xPos, yPos, xSize, ySize, state, row, column;
	static double itemDropProp = 0.9;
	static File blop = new File("src/Sounds/BlockBlop.wav");

	static Block[][] all;

	public Block (int xPos, int yPos, int xSize, int ySize){
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;

		// 0:gone 1, 2, 3
		this.state = 1;
	}	


	public void hit(Ball ball){
		
		Game.playSound(blop);
		
		state--;
		if(state == 0 && Math.random() < Block.itemDropProp){
			new Item(ball.xPos, yPos + ySize);
		}
	}

}
