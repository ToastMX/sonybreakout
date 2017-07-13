import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class Game extends JFrame implements Runnable, KeyListener{

	Container c;
	GamePaint gamePaint;
	Thread Thread;
	JButton restart;
	JLabel gameOver, lebenlabel;
	static File GameOver = new File("src/Sounds/GameOverSoundanimation.wav");
	static File LifeLost = new File("src/Sounds/LifeLost.wav");

	int xSize = 1300, ySize = 800, xLoc = 300, yLoc = 50, leben;

	Ball ball;
	Bar bar;
	//	Block[][] blocks;

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

		lebenlabel = new JLabel("Leben: " + leben);
		gamePaint.add(lebenlabel);
		gameOver = new JLabel("Game Over");
		gamePaint.add(gameOver);
		restart = new JButton("Restart");
		gamePaint.add(restart);

		this.setFocusable(true);

		ActionListener restartListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				startNewGame();
			}
		};
		restart.addActionListener(restartListener);

		this.addKeyListener(this);
		startNewGame();
	}

	public void run() {
		leben = 3;

		int i = 0;
		while (true) {
			i++;
			try {
				gamePaint.repaint();
				Thread.sleep(2); 
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
		leben = 3;
		bar = new Bar(xSize/2 - 80, ySize - 60, gamePaint.getWidth());

		Item.listAll.removeAll(Item.listAll);
		this.level = Leveldesign.listAll.get(this.levelnum);

		// read lvl and build blocks
		level.blockrows = level.map.length;
		level.blockcolumns = level.map[0].length;

		level.blockySize = level.blockySize;
		level.blockDistance = level.blockDistance;

		Block.all = new Block[level.blockrows][level.blockcolumns];
		// scale Blocks to max?
		if (true){
			int blockAreaX = gamePaint.getWidth() - level.blockxStart * 2;
			level.blockxSize = blockAreaX / level.blockcolumns - level.blockDistance;
		}

		for(int r=0; r<level.blockrows; r++){
			for(int c=0; c<level.blockcolumns; c++){
				Block.all[r][c] = new Block(level.blockxStart + c*level.blockxSize + c*level.blockDistance,
						level.blockyStart + r*level.blockySize + r*level.blockDistance,
						level.blockxSize,
						level.blockySize);
				try{
					Block.all[r][c].state = level.map[r][c];
				}catch(ArrayIndexOutOfBoundsException mapempty){
					// wenn level.map zu klein
					try{
						Block.all[r][c].state = 0;
					}catch(ArrayIndexOutOfBoundsException blockAraayIndex){}
					// wenn blocks zu klein

				}
			}
		}

		restart.setVisible(false); 	// restart knopf
		gameOver.setVisible(false);	// gamoversign

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

	public void gameOver(){
		Game.playSound(GameOver, -20.0f);
		bar.vx = 0;
		ball.vy = 0;
		ball.vx = 0;
		gameOver.setVisible(true);
		restart.setVisible(true);
		bar.left = false;
		bar.right= false;
		lebenlabel.setText("Leben: " + leben);
	}

	public void checkWinningGame(){
		for (Block[] row : Block.all){
			for(Block b : row){
				if (b.state != 0)
					return;
			}
		}
		// new lvl:
		levelnum++;
		startNewGame();
	}

	public Block getBlockByKords(int x, int y){
		int x2edge = x - level.blockxStart;
		int areaX = level.blockxSize + level.blockDistance;
		int column = Math.floorDiv(x2edge, areaX);
		if (x2edge - areaX * column > level.blockxSize)
			return null;

		int y2edge = y - level.blockyStart;
		int areaY = level.blockySize + level.blockDistance;
		int row = y2edge/areaY;
		if (y2edge - areaY * row > level.blockySize)
			return null;

		try{
			return Block.all[row][column];
		}catch(ArrayIndexOutOfBoundsException aioob){
			return null;
		}
	}

	public static void playSound(File sound, float volumechange){
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volumechange); // Reduce volume by 10 "volumechange"
			clip.start();
		}
		catch (Exception exc){
			exc.printStackTrace(System.out);
		}
	}
	public static void playSound(File sound){
		Game.playSound(sound, 0);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			bar.left = true;
			bar.right = false;
		}
		if (e.getKeyCode() == 39) {
			bar.left = false;
			bar.right = true;
		}
		if(e.getKeyCode() == 38 && bar.catchBall != null){
			bar.catchBall.vy = bar.catchBall.vyst;
			bar.catchBall = null;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE & restart.isVisible()){
			restart.doClick();
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
