package com.titankingdoms.nodinchan.titanchat.support;

import org.bukkit.entity.Player;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class CraftIRC2Support extends TCSupport {
	
	protected CraftIRC craftIRC;
	
	public CraftIRC2Support(TitanChat plugin) {
		super(plugin, "CraftIRC2Support");
	}
	
	@Override
	public void chatMade(String name, String message) {
		craftIRC.sendMessageToTag("<" + name + "> " + recolourize(message), "all");
	}

	@Override
	public String chatMade(Player player, String message) {
		return message;
	}

	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("CraftIRC")!= null && plugin.getServer().getPluginManager().getPlugin("CraftIRC").getDescription().getVersion().startsWith("2"))
			craftIRC = (CraftIRC) plugin.getServer().getPluginManager().getPlugin("CraftIRC");
	}
	
	public String recolourize(String text) {
		String recolourized = text.replaceAll("(&([a-f0-9A-F|kK]))", "§");
		return recolourized;
	}
}
