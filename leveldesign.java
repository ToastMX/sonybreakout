/**
 * 
 */

/**
 * @author paul
 *
 */

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;



public class Leveldesign {
	
	int id;
	int[][] map;
	String name;
	String blockposition;
	int blockrows;
	int blockcolumns;
	int blockxStart;
	int blockyStart;
	int blockxSize;
	int blockySize;
	int blockDistance;
	int ballXSize;
	int ballYSize;

	int colors[][];
	Color stateColors[];
	
	int startAnimationClock = 1000; // Frames startanimation
	
	
	public Leveldesign(int lvlNum){
		System.out.println("levelconst");
	}
	
	public void setDefaults(){
		
		name 			= name 			!= null ? name : "";
		blockposition 	= blockposition != null ? blockposition : "max";
		
		blockrows 		= blockrows != 0 		? blockrows 	: 7;
		blockcolumns 	= blockcolumns != 0 	? blockcolumns 	: 17;
		blockxStart 	= blockxStart != 0 		? blockxStart 	: 100;
		blockyStart 	= blockyStart != 0 		? blockyStart 	: 100;
		blockxSize 		= blockxSize != 0 		? blockxSize 	: 60;
		blockySize 		= blockySize != 0 		? blockySize 	: 25;
		blockDistance 	= blockDistance != 0 	? blockDistance : 6;
		ballXSize		= ballXSize != 0 		? ballXSize 	: 26;
		ballYSize		= ballYSize != 0 		? ballYSize 	: 26;
		
		
		
	}
	
	
	public static List<Leveldesign> listAll;
	
	public static void readFile(String filepath){
		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			Type type = new TypeToken<List<Leveldesign>>(){}.getType();
			listAll = gson.fromJson(br, type);

			for (Leveldesign l : listAll){
				l.setDefaults();
				l.setColors();
			}

		}catch(java.io.FileNotFoundException fff){
			 System.out.println(
			            "Unable to open file '" + 
			            filepath + "'");        
		}
	}
	
	public void setColors(){
		stateColors = new Color[4];
		// default COlors
		stateColors[0] = new Color (216,191,216); // BackgroundColor
		stateColors[1] = Color.BLACK;
		stateColors[2] = new Color (109, 7, 7);
		stateColors[3] = new Color (226, 162, 11);
		
		if(colors != null){
			for(int state=0; state<colors.length; state++){
				if (colors[state] != null){
					int r = colors[state][0]; 
					int g = colors[state][1]; 
					int b = colors[state][2]; 
					stateColors[state] = new Color(r,g,b);
					System.out.println(state);
				}
			}
				
		}
	}
	
}
