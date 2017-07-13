import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class AdminFrame extends JFrame{

	Container c;
	int xSize, ySize, xLoc, yLoc;
	Game g;
	JComboBox<String> chooseLevel;

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


		setLayout(new GridLayout(8,2));

		chooseLevel = new JComboBox<String>();
		for(Leveldesign i :Leveldesign.listAll){
			chooseLevel.addItem(i.name);
		}
		add(chooseLevel);
		LevelListener lL = new LevelListener();
		chooseLevel.addItemListener(lL);
		
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
}