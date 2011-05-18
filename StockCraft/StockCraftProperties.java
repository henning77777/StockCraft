/**
 * StockCraft
 *
 * @author Dominik Uphoff
 */

package StockCraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class StockCraftProperties extends Properties{	
	private static final long serialVersionUID = -922210605847273904L;
	private String fileName = "plugins/StockCraft/StockCraft.cfg";
	
	public void load(){
		File file = new File(fileName);
		if(file.exists()) {
			try  {
				load(new FileInputStream(fileName));
			} catch (IOException ex) {
				
			}
		}
	}
	public String getString(String key, String value){
		if(containsKey(key)){
			return getProperty(key);
		}
		put(key, value);
		return value;
	}
	public Boolean getBoolean(String key, boolean value) {
		if (containsKey(key)) {
			String boolString = getProperty(key);
			return (boolString.length() > 0)
					&& (boolString.toLowerCase().charAt(0) == 't');
		}
		put(key, value ? "true" : "false");
		return value;
	}
<<<<<<< HEAD
	public double getDouble(String key, double value) {
		if (containsKey(key)) {
			return Double.parseDouble(getProperty(key));
		}

		put(key, String.valueOf(value));
		return value;
	}
=======
>>>>>>> d687cfc... Version 0.2
	
	
}