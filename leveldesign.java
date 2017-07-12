/**
 * 
 */

/**
 * @author paul
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;#

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;



public class leveldesign {
	
	public static String JSON_PATH = "/Users/dawid/Workspace/Test/test.json";
	
	public leveldesign(int lvlNum){
		
	}
	
	private void readFile(){
		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(JSON_PATH));
			Type type = new TypeToken<List<leveldesign>>(){}.getType();
			List<leveldesign> models = gson.fromJson(br, type);
		}catch(java.io.FileNotFoundException fff){
			
		}
		
	}
	
	
	
	static String lvl1 =  "11111111111111111,"
						+ "11111111111111111,"
						+ "12222222222222221,"
						+ "12333333333333321,"
						+ "12222222222222221,"
						+ "11111111111111111";
	
	static String lvl2 =  "11111,"
			+ "11111,"
			+ "12221,"
			+ "12331,"
			+ "12221,"
			+ "11111";
	
}
