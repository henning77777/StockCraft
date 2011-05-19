/**
 * StockCraft
 *
 * @author Dominik Uphoff
 */

package StockCraft;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import com.mysql.jdbc.Statement;
import com.nijiko.Messaging;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class StockCraft extends JavaPlugin {
	private final StockCraftPlayerListener playerListener = new StockCraftPlayerListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public PermissionHandler PermissionsHandler = null;
    private Permissions permissions;
    private final String name = "StockCraft";
    public static final Logger log = Logger.getLogger("Minecraft");
    public void onEnable() {
    	StockCraftPropertiesVar.loadMain();
    	
    	StockCraftDatabase.connecting();
    	Statement statement = null;
 
		if(statement != null)
		{
			try {
				statement.execute("SELECT * FROM stocks");
				statement.execute("SELECT * FROM stockstats");
				statement.execute("SELECT * FROM idtable");
			} catch (SQLException e) {
				try {
					statement.execute("CREATE TABLE stocks (name char(50),stockname char(50), sumpaid float, amount int)");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					statement.execute("CREATE TABLE stockstats (name char(50), profit float)");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					statement.execute("CREATE TABLE idtable (longid char(50), shortid char(50))");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				e.printStackTrace();
			}
		}
        
        
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Normal, this);

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        StockCraftPermissions.initialize(getServer());
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void setupPermissions() {
    	Plugin test = getServer().getPluginManager().getPlugin("Permissions");
    	if(PermissionsHandler == null) {
    	    if(test != null) {
    		PermissionsHandler = ((Permissions)test).getHandler();
    	    } else {
    		log.info(Messaging.bracketize(name) + " Permission system not enabled. Disabling plugin.");
    		getServer().getPluginManager().disablePlugin(this);
    	    }
    	}
    }
    public void onDisable() {
        // TODO: Place any custom disable code here

        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        //System.out.println("Goodbye world!");
    }
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
	 public Permissions getPermissions() {
	    return permissions;
	 }
}
