/**
 * StockCraft
 *
 * @author Dominik Uphoff
 */

package StockCraft;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;

public class StockCraftPlayerListener extends PlayerListener {
    private final StockCraft plugin;
    
    public StockCraftPlayerListener(StockCraft instance) {
        plugin = instance;
    }

    
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
    	Player player = event.getPlayer();
    	String[] split = event.getMessage().split(" ");
    	//Check if the command is an mcMMO related help command
    	try {
			StockCraftCommands.getInstance().infosystem(split, player, event);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


	public StockCraft getPlugin() {
		return plugin;
	}
    
}
