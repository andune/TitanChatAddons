package com.titankingdoms.nodinchan.titanchat.channel;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.addon.RangeChannels;

public final class LocalChannel extends CustomChannel {
	
	private final RangeChannels addon;
	
	public LocalChannel(RangeChannels addon) {
		super("Local");
		init(this);
		this.addon = addon;
	}
	
	@Override
	public String format(Player player, String format) {
		String tag = addon.getConfig().getString("LocalChannel.tag");
		String prefix = plugin.getPermsBridge().getPlayerPrefix(player);
		String suffix = plugin.getPermsBridge().getPlayerSuffix(player);
		String colour = addon.getConfig().getString("LocalChannel.chat-display-colour");
		
		format = format.replace("%tag", colourise(tag));
		format = format.replace("%prefix", colourise(prefix));
		format = format.replace("%suffix", colourise(suffix));
		format = format.replace("%player", colourise(colour + player.getDisplayName()));
		format = format.replace("%message", colourise(colour) + "%message");
		
		return format;
	}
	
	@Override
	public String getFormat() {
		return addon.getConfig().getString("LocalChannel.format");
	}
	
	@Override
	public String sendMessage(Player sender, String message) {
		int radius = addon.getConfig().getInt("LocalChannel.radius");
		
		List<Player> recipants = new ArrayList<Player>();
		recipants.add(sender);
		
		for (Entity entity : sender.getNearbyEntities(radius, radius, radius)) {
			if (entity instanceof Player)
				recipants.add((Player) entity);
		}
		
		boolean colourise= addon.getConfig().getBoolean("LocalChannel.colour-code");
		message = (colourise || plugin.getPermsBridge().has(sender, "TitanChat.colours")) ? colourise(message) : decolourise(message);
		
		String sent = super.sendMessage(sender, recipants, message);
		
		if (recipants.size() == 1 || sent.equals(""))
			sender.sendMessage(ChatColor.GOLD + "Nobody hears you...");
		
		return sent;
	}
}