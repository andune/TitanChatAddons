package com.titankingdoms.nodinchan.titanchat.support;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class FilterCommand extends Command {
	
	private TitanChatFilter addon;
	
	public FilterCommand(TitanChatFilter addon) {
		super("FilterCommand");
		this.addon = addon;
	}
	
	@Override
	public boolean execute(Player player, String cmd, String[] args) {
		if (cmd.equalsIgnoreCase("filter")) {
			List<String> filter = (addon.getConfig().getStringList("phrases") != null) ? addon.getConfig().getStringList("phrases") : new ArrayList<String>();
			
			StringBuilder str = new StringBuilder();
			
			for (String arg : args) {
				if (str.length() > 0)
					str.append(" ");
				
				str.append(arg);
			}
			
			filter.add(str.toString());
			addon.getConfig().set("filter", filter);
			addon.saveConfig();
		}
		
		return false;
	}
}