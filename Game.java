import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class Game extends JFrame implements Runnable, KeyListener, ActionListener {

	Container c;
	GamePaint gamePaint;
	JButton restart;
	JLabel gameOver;
	
	JLabel lebenlabel;
	int leben = 3;
	boolean roundstarted = false;

	Thread Thread;

	int xSize = 1300;
	int ySize = 800;
	int xLoc = 300;
	int yLoc = 50;

	Ball ball;
	Bar bar;
	Block[][] blocks;

	int blockrows = 7;
	int blockcolumns = 17;
	int blockxStart = 100;
	int blockyStart = 100;
	int blockxSize = 60;
	int blockySize = 25;
	int blockDistance = 6;
	
	int startAnimationClock = 1000; // Frames startanimation

	public Game(String username) {
		Thread = new Thread(this, username);
		c = getContentPane();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(xSize, ySize);
		setVisible(true);
		setTitle(username + "'s" + " Game");
		setLocation(xLoc, yLoc);
		setResizable(false);

		gamePaint = new GamePaint(this);
		c.add(gamePaint);

		lebenlabel = new JLabel("Leben: " + leben);
		gamePaint.add(lebenlabel);
		
		KeyListener upRoundStart = new KeyListener(){
			public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode() == 38 & !roundstarted){
					System.out.println("Klappt");
					roundstarted = true;
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent e) {}
		};
		addKeyListener(upRoundStart);

		gameOver = new JLabel("Game Over");
		gameOver.setVisible(false);
		gamePaint.add(gameOver);

		restart = new JButton("Restart");
		restart.setVisible(false);
		gamePaint.add(restart);
		restart.addActionListener(this);
		KeyListener spaceRestart = new KeyListener(){
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_SPACE & restart.isVisible()){
					restart.doClick();
				}
			}
			public void keyReleased(KeyEvent ke) {}
			public void keyTyped(KeyEvent ke) {}
		};
		addKeyListener(spaceRestart);

		addKeyListener(this);
		this.setFocusable(true);

		bar = new Bar(xSize/2 - 80, ySize - 60, xSize);
		ball = new Ball( (int) ( bar.xPos + (bar.xSize)/ 2) - 13, (int) (bar.yPos - 26));

		blocks = new Block[blockrows][blockcolumns];

		for(int y=0; y<=blocks.length-1; y++){
			for(int x=0; x<=blocks[y].length-1; x++){
				blocks[y][x] = new Block(blockxStart + x*blockxSize + x*blockDistance,
						blockyStart + y*blockySize + y*blockDistance,
						blockxSize,
						blockySize);
			}
		}
		
		String[] designRows = leveldesign.lvl1.split(",");
		for(int r=0; r<=designRows.length-1; r++){
			System.out.println(designRows[r]);
			String[] designCollumns = designRows[r].split("");
			for(int c=0; c<=designCollumns.length-1; c++){
				System.out.println(designCollumns[c]);
				blocks[r][c].state = Integer.parseInt(designCollumns[c]);
			}
		}
		
		// different modi to position blocks
		// TODO
		//		if (positionLvl == "center") {
		//			int blockxStart = blockxSize;
		//			int blockDistance = 6;
		//		}
		//		else if (positionLvl == "max"){
		//			int blockxStart = 100;
		//			int blockyStart = 100;
		//			int blockxSize = 45;
		//			int blockySize = 20;
		//			int blockDistance = 6;
		//		}

	}

	public void run() {
		
		
		
		int i = 0;
		while (true) {
			i++;
			try {
				gamePaint.repaint();
				Thread.sleep(1); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (startAnimationClock > 0){
				startAnimationClock -= 1;
			}
			
			if (i % 3 == 0){
				bar.move();
				if(!roundstarted){
				ball.xPos = (int) (bar.xPos + (bar.xSize)/ 2) - 13;
				ball.yPos = (int) (bar.yPos - 26);
				
				ball.north = new Point(ball.xPos + ball.xSize/2, ball.yPos);
				ball.east = new Point (ball.xPos + ball.xSize, ball.yPos + ball.ySize/2);
				ball.south = new Point(ball.xPos + ball.xSize/2, ball.yPos + ball.ySize);
				ball.west = new Point(ball.xPos, ball.yPos + ball.ySize/2);
				
				}
				
			}
			if  (i % 5 == 0 && roundstarted){
				ball.move();
				ballFrameCollision();
				ballBarCollision();
				ballBlockCollision();
			}
		}
	}

	public void ballFrameCollision(){

		if(ball.east.x >= xSize){
			ball.vx = -1 * ball.vx;
		}else if (ball.west.x <= 0){
			ball.vx = -1 * ball.vx;
		}

		//Ball hits ground
		if(ball.south.y >= gamePaint.getHeight()){
			--leben;
			if(leben == 0){
				ball.vy = 0;
				ball.vx = 0;
				gameOver.setVisible(true);
				restart.setVisible(true);
				removeKeyListener(this);
				bar.left = false;
				bar.right= false;
				lebenlabel.setText("Leben: " + leben);
			}else if(leben > 0){
				roundstarted = false;
				ball = new Ball( (int) ( bar.xPos + (bar.xSize)/ 2) - 13, (int) (bar.yPos) - 26);
				lebenlabel.setText("Leben: " + leben);
			}
		}else if (ball.yPos < 0)
			ball.vy = -1 * ball.vy;
	}

	public void ballBarCollision(){
		// collision on top in bar range
		if (bar.upleft.x <= ball.south.x
				& bar.upright.x >= ball.south.x 
				& bar.y() <= ball.south.y 
				& bar.y() + bar.ySize >= ball.south.y){

			ball.vy = Math.abs(ball.vy) * -1;


			if(ball.south.x < bar.xPos + 1*bar.xSize/7)
				ball.vx = - (int) (ball.vxst * 2);

			else if(ball.south.x < bar.xPos + 2*bar.xSize/7)
				ball.vx = - (int) (ball.vxst * 1.5);

			else if(ball.south.x < bar.xPos + 3*bar.xSize/7)
				ball.vx = - (int) (ball.vxst * 1);

			else if(ball.south.x < bar.xPos + 4*bar.xSize/7)
				ball.vx =  (int) (ball.vxst * 0);

			else if (ball.south.x < bar.xPos + 5*bar.xSize/7)
				ball.vx =  (int) (ball.vxst * 1);

			else if(ball.south.x < bar.xPos + 6*bar.xSize/7)
				ball.vx =  (int) (ball.vxst * 1.5);

			else if(ball.south.x < bar.xPos + 7*bar.xSize/7)
				ball.vx =  (int) (ball.vxst * 2);

			//SPINGAME
			//			if(bar.left){
			//				ball.vx -= 1;
			//			}
			//			if(bar.right){
			//				ball.vx += 1;
			//			}
		}	

		// collison left egde
		else if (bar.upleft.x < ball.east.x
				& bar.upright.x > ball.east.x 
				& bar.y() <= ball.east.y 
				& bar.downleft.y >= ball.east.y){

			ball.vy = (ball.vy) * -1;
			ball.vx = (ball.vxst) * -3;
		}
		// collison right egde
		else if (bar.upleft.x < ball.west.x
				& bar.upright.x > ball.west.x 
				& bar.y() <= ball.west.y 
				& bar.downleft.y >= ball.west.y){

			ball.vy = (ball.vy) * -1;
			ball.vx = (ball.vxst) * 3;
		}
	}

	public void ballBlockCollision(){

		Block north = this.getBlockByKords(ball.north.x, ball.north.y);
		Block east = this.getBlockByKords(ball.east.x, ball.east.y);
		Block south = this.getBlockByKords(ball.south.x, ball.south.y);
		Block west = this.getBlockByKords(ball.west.x, ball.west.y);

		// from bottom 
		if (north != null && north.state != 0){
			north.state -= 1;
			ball.vy = Math.abs(ball.vy);
		}
		// from top 
		else if (south != null && south.state != 0){
			south.state -= 1;
			ball.vy = Math.abs(ball.vy) * -1;
		}
		// from right
		else if (west != null && west.state != 0){
			west.state -= 1;
			ball.vx = Math.abs(ball.vy);
		}
		// from left
		else if (east != null && east.state != 0){
			east.state -= 1;
			ball.vx = Math.abs(ball.vx) * -1;
		}
	}

	public Block getBlockByKords(int x, int y){
		int x2edge = x - blockxStart;
		int areaX = blockxSize + blockDistance;
		int column = x2edge/areaX;

		int y2edge = y - blockyStart;
		int areaY = blockySize + blockDistance;
		int row = y2edge/areaY;

		try{
			return blocks[row][column];
		}catch(ArrayIndexOutOfBoundsException aioob){
			return null;
		}
	}

	// Restart
	public void actionPerformed(ActionEvent e) {
		roundstarted = false;
		leben = 3;
		lebenlabel.setText("Leben: " + leben);
		bar = new Bar(xSize/2 - 80, ySize - 60, xSize);
		ball = new Ball( (int) ( bar.xPos + (bar.xSize)/ 2) - 13, (int) (bar.yPos) - 26);
		
		blocks = new Block[blockrows][blockcolumns];
		for(int y=0; y<=blocks.length-1; y++){
			for(int x=0; x<=blocks[y].length-1; x++){
				blocks[y][x] = new Block(blockxStart + x*blockxSize + x*blockDistance,
						blockyStart + y*blockySize + y*blockDistance,
						blockxSize,
						blockySize);
			}
		}

		restart.setVisible(false);
		gameOver.setVisible(false);
		this.addKeyListener(this);

	}

	// Movement of Bar (Key-Listener)
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == 37) {
			bar.left = true;
			bar.right = false;
		}
		if (e.getKeyCode() == 39) {
			bar.left = false;
			bar.right = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		bar.left = false;
		bar.right = false;
	}
	public void keyTyped(KeyEvent e) {}
}