import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class AdminFrame extends JFrame{

	Game g;
	Container c;
	int xSize, ySize, xLoc, yLoc;
	
	JComboBox<String> chooseLevel;
	JButton restart;
	JToggleButton ballPredict, ballStop;
	
	LevelListener lL;
	BallStopListener bSL;
	BallPredictListener bPL;
	RestartListener rL;
	AdminKeyListener aKL;
	

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
		lL = new LevelListener();
		chooseLevel.addItemListener(lL);

		ballStop = new JToggleButton("Stop Ball");
		add(ballStop);
		bSL = new BallStopListener();
		ballStop.addActionListener(bSL);

		ballPredict = new JToggleButton("Balllinie einblenden");
		add(ballPredict);
		bPL = new BallPredictListener();
		ballPredict.addActionListener(bPL);
		
		restart = new JButton("Restart");
		add(restart);
		rL = new RestartListener();
		restart.addActionListener(rL);
		

		aKL = new AdminKeyListener();
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
		int saveX, saveY;
		public void actionPerformed(ActionEvent aE) {

			if(ballStop.isSelected() & (g.ball.vx != 0 | g.ball.vy != 0)){
				saveX = g.ball.vx;
				saveY = g.ball.vy;
				
				g.ball.vx = 0;
				g.ball.vy = 0;
				
				g.gamePaint.adminlineDirX = saveX;
				g.gamePaint.adminlineDirY = saveY;
				g.gamePaint.adminStop = ballStop.isSelected();
				ballStop.setText("Restart Ball");
			}else if (!ballStop.isSelected() 
					& (g.ball.south.y < g.bar.downleft.y)
					& !(g.bar.upleft.x <= g.ball.south.x
					& g.bar.upright.x >= g.ball.south.x 
					& g.bar.y() <= g.ball.south.y 
					& g.bar.y() + g.bar.ySize >= g.ball.south.y)){
				
				g.ball.vx = saveX;
				g.ball.vy = saveY;
				g.gamePaint.adminStop = ballStop.isSelected();
				ballStop.setText("Stop Ball");
			}
		}
	}

	class AdminKeyListener implements KeyListener{
		boolean released83 = true;
		boolean released76 = true;
		boolean released82 = true;
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 83 & released83) {
				ballStop.doClick();
				released83 = false;
			}
			if (e.getKeyCode() == 76 & released76){
				ballPredict.doClick();
				released76 = false;
			}
			if (e.getKeyCode() == 82 & released82){
				restart.doClick();
				released82 = false;
			}

			
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == 83 & !released83) {
				released83 = true;
			}
			if (e.getKeyCode() == 76 & !released76){
				released76 = true;
			}
			if (e.getKeyCode() == 82 & !released82){
				released82 = true;
			}
		}
		public void keyTyped(KeyEvent e) {}
	}

	class BallPredictListener implements ActionListener{

		public void actionPerformed(ActionEvent aE) {
			if(!ballPredict.isSelected()){
				g.gamePaint.directionLine = true;
				ballPredict.setText("Balllinie ausblenden");
			}else{
				g.gamePaint.directionLine = false;
				ballPredict.setText("Balllinie einblenden");
			}
		}
	}

	class RestartListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			g.startNewGame();
		}
	}
}