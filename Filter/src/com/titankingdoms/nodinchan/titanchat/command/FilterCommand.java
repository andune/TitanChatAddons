package com.titankingdoms.nodinchan.titanchat.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.addon.Filter;

public final class FilterCommand extends Command {
	
	private final Filter addon;
	
	public FilterCommand(Filter addon) {
		this.addon = addon;
	}
	
	@CommandID(name = "Filter", triggers = "filter", requireChannel = false)
	@CommandInfo(description = "Add words or phrases to the filter addon", usage = "filter [word/phrase]")
	public void filter(Player player, String[] args) {
		if (plugin.getPermsBridge().has(player, "TitanChat.filter")) {
			List<String> filter = (addon.getConfig().getStringList("phrases") != null) ? addon.getConfig().getStringList("phrases") : new ArrayList<String>();
			
			StringBuilder str = new StringBuilder();
			
			for (String arg : args) {
				if (str.length() > 0)
					str.append(" ");
				
				str.append(arg);
			}
			
			filter.add(str.toString());
			
			addon.getConfig().set("phrases", filter);
			addon.saveConfig();
			
			plugin.sendInfo(player, "\"" + str.toString() + "\" is now filtered");
		}
	}
}