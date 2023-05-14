package kit.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import bukkit.exodusprison.spurkle.Main;
import util.Util;

public class RedeemHandler {
	private static String checkURL = "http://78.47.233.58/fbvotevGefxFG/vote.php?action=check&code=";
	private static List<Player> pending_list = new ArrayList<Player>();
	
	public static void attemptActivate(final Player p, final String code){
		
		if(!pending_list.contains(p)) pending_list.add(p);
		else return; 
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), new Runnable(){
			@Override
            public void run() {
		    	URL url;
		        InputStream is = null;
		        BufferedReader br;
		        String line;
		        try {
		        	url = new URL(checkURL + code);
		            is = url.openStream();  // throws an IOException
		            br = new BufferedReader(new InputStreamReader(is));
						while ((line = br.readLine()) != null)
						    codeStatusReceived(p, code, line);

					} catch (IOException e) {
						e.printStackTrace();
					}
			}
        });
	}
	
	
	public static void codeStatusReceived(Player p, String code, String status){
		
		if(pending_list.contains(p)) pending_list.remove(p);
		else return;
		
			switch(status){
			
			case "0": 
				p.sendMessage(ChatColor.RED + "This code does not exist in the database.");
				break;
				
			case "1": 

				p.sendMessage(ChatColor.GREEN + "Code successfully redeemed! Enjoy. :)");
				
				Util.globalMessage("§ePlayer §6"+p.getName() + " §eliked our Facebook page and received a free kit!"
						+ " §eType §a/facebook §eto like our page as well :)");
				
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + p.getName() + " add exodus.fb");

				break;
				
			case "2": 
				p.sendMessage(ChatColor.RED + "This code has been already activated.");
				break;
			}
				
		
		
	}
	
}
