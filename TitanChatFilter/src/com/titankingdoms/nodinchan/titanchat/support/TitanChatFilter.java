package com.titankingdoms.nodinchan.titanchat.support;

import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class TitanChatFilter extends Support {

	public TitanChatFilter(TitanChat plugin) {
		super(plugin, "TitanChatFilter");
	}
	
	@Override
	public void chatMade(String name, String message) {}
	
	@Override
	public String chatMade(Player player, String message) {
		String filtered = message;
		String censor = getConfig().getString("censor");
		
		if (getConfig().getStringList("phrases") != null) {
			for (String word : getConfig().getStringList("phrases")) {
				if (message.toLowerCase().contains(word.toLowerCase())) {
					filtered = message.replaceAll("(?i)" + word, word.replaceAll("[A-Za-z]", censor));
				}
			}
		}
		
		return filtered;
	}

	@Override
	public void init() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		plugin.registerCommand(new FilterCommand(this));
	}
	
	public TitanChat getPlugin() {
		return plugin;
	}
}