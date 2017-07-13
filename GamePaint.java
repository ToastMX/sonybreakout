import java.awt.*;

import javax.swing.JPanel;

public class GamePaint extends JPanel{

	Game game;

	public GamePaint(Game game){
		this.game = game;
		
	}

	//Painting Everything
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		int blockcount = 0;
		
		this.setBackground(game.level.stateColors[0]);
		
		// Rectangles
		for(int y = 0; y < Block.all.length; y++){
			for(int x = 0; x < Block.all[y].length; x++){
//				System.out.println((int)(Math.pow((float)game.startAnimationClock/(float)500,2.0) * 100));
//				if (game.blocks[y][x].state == 0 || (1000-game.startAnimationClock) - blockcount * 8 < 0 )
				if (Block.all[y][x].state == 0 
						|| (int)(Math.random() * 100) < (int)(Math.pow((float)game.level.startAnimationClock/(float)5000,2.0) * 100))
					continue;
				
				g.setColor(game.level.stateColors[Block.all[y][x].state]);
				
				g.fillRect(Block.all[y][x].xPos, 
						Block.all[y][x].yPos, 
						Block.all[y][x].xSize, 
						Block.all[y][x].ySize);
				
				g.setColor(Color.BLACK);
				blockcount++;
			}
		}
		// direction line
//		g.setColor(new Color(100,100,100));
//        g.drawLine(game.ball.xPos + game.ball.xSize/2, game.ball.yPos + game.ball.ySize/2,
//        			game.ball.xPos + game.ball.xSize/2 + 400* game.ball.vx, 
//        			game.ball.yPos + game.ball.ySize/2 + 400* game.ball.vy);
//        g.setColor(Color.BLACK);
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