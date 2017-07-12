/**
 * 
 */

/**
 * @author paul
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;



public class Leveldesign {
	
	public int id;
	public int[][] map;
	public String name;
	public String blockposition;
	public int blockrows;
	public int blockcolumns;
	public int blockxStart;
	public int blockyStart;
	public int blockxSize;
	public int blockySize;
	public int blockDistance;
	public int ballXSize;
	public int ballYSize;

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
			}

		}catch(java.io.FileNotFoundException fff){
			 System.out.println(
			            "Unable to open file '" + 
			            filepath + "'");        
		}
	}
	
}
