package com.titankingdoms.nodinchan.titanchat.support;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.TitanChat;
import com.titankingdoms.nodinchan.titanchat.channel.Channel;

public class LocalChannel extends CustomChannel {
	
	public LocalChannel(TitanChat plugin) {
		super(plugin, "Local");
	}
	
	@Override
	public String format(Player player, String message) {
		String msg = message;
		
		String tag = plugin.getConfig().getString("tag");
		String prefix = plugin.getPlayerPrefix(player);
		String suffix = plugin.getPlayerSuffix(player);
		String colour = plugin.getConfig().getString("chat-display-colour");
		
		msg = colourise(tag + " " + colour + prefix + player.getDisplayName() + suffix + "&f: " + colour + msg);
		
		return msg;
	}
	
	@Override
	public void init() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public Channel load(Channel channel) {
		return channel;
	}

	@Override
	public boolean onJoin(Player player) {
		return true;
	}
	
	@Override
	public void onLeave(Player player) {}

	@Override
	public void sendMessage(Player player, String message) {
		int radius = getConfig().getInt("radius");
		
		List<Entity> entities = player.getNearbyEntities(radius, radius, radius);
		entities.add(player);
		
		for (Entity entity : entities) {
			if (entity instanceof Player)
				((Player) entity).sendMessage(message);
			else
				entities.remove(entity);
		}
		
		if (entities.size() == 1)
			player.sendMessage(ChatColor.GOLD + "Nobody hears you...");
	}
}