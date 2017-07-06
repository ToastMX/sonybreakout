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

	Thread Thread;

	int xSize = 1300;
	int ySize = xSize / 2;
	int xLoc = 300;
	int yLoc = 50;

	Ball ball;
	Bar bar;
	Block[][] blocks;

	int blockxStart = 100;
	int blockyStart = 100;
	int blockxSize = 75;
	int blockySize = 35;
	int blockDistance = 5;

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

		gameOver = new JLabel("Game Over");
		gameOver.setVisible(false);
		gamePaint.add(gameOver);

		restart = new JButton("Neustart");
		restart.setVisible(false);
		gamePaint.add(restart);
		restart.addActionListener(this);

		this.setFocusable(true);
		addKeyListener(this);

		ball = new Ball(xSize/2, ySize - 60);
		bar = new Bar(xSize/2, ySize - 60, xSize);

		blocks = new Block[3][14];
		for(int y=0; y<=2; y++){
			for(int x=0; x<=13; x++){
				blocks[y][x] = new Block(blockxStart + x*blockxSize + x*blockDistance, blockyStart + y*blockySize + y*blockDistance, blockxSize, blockySize);
			}
		}
	}

	public void run() {
		while (true) {


			ball.move();
			ballFrameCollision();
			ballBarCollision();
			ballBlockCollision();
			bar.move();
			
//			System.out.println("ballxPos = " + ball.xPos + ", ballyPos = " + ball.yPos);
//			System.out.println("N: " + ball.north.toString());
//			System.out.println("E: " + ball.east.toString());
//			System.out.println("S: " + ball.south.toString());
//			System.out.println("W: " + ball.west.toString());

			try {
				gamePaint.repaint();
				Thread.sleep(5); // Ball Speed
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//Ball Collision
	public void ballFrameCollision(){

		if((ball.xPos + ball.xSize) >= xSize){
			ball.vx = -1 * ball.vx;
		}else if (ball.xPos < 0){
			ball.vx = -1 * ball.vx;
		}
		if((ball.yPos + ball.ySize) > ySize - 35){
			ball.vy = 0;
			ball.vx = 0;
			gameOver.setVisible(true);
			restart.setVisible(true);
			removeKeyListener(this);
			bar.left = false;
			bar.right= false;

		}else if (ball.yPos < 0)
			ball.vy = -1 * ball.vy;


	}

	public void ballBarCollision(){

		if (bar.xPos < ball.xPos & bar.xPos + bar.xSize > ball.xPos & bar.yPos <= ball.yPos + ball.ySize & bar.yPos >= ball.ySize + ball.xSize + 5){
			ball.vy = Math.abs(ball.vy) * -1;
			//ToDo Bugfix
			if(bar.left){
				ball.vx -= 1;
			}
			if(bar.right){
				ball.vx += 1;
			}
		}


	}

	public void ballBlockCollision(){
		int x2edge = ball.xPos - blockxStart;
		int areaX = blockxSize + blockDistance;
		int column = x2edge/areaX;

		int y2edge = ball.yPos - blockyStart;
		int areaY = blockySize + blockDistance;
		int row = y2edge/areaY;

		try{
			if (blocks[row][column] != null){
				blocks[row][column] = null;
				ball.vy = 3; // Hier dann noch die anderen Bedingungen, je nach dem wo der Ball den Block trifft
			}
		}catch(ArrayIndexOutOfBoundsException aioob){}

	}
	// Restart
	public void actionPerformed(ActionEvent e) {

		ball = new Ball(xSize/2, ySize - 60);
		bar = new Bar(xSize/2, ySize - 60, xSize);

		blocks = new Block[3][14];
		for(int y=0; y<=2; y++){
			for(int x=0; x<=13; x++){
				blocks[y][x] = new Block(blockxStart + x*blockxSize + x*blockDistance, blockyStart + y*blockySize + y*blockDistance, blockxSize, blockySize);
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