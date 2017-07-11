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

		// Rectangles
		for(int y = 0; y < game.blocks.length; y++){
			for(int x = 0; x < game.blocks[y].length; x++){

				if (game.blocks[y][x].state == 0)
					continue;
				else if (game.blocks[y][x].state == 1)
					g.setColor(Color.BLACK);
				else if (game.blocks[y][x].state == 2)
					g.setColor(Color.RED);
				else if (game.blocks[y][x].state == 3)
					g.setColor(Color.YELLOW); 
				
				g.fillRect(game.blocks[y][x].xPos, game.blocks[y][x].yPos, game.blocks[y][x].xSize, game.blocks[y][x].ySize);
				
				g.setColor(Color.BLACK);
			}
		}

		//The Bar
		g.fillRect(game.bar.xPos, game.bar.yPos, game.bar.xSize, game.bar.ySize);
		//The Ball
		g.fillOval(game.ball.xPos, game.ball.yPos, game.ball.xSize, game.ball.ySize);		
	}
}