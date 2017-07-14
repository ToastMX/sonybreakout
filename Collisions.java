import java.util.ArrayList;
import java.util.Iterator;

public class Collisions{

	static Game g;

	public static void referenceGame(Game game){
		g = game;
	}

	public static void ballFrameCollision(){

		if(g.ball.east.x >= g.xSize){
			g.ball.vx = -1 * g.ball.vx;
		}else if (g.ball.west.x <= 0){
			g.ball.vx = -1 * g.ball.vx;
		}

		//Ball hits ground
		if(g.ball.south.y >= g.gamePaint.getHeight()){
			--g.leben;
			if(g.leben == 0){
				g.gameOver();
			}else if(g.leben > 0){
				Game.playSound(g.LifeLost);
				g.startNewRound();
			}
		}else if (g.ball.yPos <= 0)
			g.ball.vy = -1 * g.ball.vy;
	}

	public static void ballBarCollision(){
		// collision on top in bar range
		if (g.bar.upleft.x <= g.ball.south.x
				& g.bar.upright.x >= g.ball.south.x 
				& g.bar.y() <= g.ball.south.y 
				& g.bar.y() + g.bar.ySize >= g.ball.south.y){

			g.ball.vy = Math.abs(g.ball.vy) * -1;


			if(g.ball.south.x < g.bar.xPos + 1*g.bar.xSize/7)
				g.ball.vx = - (int) (g.ball.vxst * 2);

			else if(g.ball.south.x < g.bar.xPos + 2*g.bar.xSize/7)
				g.ball.vx = - (int) (g.ball.vxst * 1.5);

			else if(g.ball.south.x < g.bar.xPos + 3*g.bar.xSize/7)
				g.ball.vx = - (int) (g.ball.vxst * 1);

			else if(g.ball.south.x < g.bar.xPos + 4*g.bar.xSize/7)
				g.ball.vx =  (int) (g.ball.vxst * 0);

			else if (g.ball.south.x < g.bar.xPos + 5*g.bar.xSize/7)
				g.ball.vx =  (int) (g.ball.vxst * 1);

			else if(g.ball.south.x < g.bar.xPos + 6*g.bar.xSize/7)
				g.ball.vx =  (int) (g.ball.vxst * 1.5);

			else if(g.ball.south.x < g.bar.xPos + 7*g.bar.xSize/7)
				g.ball.vx =  (int) (g.ball.vxst * 2);

			//SPINGAME
			//			if(bar.left){
			//				ball.vx -= 1;
			//			}
			//			if(bar.right){
			//				ball.vx += 1;
			//			}
		}	

		// collison left egde
		else if (g.bar.upleft.x < g.ball.east.x
				& g.bar.upright.x > g.ball.east.x 
				& g.bar.y() <= g.ball.east.y 
				& g.bar.downleft.y >= g.ball.east.y){

			g.ball.vy = (g.ball.vy) * -1;
			g.ball.vx = (g.ball.vxst) * -3;
		}
		// collison right egde
		else if (g.bar.upleft.x < g.ball.west.x
				& g.bar.upright.x > g.ball.west.x 
				& g.bar.y() <= g.ball.west.y 
				& g.bar.downleft.y >= g.ball.west.y){

			g.ball.vy = (g.ball.vy) * -1;
			g.ball.vx = (g.ball.vxst) * 3;
		}
	}

	public static void ballBlockCollision(){

		Block north = g.getBlockByKords(g.ball.north.x, g.ball.north.y);
		Block east = g.getBlockByKords(g.ball.east.x, g.ball.east.y);
		Block south = g.getBlockByKords(g.ball.south.x, g.ball.south.y);
		Block west = g.getBlockByKords(g.ball.west.x, g.ball.west.y);


		// from bottom 
		if (north != null && north.state != 0){
			north.hit(g.ball);
			g.ball.vy = Math.abs(g.ball.vy);
			g.checkWinningGame();
		}
		// from top 
		if (south != null && south.state != 0){
			south.hit(g.ball);
			g.ball.vy = Math.abs(g.ball.vy) * -1;
			g.checkWinningGame();
		}
		// from right
		if (west != null && west.state != 0){
			west.hit(g.ball);
//			System.out.println("right side");
			//on edge from bottom
			if(g.ball.vx == 0){
				g.ball.vx = g.ball.vxst * 2;
				g.ball.vy = Math.abs(g.ball.vy) * -1;
			}
			//on side
			else {
				g.ball.vx = g.ball.vx * -1;
			}
			// bugfix f�r doble hits, verschiebe ball a bit
			int howfarin = west.xPos + west.xSize - g.ball.west.x;
			g.ball.move(0, howfarin*10);
//			System.out.println("right edge" + howfarin);
			
			g.checkWinningGame();
		}
		// from left
		if (east != null && east.state != 0){
			east.hit(g.ball);
//			System.out.println("left side");
			//on edge from bottom
			if(g.ball.vx == 0){
				g.ball.vx = g.ball.vxst * -2;
			}
			//on side
			else {
				g.ball.vx = g.ball.vx * -1;
			}
			// bugfix f�r doble hits, verschiebe ball a bit
			int howfarin = east.xPos - g.ball.east.x;
			g.ball.move(0, howfarin*10);
//			System.out.println("left edge" + howfarin);
			
			g.checkWinningGame();
		}
	}

	public static void itemBarCollision(){
		for (Iterator<Item> it = Item.listAll.iterator(); it.hasNext(); ){
			Item i = it.next();
			if (g.bar.upleft.x <= i.downright.x
					& g.bar.upright.x >= i.downleft.x 
					& g.bar.y() <= i.downright.y 
					& g.bar.y() + g.bar.ySize >= i.upright.y){

				Collisions.effect(g.ball, i);
				it.remove();
			}
			if (i.downleft.y < 0){
				it.remove();
			}
		}
	}
	
	public static void effect(Ball ball, Item item) {
		
		//TODO
		switch(item.effect){
		case 1: 
			g.leben++;
			g.lebenlabel.setText("Leben: " + g.leben);
			break;
		case 2: ball.setSize((int)(ball.xSize*0.75), (int)(ball.ySize*0.75));
			break;
		case 3: ball.setSize((int)(ball.xSize*1.5), (int)(ball.ySize*1.5));
			break;
//		case 4: ball.setSize((int)(ball.xSize*1.5), ball.ySize);
//			break;
//		case 5: ball.setSize(ball.xSize, (int)(ball.ySize*1.5));;
//			break;
//		case 6: //Nothing
//			break;
		}
	}
}
