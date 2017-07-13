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
//	Color[] state2 = new Co;
	
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
	
	public void setColors(){
		
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
			}

		}catch(java.io.FileNotFoundException fff){
			 System.out.println(
			            "Unable to open file '" + 
			            filepath + "'");        
		}
	}
	
}
