package com.titankingdoms.nodinchan.titanchat.support;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ensifera.animosity.craftirc.CraftIRC;
import com.ensifera.animosity.craftirc.EndPoint;
import com.ensifera.animosity.craftirc.RelayedMessage;
import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class CraftIRC3Support extends TCSupport implements EndPoint {
	
	protected CraftIRC craftIRC;
	
	public CraftIRC3Support(TitanChat plugin) {
		super(plugin, "CraftIRC3Support");
	}

	@Override
	public boolean adminMessageIn(RelayedMessage msg) {
		return false;
	}

	@Override
	public void chatMade(String name, String message) {
		if (craftIRC != null) {
			Player player = Bukkit.getServer().getPlayer(name);
			
			RelayedMessage chat = craftIRC.newMsg(this, null, "");
			chat.setField("message", recolourize(message));
			chat.setField("sender", player.getDisplayName());
			chat.setField("world", player.getWorld().getName());
			chat.setField("readlSender", name);
			chat.setField("prefix", plugin.getPlayerPrefix(player));
			chat.setField("suffix", plugin.getPlayerSuffix(player));
			chat.post();
		}
	}

	@Override
	public String chatMade(Player player, String message) {
		return message;
	}
	
	@Override
	public Type getType() {
		return EndPoint.Type.MINECRAFT;
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("CraftIRC") != null && plugin.getServer().getPluginManager().getPlugin("CraftIRC").getDescription().getVersion().startsWith("3"))
			craftIRC = (CraftIRC) plugin.getServer().getPluginManager().getPlugin("CraftIRC");
		
		if (craftIRC != null)
			craftIRC.registerEndPoint("titanchat", this);
	}
	
	@Override
	public List<String> listDisplayUsers() {
		return null;
	}

	@Override
	public List<String> listUsers() {
		return null;
	}

	@Override
	public void messageIn(RelayedMessage msg) {}
	
	public String recolourize(String text) {
		String recolourized = text.replaceAll("(&([a-f0-9A-F]))", "§");
		return recolourized;
	}
	
	@Override
	public boolean userMessageIn(String username, RelayedMessage msg) {
		return false;
	}
}