package com.titankingdoms.nodinchan.titanchat.channel;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class LocalChannel extends CustomChannel {
	
	public LocalChannel() {
		super("Local");
		init(this);
	}
	
	@Override
	public String format(Player player, String format) {
		String tag = getConfig().getString("tag");
		String colour = getConfig().getString("chat-display-colour");
		
		format = format.replace("%tag", colourise(tag));
		format = format.replace("%player", colourise(colour + player.getDisplayName()));
		format = format.replace("%message", colourise(colour) + "%message");
		
		return format;
	}
	
	@Override
	public String getFormat() {
		return getConfig().getString("format");
	}
	
	@Override
	public void init() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
	public String sendMessage(Player sender, String message) {
		int radius = getConfig().getInt("radius");
		
		List<Player> recipants = new ArrayList<Player>();
		recipants.add(sender);
		
		for (Entity entity : sender.getNearbyEntities(radius, radius, radius)) {
			if (entity instanceof Player)
				recipants.add((Player) entity);
		}
		
		boolean colourise= getConfig().getBoolean("colour-code");
		message = (colourise || plugin.getPermsBridge().has(sender, "TitanChat.colours")) ? colourise(message) : decolourise(message);
		
		String sent = super.sendMessage(sender, recipants, message);
		
		if (recipants.size() == 1 || sent.equals(""))
			sender.sendMessage(ChatColor.GOLD + "Nobody hears you...");
		
		return sent;
	}
}