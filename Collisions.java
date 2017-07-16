import java.util.ArrayList;
import java.util.Iterator;

public class Collisions{

	static Game g;

	public static void referenceGame(Game game){
		g = game;
	}

	public static void ballFrameCollision(){

		for (Iterator<Ball> it = Ball.listAll.iterator(); it.hasNext(); ){
			Ball b = it.next();

			//Ball hits side
			if(b.east.x >= g.xSize){
				b.vx = -1 * b.vx;
			}else if (b.west.x <= 0){
				b.vx = -1 * b.vx;
			}

			//Ball hits ground
			if(b.south.y >= g.gamePaint.getHeight()){

				if(Ball.listAll.size() == 1){
					--g.leben;
					if(g.leben == 0){
						g.gameOver();
						break;
					}else{
						Game.playSound(g.LifeLost);
						g.startNewRound();
					}
				}
				else{
					System.out.println("remove");
					it.remove();
				}
				//Ball hits top
			}else if (b.yPos <= 0)
				b.vy = -1 * b.vy;
		}
	}

	public static void ballBarCollision(){


		for(Ball b : Ball.listAll){

			// collision on top in bar range
			if (g.bar.upleft.x <= b.south.x
					& g.bar.upright.x >= b.south.x 
					& g.bar.y() <= b.south.y 
					& g.bar.y() + g.bar.ySize >= b.south.y){

				b.vy = Math.abs(b.vy) * -1;


				if(b.south.x < g.bar.xPos + 1*g.bar.xSize/7)
					b.vx = - (int) (b.vxst * 2);

				else if(b.south.x < g.bar.xPos + 2*g.bar.xSize/7)
					b.vx = - (int) (b.vxst * 1.5);

				else if(b.south.x < g.bar.xPos + 3*g.bar.xSize/7)
					b.vx = - (int) (b.vxst * 1);

				else if(b.south.x < g.bar.xPos + 4*g.bar.xSize/7)
					b.vx =  (int) (b.vxst * 0);

				else if (b.south.x < g.bar.xPos + 5*g.bar.xSize/7)
					b.vx =  (int) (b.vxst * 1);

				else if(b.south.x < g.bar.xPos + 6*g.bar.xSize/7)
					b.vx =  (int) (b.vxst * 1.5);

				else if(b.south.x < g.bar.xPos + 7*g.bar.xSize/7)
					b.vx =  (int) (b.vxst * 2);

			}	

			// collison left egde
			else if (g.bar.upleft.x < b.east.x
					& g.bar.upright.x > b.east.x 
					& g.bar.y() <= b.east.y 
					& g.bar.downleft.y >= b.east.y){

				b.vy = (b.vy) * -1;
				b.vx = (b.vxst) * -3;
			}
			// collison right egde
			else if (g.bar.upleft.x < b.west.x
					& g.bar.upright.x > b.west.x 
					& g.bar.y() <= b.west.y 
					& g.bar.downleft.y >= b.west.y){

				b.vy = (b.vy) * -1;
				b.vx = (b.vxst) * 3;
			}
		}
	}

	public static void ballBlockCollision(){

		for(Ball b : Ball.listAll){
		
		Block north = g.getBlockByKords(b.north.x, b.north.y);
		Block east = g.getBlockByKords(b.east.x, b.east.y);
		Block south = g.getBlockByKords(b.south.x, b.south.y);
		Block west = g.getBlockByKords(b.west.x, b.west.y);


		// from bottom 
		if (north != null && north.state != 0){
			north.hit(b);
			b.vy = Math.abs(b.vy);
			g.checkWinningGame();
		}
		// from top 
		if (south != null && south.state != 0){
			south.hit(b);
			b.vy = Math.abs(b.vy) * -1;
			g.checkWinningGame();
		}
		// from right
		if (west != null && west.state != 0){
			west.hit(b);
//			System.out.println("right side");
			//on edge from bottom
			if(b.vx == 0){
				b.vx = b.vxst * 2;
				b.vy = Math.abs(b.vy) * -1;
			}
			//on side
			else {
				b.vx = b.vx * -1;
			}
			// bugfix für doble hits, verschiebe ball a bit
			int howfarin = west.xPos + west.xSize - b.west.x;
			b.move(0, howfarin*10);
//			System.out.println("right edge" + howfarin);
			
			g.checkWinningGame();
		}
		// from left
		if (east != null && east.state != 0){
			east.hit(b);
//			System.out.println("left side");
			//on edge from bottom
			if(b.vx == 0){
				b.vx = b.vxst * -2;
			}
			//on side
			else {
				b.vx = b.vx * -1;
			}
			// bugfix für doble hits, verschiebe ball a bit
			int howfarin = east.xPos - b.east.x;
			b.move(0, howfarin*10);
//			System.out.println("left edge" + howfarin);
			
			g.checkWinningGame();
		}
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
		case 4: new Ball(g.bar.xPos + g.bar.xSize/2 - g.level.ballXSize/2, g.bar.upleft.y - g.level.ballYSize - 1, g.level.ballXSize, g.level.ballYSize, 2, -4);
			break;
		case 5: new Ball(g.bar.xPos + g.bar.xSize/2 - g.level.ballXSize/2, g.bar.upleft.y - g.level.ballYSize - 1, g.level.ballXSize, g.level.ballYSize, -2, -4);
			break;
		case 6: new Ball(g.bar.xPos + g.bar.xSize/2 - g.level.ballXSize/2, g.bar.upleft.y - g.level.ballYSize - 1, g.level.ballXSize, g.level.ballYSize, 2, -4);
			break;
		}
	}
}
