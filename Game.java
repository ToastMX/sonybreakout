import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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

	Thread Thread;

	int xSize = 1300;
	int ySize = 800;
	int xLoc = 300;
	int yLoc = 50;

	Ball ball;
	Bar bar;
	Block[][] blocks;
	
	Leveldesign level;
	int levelnum = 0;
	
	public Game(String username) {
		Collisions.referenceGame(this);
		Leveldesign.readFile("src/lvldata.json");

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
				if(ke.getKeyCode() == 38 && bar.catchBall != null){
					bar.catchBall.vy = bar.catchBall.vyst;
					bar.catchBall = null;
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

			if (level.startAnimationClock > 0){
				level.startAnimationClock -= 1;
			}

			if (i % 3 == 0){
				bar.move();

			}
			if  (i % 5 == 0){
				Item.moveall();
				ball.move();
				Collisions.itemBarCollision();
				Collisions.ballFrameCollision();
				Collisions.ballBarCollision();
				Collisions.ballBlockCollision();
			}
		}
	}

	public void startNewGame() {
		//bar.catchBall = null;
		leben = 3;

		bar = new Bar(xSize/2 - 80, ySize - 60, gamePaint.getWidth());
		
//		ball gets build at newRound
		
		
		this.level = Leveldesign.listAll.get(this.levelnum);
		
		// read lvl and build blocks
		level.blockrows = level.map.length;
		level.blockcolumns = level.map[0].length;
		
		level.blockySize = level.blockySize;
		level.blockDistance = level.blockDistance;

		blocks = new Block[level.blockrows][level.blockcolumns];
		// scale Blocks to max?
		if (true){
			int blockAreaX = gamePaint.getWidth() - level.blockxStart * 2;
			level.blockxSize = blockAreaX / level.blockcolumns - level.blockDistance;
		}
		
		for(int r=0; r<level.blockrows; r++){
			for(int c=0; c<level.blockcolumns; c++){
				blocks[r][c] = new Block(level.blockxStart + c*level.blockxSize + c*level.blockDistance,
						level.blockyStart + r*level.blockySize + r*level.blockDistance,
						level.blockxSize,
						level.blockySize);
				try{
					blocks[r][c].state = level.map[r][c];
				}catch(ArrayIndexOutOfBoundsException mapempty){
					// wenn level.map zu klein
					try{
						blocks[r][c].state = 0;
					}catch(ArrayIndexOutOfBoundsException blockAraayIndex){}
					// wenn blocks zu klein
					
				}
				
			}
		}




		restart.setVisible(false); 	// restart knopf
		gameOver.setVisible(false);	// gamoversign
		this.addKeyListener(this);	// make bar active

		startNewRound();

	}
	public void startNewRound(){
		ball = new Ball(
				(int)(bar.xPos + (bar.xSize)/ 2) - 13, 
				(int)(bar.yPos) - 26,
				level.ballXSize,
				level.ballYSize);
		bar.catchBall = ball;
		lebenlabel.setText("Leben: " + leben);
	}

	public void effect(int effect) {
		System.out.println("Effekt kan hier nun Programmiert werden :)");

		//TODO
		switch(effect){
		case 1: //
			break;
		case 2: //
			break;
		case 3: //
			break;
		case 4: //
			break;
		case 5: //
			break;
		case 6: //
			break;
		}
	}

	public Block getBlockByKords(int x, int y){
		int x2edge = x - level.blockxStart;
		int areaX = level.blockxSize + level.blockDistance;
		int column = Math.floorDiv(x2edge, areaX);

		int y2edge = y - level.blockyStart;
		int areaY = level.blockySize + level.blockDistance;
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
