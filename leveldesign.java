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
	public int blocksize;
	public String blockposition;
	public String name;
	public int blockrows;
	public int blockcolumns;
	public int blockxStart;
	public int blockyStart;
	public int blockxSize;
	public int blockySize;
	public int blockDistance;
	
	
	public Leveldesign(int lvlNum){
		
	}
	
	public static List<Leveldesign> listAll;
	
	public static void readFile(String filepath){
		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			Type type = new TypeToken<List<Leveldesign>>(){}.getType();
			listAll = gson.fromJson(br, type);
			System.out.println(listAll.get(0).name);
		}catch(java.io.FileNotFoundException fff){
			 System.out.println(
			            "Unable to open file '" + 
			            filepath + "'");        
		}
	}
	
}
