/**
 * StockCraft
 *
 * @author Dominik Uphoff
 */

package StockCraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.nijiko.coelho.iConomy.iConomy;
import com.iConomy.system.Holdings;

public class bankmoney
{
	public static double checkmoney(Player player) {
		if (StockCraftPropertiesVar.iconomy5 == true) {
			Holdings balance = com.iConomy.iConomy.getAccount(player.getName()).getHoldings();
			return balance.balance(); 
		}
		else {
			return iConomy.getBank().getAccount(player.getName()).getBalance();
		}
		
	}
	public static void addmoney(Player player,double amount) {
		if (StockCraftPropertiesVar.iconomy5 == true) {
			Holdings balance = com.iConomy.iConomy.getAccount(player.getName()).getHoldings();
			balance.add(amount);
		}
		else {
			iConomy.getBank().getAccount(player.getName()).add(amount);			
		}
		
	}
	public static void subtractmoney(Player player,double amount) {
		double tradingfee = 0;
		if(StockCraftPropertiesVar.fee > 0 || StockCraftPropertiesVar.minimumfee > 0)
		{
			tradingfee = (amount * StockCraftPropertiesVar.fee / 100);
			if(tradingfee < StockCraftPropertiesVar.minimumfee)
			{
				tradingfee = StockCraftPropertiesVar.minimumfee;
			}
			tradingfee = tradingfee *100;
			tradingfee = Math.round(tradingfee);
			tradingfee = tradingfee /100;
			
			player.sendMessage(ChatColor.RED+"You have paid "+tradingfee+" "+getcurrency()+ " trading fee!");
		}
		if (StockCraftPropertiesVar.iconomy5 == true) {
			Holdings balance = com.iConomy.iConomy.getAccount(player.getName()).getHoldings();
			balance.subtract(amount + tradingfee);
		}
		else {
			iConomy.getBank().getAccount(player.getName()).subtract(amount + tradingfee);			
		}
		
	}
	public static String getcurrency() {
		if (StockCraftPropertiesVar.iconomy5 == true) {
			String formatted = com.iConomy.iConomy.format(0);
			String currency = formatted.substring(5, formatted.length());
			return currency;
		}
		else {
			return iConomy.getBank().getCurrency();			
		}
		
	}
}