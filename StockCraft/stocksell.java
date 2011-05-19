/**
 * StockCraft
 *
 * @author Dominik Uphoff
 */

package StockCraft;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Statement;

public class stocksell
 {
	public static void stocksellcommand(Player player,String [] split) throws SQLException
	{
		if(StockCraftPropertiesVar.perm == false ||  StockCraftPermissions.getInstance().stocksell(player))
		{
			String amount = "1";
			String idname = String.valueOf(split[1]);
			String id = StockCraftCommands.idchange(idname);
			String[] gtext = StockCraftDatabase.getcourse(id);
			String course = gtext[1];
			if(split.length > 2){amount = split[2];}
			float fcourse = Float.valueOf(course);
			int iamount = Integer.valueOf(amount);			
			float sumget = (iamount * fcourse);
			float allprofit = 0;
			float price = (iamount * fcourse);
			
			Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
			if(statement != null && id!=null)
			{
				ResultSet resultset = null;
				int amountof = 0;
				float sumpaid = 0;
				try{
					String sql = "SELECT amount,sumpaid FROM stocks WHERE name ='"+player.getName()+"' AND stockname='"+idname+"'";
					resultset = statement.executeQuery(sql);
					while (resultset.next()) {
						sumpaid = Float.valueOf(resultset.getString("sumpaid"));
						amountof = Integer.valueOf(resultset.getString("amount"));							
					}
					if(amountof < 0 && price <= bankmoney.checkmoney(player)){
						if(StockCraftPropertiesVar.shorten == true) {								
							sql = "UPDATE stocks SET amount ="+(amountof - iamount)+", sumpaid ="+(sumpaid + price)+" WHERE name ='"+player.getName()+"' AND stockname ='"+idname+"'";
							statement.execute(sql);
							player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks sold (shorted) -> "+price+" "+bankmoney.getcurrency());
							sumget = -sumget;
							bankmoney.addmoney(player, sumget);
						}
						else{
							player.sendMessage(ChatColor.RED+"Shorten is not allowed!");
						}
					}
					else if(amountof < 0 && price > bankmoney.checkmoney(player)){
						player.sendMessage(ChatColor.RED+"Not enough money! You need "+(iamount * fcourse)+" "+bankmoney.getcurrency()+"! You have "+bankmoney.checkmoney(player)+" "+bankmoney.getcurrency()+"!");
					}
					else if(amountof == 0){
						if(StockCraftPropertiesVar.shorten == true ) {
							if(price <= bankmoney.checkmoney(player))
							{
								sql = "INSERT INTO stocks (name,stockname,sumpaid,amount) VALUES('"+player.getName()+"','"+idname+"',"+price+","+ -iamount+")";
								statement.execute(sql);
								player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks sold (shorted) -> "+price+" "+bankmoney.getcurrency());
								sumget = -sumget;
								bankmoney.addmoney(player, sumget);
							}
							else if(amountof == 0 && price > bankmoney.checkmoney(player)){
								player.sendMessage(ChatColor.RED+"Not enough money! You need "+(iamount * fcourse)+" "+bankmoney.getcurrency()+"! You have "+bankmoney.checkmoney(player)+" "+bankmoney.getcurrency()+"!");
							}
						}
						else{
							player.sendMessage(ChatColor.RED+"You don't have stocks of "+idname);
						}							
					}
					else{
						if(amountof <= iamount)
						{
							if(amountof < iamount)
							{
								player.sendMessage(ChatColor.RED+"You don't have "+iamount+" stocks of "+idname+"! You have sold "+amountof+" stocks!");
								iamount = amountof;
								sumget = (iamount * fcourse);
							}
							sql = "DELETE FROM stocks WHERE name ='"+player.getName()+"' AND stockname ='"+idname+"'";
						}
						else{
							sql = "UPDATE stocks SET amount ="+(amountof - iamount)+", sumpaid ="+(sumpaid - (sumpaid/amountof)*iamount)+" WHERE name ='"+player.getName()+"' AND stockname ='"+idname+"'";
						}	
						statement.execute(sql);
						float profit = sumget -(sumpaid/amountof*iamount);
						player.sendMessage(ChatColor.GOLD+"You got "+profit+" "+bankmoney.getcurrency()+" profit!");
						sql = "SELECT profit FROM stockstats WHERE name ='"+player.getName()+"'";
						resultset = statement.executeQuery(sql);
						while (resultset.next()) {
							allprofit = Float.valueOf(resultset.getString("profit"));							
						}
						sql = "SELECT name FROM stockstats WHERE name ='"+player.getName()+"'";
						resultset = statement.executeQuery(sql);
						String name = null;
						while (resultset.next()) {
							name = resultset.getString("name");							
						}
						if(name!=null){
							sql = "UPDATE stockstats SET profit="+(allprofit+profit)+" WHERE name ='"+player.getName()+"'";
						}
						else {
							sql = "INSERT INTO stockstats (name,profit) VALUES ('"+player.getName()+"',"+profit+")";
						}			
						statement.execute(sql);
						bankmoney.addmoney(player, sumget);
						player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks sold -> "+sumget+" "+bankmoney.getcurrency());
						
					}
					
				}
				catch(Exception e){
					e.printStackTrace();					
				}
				
			}
			else{
				if(id == null){
					player.sendMessage(ChatColor.RED+"There is no id named "+idname+"!");
				}					
			}
		}
		else{
			player.sendMessage(ChatColor.RED+"You don't have permission!");
		}
	}
}