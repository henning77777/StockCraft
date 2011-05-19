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

public class ids
 {
	public static void idscommand(Player player,String [] split) throws SQLException
	{
		if(StockCraftPropertiesVar.perm == false ||  StockCraftPermissions.getInstance().ids(player))
		{
			Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
			if(statement != null)
			{
				ResultSet resultset = null;
				String id = null;
				String shortid = null;
				String course = null;
				String sql = "SELECT longid,shortid FROM idtable";
				try {
					resultset = statement.executeQuery(sql);						
					try {
						Boolean idsadded = false;
						int l = 0;
						while (resultset.next()) {
							l = l+1;
						}
						resultset.beforeFirst();
						String[] idlist = new String[l];							
						for (int i = 0;resultset.next();i++) {
							id = resultset.getString("longid");								
							if(id != null){
								idsadded = true; 
							}
							shortid = resultset.getString("shortid");
							idlist[i] = shortid;
						}
						if(idsadded == false) {
							player.sendMessage(ChatColor.RED+"There are no available stocks -> ask admin to add some!");
						}
						else{
							int start = 0;
							int end = 9;
							int maxpage = 0;
							maxpage = (idlist.length) / 10 + 1;
							boolean symbol = false;
							boolean page = false;
							if(split.length > 1)
							{
								
								if(split[1].matches("\\d*"))
								{
									start = 10 * (Integer.parseInt(split[1])-1);
									end = 10 * (Integer.parseInt(split[1])-1) + 9;
									if(Integer.parseInt(split[1]) > maxpage)
									{
										player.sendMessage(ChatColor.RED+"This page does not exist! There are only "+ChatColor.LIGHT_PURPLE+maxpage+ChatColor.RED+" pages!");
									}
									else
									{
										player.sendMessage(ChatColor.GREEN+"--- stock ids -> page "+ChatColor.LIGHT_PURPLE+Integer.parseInt(split[1])+ChatColor.GREEN+" / "+maxpage+" ---");
									}
									page = true;
								}
								else if(split[1].equals("symbol") || split[1].equals("s"))
								{
									symbol = true;
									if(split.length > 2)
									{
										if(split[2].matches("\\d*"))
										{
											start = 10 * (Integer.parseInt(split[2])-1);
											end = 10 * (Integer.parseInt(split[2])-1) + 9;										
											if(Integer.parseInt(split[2]) > maxpage)
											{
												player.sendMessage(ChatColor.RED+"This page does not exist! There are only "+ChatColor.LIGHT_PURPLE+maxpage+ChatColor.RED+" pages!");
											}
											else
											{
												player.sendMessage(ChatColor.GREEN+"--- stock ids -> page "+ChatColor.LIGHT_PURPLE+Integer.parseInt(split[2])+ChatColor.GREEN+" / "+maxpage+" ---");
											}
											page = true;
										}
									}
								}
							}
							if(!page)
							{
								player.sendMessage(ChatColor.GREEN+"--- stock ids -> page "+ChatColor.LIGHT_PURPLE+"1"+ChatColor.GREEN+" / "+maxpage+" ---");
							}
							String[] gtext = StockCraftDatabase.getcourse(idlist);
							for(int i = start;i<idlist.length && i <= end;i++)
							{
								course = gtext[i];
								if(symbol)
								{
									player.sendMessage(ChatColor.AQUA+idlist[i]+ChatColor.YELLOW+" : "+course);
								}							
								else
								{
									player.sendMessage(ChatColor.AQUA+StockCraftCommands.idback(idlist[i])+ChatColor.YELLOW+" : "+course);
								}
							}
						}
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}				
			}	
		}
		else{
			player.sendMessage(ChatColor.RED+"You don't have permission!");
		}
		
	}
 }