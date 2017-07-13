import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class AdminFrame extends JFrame{

	Container c;
	int xSize, ySize, xLoc, yLoc;
	Game g;
	JComboBox<String> chooseLevel;
	JButton ballStop;

	public AdminFrame(Game game){
		g = game;
		c = getContentPane();

		xSize = g.xLoc;
		ySize = g.ySize;
		xLoc = 0;
		yLoc = g.yLoc;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(xSize, ySize);
		setTitle("Admin");
		setLocation(xLoc, yLoc);

		setLayout(new GridLayout(5,1));

		chooseLevel = new JComboBox<String>();
		add(chooseLevel);
		for(Leveldesign i :Leveldesign.listAll){
			chooseLevel.addItem(i.name);
		}
		LevelListener lL = new LevelListener();
		chooseLevel.addItemListener(lL);

		ballStop = new JButton("Stop Ball");
		add(ballStop);
		BallStopListener bSL = new BallStopListener();
		ballStop.addActionListener(bSL);

		AdminKeyListener aKL = new AdminKeyListener();
		g.addKeyListener(aKL);

		setVisible(true);
	}

	class LevelListener implements ItemListener{
		public void itemStateChanged(ItemEvent iE) {
			for(Leveldesign i :Leveldesign.listAll){
				if(i.name == iE.getItem()){
					g.levelnum = i.id;
					g.startNewGame();
				}
			}
		}
	}
	
	class BallStopListener implements ActionListener{
		boolean stopped = false;
		int saveX, saveY;
		public void actionPerformed(ActionEvent aE) {

			if(!stopped & (g.ball.vx != 0 | g.ball.vy != 0)){
				saveX = g.ball.vx;
				saveY = g.ball.vy;
				g.ball.vx = 0;
				g.ball.vy = 0;
				//System.out.println("saveX" + saveX);
				ballStop.setText("Restart Ball");
				stopped = true;
			}else if (stopped 
					& (g.ball.south.y < g.bar.downleft.y)
					& !(g.bar.upleft.x <= g.ball.south.x
					& g.bar.upright.x >= g.ball.south.x 
					& g.bar.y() <= g.ball.south.y 
					& g.bar.y() + g.bar.ySize >= g.ball.south.y)){
				g.ball.vx = saveX;
				g.ball.vy = saveY;
				ballStop.setText("Stop Ball");
				stopped = false;
			}
		}
	}

	class AdminKeyListener implements KeyListener{
		boolean released = true;
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 83 & released) {
				ballStop.doClick();
				released = false;
			}
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == 83 & !released) {
				released = true;
			}
		}
		public void keyTyped(KeyEvent e) {}
	}
}