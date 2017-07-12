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
	int leben;
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
		Collisions.referenceGame(this);
		
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
		
		KeyListener upRoundStart = new KeyListener(){
			public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode() == 38 & !roundstarted){
					roundstarted = true;
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent e) {}
		};
		addKeyListener(upRoundStart);
		
		lebenlabel = new JLabel("Leben: " + leben);
		gamePaint.add(lebenlabel);
		gameOver = new JLabel("Game Over");
		gamePaint.add(gameOver);
		restart = new JButton("Restart");
		gamePaint.add(restart);
		restart.addActionListener(this);
		
		this.setFocusable(true);
		
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

		startNewGame();
		
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
					if (bar.xPos >= 0){
						ball.xPos = (int) (bar.xPos + (bar.xSize)/ 2) - 13;
					}
					else{
						ball.xPos = 1;
					}
					if (bar.upright.x >= xSize){
						ball.xPos = gamePaint.getWidth() - 1 - ball.xSize;
					}
					
					ball.yPos = (int) (bar.yPos - 26);
					
					ball.north = new Point(ball.xPos + ball.xSize/2, ball.yPos);
					ball.east = new Point (ball.xPos + ball.xSize, ball.yPos + ball.ySize/2);
					ball.south = new Point(ball.xPos + ball.xSize/2, ball.yPos + ball.ySize);
					ball.west = new Point(ball.xPos, ball.yPos + ball.ySize/2);
				}
				
			}
			if  (i % 5 == 0 && roundstarted){
				ball.move();
				Collisions.ballFrameCollision();
				Collisions.ballBarCollision();
				Collisions.ballBlockCollision();
			}
		}
	}
	
	public void startNewGame() {
		roundstarted = false;
		leben = 3;

		bar = new Bar(xSize/2 - 80, ySize - 60, xSize);
//		ball = new Ball( (int) ( bar.xPos + (bar.xSize)/ 2) - 13, (int) (bar.yPos - 26));
//		ball gets build at newRound
		
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



		restart.setVisible(false); 	// restart knopf
		gameOver.setVisible(false);	// gamoversign
		this.addKeyListener(this);	// make bar active
		
		startNewRound();

	}

	public void startNewRound(){
		roundstarted = false; //wait until top arrow is pressed
		ball = new Ball( (int) ( bar.xPos + (bar.xSize)/ 2) - 13, (int) (bar.yPos) - 26);
		lebenlabel.setText("Leben: " + leben);
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

	// Restart Button
	public void actionPerformed(ActionEvent e) {
		startNewGame();
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
		if (e.getKeyCode() == 37) {
			bar.left = false;
		}
		if (e.getKeyCode() == 39) {
			bar.right = false;
		}
	}
	public void keyTyped(KeyEvent e) {}
}
