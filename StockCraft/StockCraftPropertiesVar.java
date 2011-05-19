/**
 * StockCraft
 *
 * @author Dominik Uphoff
 */

package StockCraft;

public class StockCraftPropertiesVar {

	public static String url;
	public static String user;
	public static String pass;
	public static Boolean perm;
	public static Boolean shorten;
	public static Boolean iconomy5;
	public static Double fee;
	public static Double minimumfee;
	private static String purl = "jdbc:mysql:";
	
	
	static StockCraftProperties properties = new StockCraftProperties();
	
	public static void loadMain(){
		properties.load();
		url = purl + properties.getString("url","//localhost:3306/minecraft");
		user = properties.getString("user","root");
		pass = properties.getString("password", "");
		perm = properties.getBoolean("detailedpermissions",false);
		shorten = properties.getBoolean("shorten",false);
		iconomy5 = properties.getBoolean("iconomy5",true);
		fee = properties.getDouble("fee",0);
		minimumfee = properties.getDouble("minimumfee", 0);
		
	}
}