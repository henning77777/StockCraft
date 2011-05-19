/**
 * StockCraft
 *
 * @author Dominik Uphoff
 */

package StockCraft;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import com.mysql.jdbc.Statement;
import com.nijiko.coelho.iConomy.iConomy;


public class StockCraftCommands {
	
	Logger log = Logger.getLogger("Minecraft");	
	private static StockCraft plugin;
	public StockCraftCommands(StockCraft instance) {
    	plugin = instance;
    }
	private static volatile StockCraftCommands instance;
	public static StockCraftCommands  getInstance() {
    	if (instance == null) {
    		instance = new StockCraftCommands(plugin);
    	}
    	return instance;
    }
	
	public static String idchange(String longid) throws SQLException{
		String shortid = null;
		Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
		if(statement != null){
			ResultSet resultset = null;
			String sql = "SELECT shortid FROM idtable WHERE longid ='"+longid+"'";
			try {
				resultset = statement.executeQuery(sql);
				while (resultset.next()) {
					shortid = resultset.getString("shortid");					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return shortid;
	}
	public String[] loadidlist(Player player,String loc)
	{
		String[] texti = null;
		try{
    		FileReader readfile = new FileReader(loc);
            BufferedReader rf = new BufferedReader(readfile);
            StringBuffer stringBuffer = new StringBuffer();
            String text = "";    
            while((text = rf.readLine()) != null){
            	stringBuffer.append(text);            	
            }
            text = String.valueOf(stringBuffer);
            texti = text.split(":"); 
               
            rf.close();
    	}
    	catch(Exception e){
        	log.log(Level.SEVERE, "There is no file "+loc+"!", e);        	    
        	}
    	return texti;
	}
	
	public void writetxt(String loc,PlayerChatEvent event)
    {
    	Player player = event.getPlayer();
    	try{
    		FileReader readfile = new FileReader(loc);
            BufferedReader rf = new BufferedReader(readfile);
            String text = "";
            String color = "WHITE";

            while((text = rf.readLine()) != null)
            {
            	String[] texti = text.split(":");
            	for(int i = 0;i<texti.length;i++)
                {
            		if(texti[i].equals("WHITE")){color = "WHITE";}
            		else if(texti[i].equals("AQUA")){color = "AQUA";}
            		else if(texti[i].equals("BLACK")){color = "BLACK";}
            		else if(texti[i].equals("BLUE")){color = "BLUE";}
            		else if(texti[i].equals("DARK_AQUA")){color = "DARK_AQUA";}
            		else if(texti[i].equals("DARK_BLUE")){color = "DARK_BLUE";}
            		else if(texti[i].equals("DARK_GRAY")){color = "DARK_GRAY";}
            		else if(texti[i].equals("DARK_GREEN")){color = "DARK_GREEN";}
            		else if(texti[i].equals("DARK_PURPLE")){color = "DARK_PURPLE";}
            		else if(texti[i].equals("DARK_RED")){color = "DARK_RED";}
            		else if(texti[i].equals("GOLD")){color = "GOLD";}
            		else if(texti[i].equals("GRAY")){color = "GRAY";}
            		else if(texti[i].equals("GREEN")){color = "GREEN";}
            		else if(texti[i].equals("LIGHT_PURPLE")){color = "LIGHT_PURPLE";}
            		else if(texti[i].equals("RED")){color = "RED";}
            		else if(texti[i].equals("YELLOW")){color = "YELLOW";}                		
            		
            		else{
            			if(color.equals("WHITE")){player.sendMessage(ChatColor.WHITE+texti[i]);}
            			if(color.equals("AQUA")){player.sendMessage(ChatColor.AQUA+texti[i]);}
            			if(color.equals("BLACK")){player.sendMessage(ChatColor.BLACK+texti[i]);}
            			if(color.equals("BLUE")){player.sendMessage(ChatColor.BLUE+texti[i]);}
            			if(color.equals("DARK_AQUA")){player.sendMessage(ChatColor.DARK_AQUA+texti[i]);}
            			if(color.equals("DARK_BLUE")){player.sendMessage(ChatColor.DARK_BLUE+texti[i]);}
            			if(color.equals("DARK_GRAY")){player.sendMessage(ChatColor.DARK_GRAY+texti[i]);}
            			if(color.equals("DARK_GREEN")){player.sendMessage(ChatColor.DARK_GREEN+texti[i]);}
            			if(color.equals("DARK_PURPLE")){player.sendMessage(ChatColor.DARK_PURPLE+texti[i]);}
            			if(color.equals("DARK_RED")){player.sendMessage(ChatColor.DARK_RED+texti[i]);}
            			if(color.equals("GOLD")){player.sendMessage(ChatColor.GOLD+texti[i]);}
            			if(color.equals("GRAY")){player.sendMessage(ChatColor.GRAY+texti[i]);}
            			if(color.equals("GREEN")){player.sendMessage(ChatColor.GREEN+texti[i]);}
            			if(color.equals("LIGHT_PURPLE")){player.sendMessage(ChatColor.LIGHT_PURPLE+texti[i]);}
            			if(color.equals("RED")){player.sendMessage(ChatColor.RED+texti[i]);}
            			if(color.equals("YELLOW")){player.sendMessage(ChatColor.YELLOW+texti[i]);}
            		}
            		
            	}
            }
            rf.close();
    	}
    	catch(Exception e){
        	log.log(Level.SEVERE, "There is no file "+loc+"!", e);        	    
        	}
        }
	public static String idback(String shortid) throws SQLException{
		String longid = null;
		Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
		if(statement != null){
			ResultSet resultset = null;
			String sql = "SELECT longid FROM idtable WHERE shortid ='"+shortid+"'";
			try {
				resultset = statement.executeQuery(sql);
				while (resultset.next()) {
					longid = resultset.getString("longid");		
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return longid;
	}
	
	
	
	public void infosystem(String[] split, Player player, PlayerChatEvent event) throws SQLException{
		
		if(split[0].equalsIgnoreCase("/course")) {
			event.setCancelled(true); 
			if(StockCraftPropertiesVar.perm == false ||  StockCraftPermissions.getInstance().course(player))
			{
				String idname = String.valueOf(split[1]);
				String id = idchange(idname);
				String[] gtext = StockCraftDatabase.getcourse(id);
				String course = gtext[1];
				String lastday = gtext[2];
				String percent = gtext[3];
				String days50 = gtext[4];
				player.sendMessage(ChatColor.DARK_AQUA+"Stock: "+idname+ChatColor.BLUE+" course: "+course+ChatColor.DARK_AQUA+" end of last day: "+lastday+ChatColor.BLUE+" change in percent: "+percent+ChatColor.DARK_AQUA+" 50 days average: "+days50);
			}
			else {
				player.sendMessage(ChatColor.RED+"You don't have permission!");
			}
		}
		if(split[0].equalsIgnoreCase("/stocks")) {
			event.setCancelled(true); 
			if(StockCraftPropertiesVar.perm == false ||  StockCraftPermissions.getInstance().stocks(player))
			{
				Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
				if(statement != null)
				{
					ResultSet resultset = null;
					int amountof = 0;
					float sumpaid = 0;
					String id = null;
					String shortid = null;
					String course = null;
					String sql = "SELECT stockname,amount,sumpaid FROM stocks WHERE name ='"+player.getName()+"'";
					try {
						resultset = statement.executeQuery(sql);
						try {
							Boolean bought = false;
							int l = 0;
							while (resultset.next()) {
								l = l+1;
							}
							resultset.beforeFirst();
							String[] idlist = new String[l];
							float[] sumpaidlist = new float[l];
							int[] amountoflist = new int[l];
							for (int i = 0;resultset.next();i++) {
								id = resultset.getString("stockname");	
								sumpaidlist[i] = Float.valueOf(resultset.getString("sumpaid"));
								amountoflist[i] = Integer.valueOf(resultset.getString("amount"));	
								if(id != null){
									bought = true; 
								}
								shortid = idchange(id);
								idlist[i] = shortid;
								
								
							}
							
							if(bought== false) {
								player.sendMessage(ChatColor.RED+"You don't have stocks -> buy some first!");
							}
							else{
								String[] gtext = StockCraftDatabase.getcourse(idlist);
								float profit = 0;
								for(int i = 0;i<idlist.length;i++)
								{
									course = gtext[i];
									amountof = amountoflist[i];
									sumpaid = sumpaidlist[i];
									id = idback(idlist[i]);
									float avp = 0;
									if(amountof > 0){								
										profit = Float.valueOf(course) - (sumpaid/amountof);
										avp = (sumpaid/amountof);
									}
									else if(amountof < 0){
										profit = Float.valueOf(course) - (sumpaid/(-amountof));
										profit = -profit;
										avp = (sumpaid/-amountof);
									}
									
									if(profit > 0){
										player.sendMessage(ChatColor.YELLOW+id+ChatColor.LIGHT_PURPLE+" amount: "+amountof+ChatColor.GREEN+" average paid: "+avp+ChatColor.BLUE+" course: "+course+ChatColor.GREEN+" +"+profit);
									}
									else if (profit == 0)
									{
										player.sendMessage(ChatColor.YELLOW+id+ChatColor.LIGHT_PURPLE+" amount: "+amountof+ChatColor.GREEN+" average paid: "+avp+ChatColor.BLUE+" course: "+course+ChatColor.WHITE+" "+profit);
									}
									else if (profit < 0)
									{
										player.sendMessage(ChatColor.YELLOW+id+ChatColor.LIGHT_PURPLE+" amount: "+amountof+ChatColor.GREEN+" average paid: "+avp+ChatColor.BLUE+" course: "+course+ChatColor.RED+" "+profit);
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
		if(split[0].equalsIgnoreCase("/ids")) {
			event.setCancelled(true); 
			ids.idscommand(player, split);
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
							
							player.sendMessage(ChatColor.GREEN+"--- stock ids ---");
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
								String[] gtext = StockCraftDatabase.getcourse(idlist);
								for(int i = 0;i<idlist.length;i++)
								{
									course = gtext[i];
									player.sendMessage(ChatColor.AQUA+idback(idlist[i])+ChatColor.YELLOW+" : "+course);
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
		if(split[0].equalsIgnoreCase("/stocksell")) {
			event.setCancelled(true);
			stocksell.stocksellcommand(player, split);			
			if(StockCraftPropertiesVar.perm == false ||  StockCraftPermissions.getInstance().stocksell(player))
			{
				String amount = "1";
				String idname = String.valueOf(split[1]);
				String id = idchange(idname);
				String[] gtext = StockCraftDatabase.getcourse(id);
				String course = gtext[1];
				if(split.length > 2){amount = split[2];}
				float fcourse = Float.valueOf(course);
				int iamount = Integer.valueOf(amount);			
				float sumget = (iamount * fcourse);
				float allprofit = 0;
				float price = (iamount * fcourse);
				
				Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
				if(statement != null && id!=null && price <= iConomy.getBank().getAccount(player.getName()).getBalance())
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
						if(amountof < 0){
							if(StockCraftPropertiesVar.shorten == true) {								
								sql = "UPDATE stocks SET amount ="+(amountof - iamount)+", sumpaid ="+(sumpaid + price)+" WHERE name ='"+player.getName()+"' AND stockname ='"+idname+"'";
								statement.execute(sql);
								player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks sold (shorted) -> "+price+" "+iConomy.getBank().getCurrency());
								sumget = -sumget;
								iConomy.getBank().getAccount(player.getName()).add(sumget);
							}
							else{
								player.sendMessage(ChatColor.RED+"Shorten is not allowed!");
							}
						}
						else if(amountof == 0){
							if(StockCraftPropertiesVar.shorten == true) {
								
								sql = "INSERT INTO stocks (name,stockname,sumpaid,amount) VALUES('"+player.getName()+"','"+idname+"',"+price+","+ -iamount+")";
								statement.execute(sql);
								player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks sold (shorted) -> "+price+" "+iConomy.getBank().getCurrency());
								sumget = -sumget;
								iConomy.getBank().getAccount(player.getName()).add(sumget);
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
							player.sendMessage(ChatColor.GOLD+"You got "+profit+" "+iConomy.getBank().getCurrency()+" profit!");
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
							iConomy.getBank().getAccount(player.getName()).add(sumget);
							player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks sold -> "+sumget+" "+iConomy.getBank().getCurrency());
							
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
					else if(price > iConomy.getBank().getAccount(player.getName()).getBalance()){
						player.sendMessage(ChatColor.RED+"Not enough money! You need "+(iamount * fcourse)+" "+iConomy.getBank().getCurrency()+"! You have "+iConomy.getBank().getAccount(player.getName()).getBalance()+" "+iConomy.getBank().getCurrency()+"!");
					}
				}
			}
			else{
				player.sendMessage(ChatColor.RED+"You don't have permission!");
			}
			
		}
		
		if(split[0].equalsIgnoreCase("/stockbuy")) {
			event.setCancelled(true); 

			stockbuy.stockbuycommand(player, split);				

			if(StockCraftPropertiesVar.perm == false ||  StockCraftPermissions.getInstance().stockbuy(player))
			{
				String amount = "1";			
				String idname = String.valueOf(split[1]);
				String id = idchange(idname);
				String[] gtext = StockCraftDatabase.getcourse(id);
				String course = gtext[1];
				if(split.length > 2){amount = split[2];}
				float fcourse = Float.valueOf(course);
				int iamount = Integer.valueOf(amount);
				float sumget = (iamount * fcourse);
				float allprofit = 0;
				
				float price = (iamount * fcourse);
				
				if(id!=null)
				{
					Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
					if(statement != null)
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
							if(price <= iConomy.getBank().getAccount(player.getName()).getBalance() || amountof < 0)
							{
								if(amountof < 0){
									 if(StockCraftPropertiesVar.shorten == true)
									 {
										 if(-amountof <= iamount)
											{
												if(-amountof < iamount)
												{
													player.sendMessage(ChatColor.RED+"You don't have "+iamount+" sold (shorted) stocks of "+idname+"! You have bought "+amountof+" stocks!");
													iamount = amountof;
													sumget = (iamount * fcourse);
												}
												sql = "DELETE FROM stocks WHERE name ='"+player.getName()+"' AND stockname ='"+idname+"'";
											}
											else{
												sql = "UPDATE stocks SET amount ="+(amountof + iamount)+", sumpaid ="+(sumpaid - (sumpaid/-amountof)*iamount)+" WHERE name ='"+player.getName()+"' AND stockname ='"+idname+"'";
											}	
											statement.execute(sql);
											float profit = sumget-(sumpaid/-amountof*iamount);
											profit = -profit;
											player.sendMessage(ChatColor.GOLD+"You got "+profit+" "+iConomy.getBank().getCurrency()+" profit!");
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
											iConomy.getBank().getAccount(player.getName()).add(sumget);
											iConomy.getBank().getAccount(player.getName()).add(profit * 2);
											player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks bought (shorted) -> "+sumget+" "+iConomy.getBank().getCurrency());
									 }
									 else
									 {
										 player.sendMessage(ChatColor.RED+"Shorten is not allowed!");
									 }
									
								}
								else if(amountof == 0){
									sql = "INSERT INTO stocks (name,stockname,sumpaid,amount) VALUES('"+player.getName()+"','"+idname+"',"+price+","+amount+")";
									statement.execute(sql);
									iConomy.getBank().getAccount(player.getName()).subtract(price);
									player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks bought -> "+price+" "+iConomy.getBank().getCurrency());
								}
								else if(amountof > 0){
									sql = "UPDATE stocks SET amount ="+(amountof + iamount)+", sumpaid ="+(sumpaid + price)+" WHERE name ='"+player.getName()+"' AND stockname ='"+idname+"'";
									statement.execute(sql);
									iConomy.getBank().getAccount(player.getName()).subtract(price);
									player.sendMessage(ChatColor.GREEN+amount+" "+idname+" stocks bought -> "+price+" "+iConomy.getBank().getCurrency());
								}
							}
							else {
								player.sendMessage(ChatColor.RED+"Not enough money! You need "+(iamount * fcourse)+" "+iConomy.getBank().getCurrency()+"! You have "+iConomy.getBank().getAccount(player.getName()).getBalance()+" "+iConomy.getBank().getCurrency()+"!");
							}
							
						}
						catch(Exception e){
							e.printStackTrace();	
						}
					}
				}
				else {
					if(id == null){
						player.sendMessage(ChatColor.RED+"There is no id named "+idname+"!");
					}					
				}
			}
			else{
				player.sendMessage(ChatColor.RED+"You don't have permission!");
			}			
		}
		if(split[0].equalsIgnoreCase("/addid")) {
			event.setCancelled(true); 
			if(StockCraftPermissions.getInstance().addid(player)){		
				if(split.length > 2){
					StockCraftDatabase.idadd(player, split[1], split[2]);
				}				
				else{
					player.sendMessage(ChatColor.RED+"/addid [name] [id]");
					player.sendMessage(ChatColor.RED+"name = The name you want to use for the stock");
					player.sendMessage(ChatColor.RED+"id = The id from 'finance.yahoo.com' (BMW -> BMW.DE)");
					player.sendMessage(ChatColor.RED+"Example: /addid BMW BMW.DE");
				}
			}
			
		}
		
		if(split[0].equalsIgnoreCase("/addidlist")) {
			event.setCancelled(true); 
			if(StockCraftPermissions.getInstance().addid(player)){		
				if(split.length > 1){
					String loc = "plugins/StockCraft/" + split[1] +".txt";
					String[] list = loadidlist(player, loc);

					for(int i = 0;i<list.length;i=i+2) {
		        		StockCraftDatabase.idadd(player, list[i], list[i+1]);
		        	}
				}				
				else{
					player.sendMessage(ChatColor.RED+"/addidlist [name]");
					player.sendMessage(ChatColor.RED+"name = The name of the list in your plugins/StockCraft/ directory");
					player.sendMessage(ChatColor.RED+"If the file is named mylist.txt the name is mylist");
					player.sendMessage(ChatColor.RED+"Example: /addidlist mylist");
				}
			}
			
		}
		
		
		if(split[0].equalsIgnoreCase("/removeid")) {
			event.setCancelled(true); 
			if(StockCraftPermissions.getInstance().removeid(player)){		
				if(split.length > 1){
					Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
					if(statement != null)
					{	
						try {
							ResultSet resultset = null;
							String sql = "SELECT longid FROM idtable WHERE longid='"+split[1]+"'";
							resultset = statement.executeQuery(sql);
							String longid = "";
							while (resultset.next()) {
								longid = resultset.getString("longid");						
							}
							if(longid.toLowerCase().equals(split[1].toLowerCase())){
								sql = "SELECT name FROM stocks WHERE stockname='"+longid+"'";
								resultset = statement.executeQuery(sql);
								String name = null;
								while (resultset.next()) {
									name = resultset.getString("name");	
									player.sendMessage(ChatColor.RED+name+" has still stocks from "+longid+"!");
								}
								sql = "DELETE FROM idtable WHERE longid='"+split[1]+"'";
								statement.execute(sql);
								player.sendMessage(ChatColor.GREEN+longid+" successfully deleted!");
							}
							else{
								player.sendMessage(ChatColor.RED+longid+"There is no stock "+split[1]+"!");
							}
								
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					}
				}
				
				else{
					player.sendMessage(ChatColor.RED+"/removeid [name]");
					player.sendMessage(ChatColor.RED+"name = The name of the stock you want to remove");
					player.sendMessage(ChatColor.RED+"Example: /removeid BMW");
				}
			}
			
		}
		if(split[0].equalsIgnoreCase("/stocktop")){
    		event.setCancelled(true);
    		if(StockCraftPropertiesVar.perm == false ||  StockCraftPermissions.getInstance().stocktop(player))
			{
    			Statement statement = (Statement) StockCraftDatabase.conn.createStatement();
	    		if(statement != null)
				{	
					try {
						ResultSet resultset = null;
						String sql = "SELECT name,profit FROM stockstats ORDER BY profit DESC";
						resultset = statement.executeQuery(sql);
						String name = "";
						String profit = "";
						player.sendMessage(ChatColor.GREEN+"--- Stocks profit toplist ---");
						for(int i = 1;resultset.next() && i<11;i++) {
							name = resultset.getString("name");		
							profit = resultset.getString("profit");
							player.sendMessage(ChatColor.LIGHT_PURPLE+""+i+". "+name+" "+profit);
						}    
					} catch (SQLException e1) {
					e1.printStackTrace();
					}	
					
				}
			}
    		else{
				player.sendMessage(ChatColor.RED+"You don't have permission!");
			}
    		
    	}
		
		if(split[0].equalsIgnoreCase("/stockhelp") || split[0].equalsIgnoreCase("/stockshelp") || split[0].equalsIgnoreCase("/stockcraft")){
    		event.setCancelled(true);
    		if(StockCraftPropertiesVar.perm == false ||  StockCraftPermissions.getInstance().stockhelp(player))
			{
	    		String rulesloc = "plugins/StockCraft/stockhelp.txt";
	    		writetxt(rulesloc,event);  
			}
    		else{
				player.sendMessage(ChatColor.RED+"You don't have permission!");
			}
    	}

	}
}
