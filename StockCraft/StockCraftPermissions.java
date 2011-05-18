/**
 * StockCraft
 *
 * @author Dominik Uphoff
 */

package StockCraft;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijikokun.bukkit.Permissions.Permissions;

public class StockCraftPermissions {
	private static Permissions permissionsPlugin;
    public static boolean permissionsEnabled = false;
    private static volatile StockCraftPermissions instance;
    
    public static void initialize(Server server) {
        Plugin test = server.getPluginManager().getPlugin("Permissions");
        if (test != null) {
            Logger log = Logger.getLogger("Minecraft");
            permissionsPlugin = ((Permissions) test);
            permissionsEnabled = true;
            log.log(Level.INFO, "[StockCraft] Permissions enabled.");
        } else {
            Logger log = Logger.getLogger("Minecraft");
            log.log(Level.SEVERE, "[StockCraft] Permissions isn't loaded, there are no restrictions.");
        }
    }
    private boolean permission(Player player, String string) {
        return Permissions.Security.permission(player, string);  
    }
    public static Permissions getPermissionsPlugin() {
		return permissionsPlugin;
	}
    public static StockCraftPermissions getInstance() {
    	if (instance == null) {
    	instance = new StockCraftPermissions();
    	}
    	return instance;
    }
    
    public boolean addid(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.addid");
        } else {
            return true;
        }
    }
    public boolean removeid(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.removeid");
        } else {
            return true;
        }
    }
    
    public boolean course(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.commands.course");
        } else {
            return true;
        }
    }
    public boolean stockbuy(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.commands.stockbuy");
        } else {
            return true;
        }
    }
    public boolean stocksell(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.commands.stocksell");
        } else {
            return true;
        }
    }
    public boolean stocktop(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.commands.stocktop");
        } else {
            return true;
        }
    }
    public boolean stocks(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.commands.stocks");
        } else {
            return true;
        }
    }
    public boolean ids(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.commands.ids");
        } else {
            return true;
        }
    }
    public boolean stockhelp(Player player) {
        if (permissionsEnabled) {
            return permission(player, "stockcraft.commands.stockhelp");
        } else {
            return true;
        }
    }
	
	
}