import java.awt.*;

import javax.swing.JPanel;

public class GamePaint extends JPanel{

	Game game;
	Color state2 = new Color (109, 7, 7);
	Color state3 = new Color (226, 162, 11);

	public GamePaint(Game game){
		this.game = game;
		
		
	}

	//Painting Everything
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		int blockcount = 0;
		
		// Rectangles
		for(int y = 0; y < game.blocks.length; y++){
			for(int x = 0; x < game.blocks[y].length; x++){
//				System.out.println((int)(Math.pow((float)game.startAnimationClock/(float)500,2.0) * 100));
//				if (game.blocks[y][x].state == 0 || (1000-game.startAnimationClock) - blockcount * 8 < 0 )
				if (game.blocks[y][x].state == 0 
						|| (int)(Math.random() * 100) < (int)(Math.pow((float)game.startAnimationClock/(float)5000,2.0) * 100))
					continue;
				else if (game.blocks[y][x].state == 1)
					g.setColor(Color.BLACK);
				else if (game.blocks[y][x].state == 2)
					g.setColor(state2);
				else if (game.blocks[y][x].state == 3)
					g.setColor(state3); 
				
				g.fillRect(game.blocks[y][x].xPos, game.blocks[y][x].yPos, game.blocks[y][x].xSize, game.blocks[y][x].ySize);
				
				g.setColor(Color.BLACK);
				blockcount++;
			}
		}

		//The Bar
		g.fillRect(game.bar.xPos, game.bar.yPos, game.bar.xSize, game.bar.ySize);
		//The Ball
		try {
			g.fillOval(game.ball.xPos, game.ball.yPos, game.ball.xSize, game.ball.ySize);	
		}catch(NullPointerException a){}
		//Items
		g.setColor(Color.RED);
		for(Item i: Item.listAll){
			g.fillRect(i.xPos, i.yPos, i.xSize, i.ySize);
		}
		g.setColor(Color.BLACK);
	}
}