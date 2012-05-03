package com.titankingdoms.nodinchan.titanchat.channel;

import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.addon.RangeChannels;

public final class WorldChannel extends CustomChannel {
	
	private final RangeChannels addon;
	
	public WorldChannel(RangeChannels addon) {
		super("World");
		init(this);
		this.addon = addon;
	}
	
	@Override
	public String format(Player player, String format) {
		String tag = addon.getConfig().getString("WorldChannel.tag");
		String prefix = plugin.getPermsBridge().getPlayerPrefix(player);
		String suffix = plugin.getPermsBridge().getPlayerSuffix(player);
		String colour = addon.getConfig().getString("WorldChannel.chat-display-colour");
		
		format = format.replace("%tag", colourise(tag));
		format = format.replace("%prefix", colourise(prefix));
		format = format.replace("%suffix", colourise(suffix));
		format = format.replace("%player", colourise(colour + player.getDisplayName()));
		format = format.replace("%message", colourise(colour) + "%message");
		
		return format;
	}
	
	@Override
	public String getFormat() {
		return addon.getConfig().getString("WorldChannel.format");
	}
	
	@Override
	public String sendMessage(Player sender, String message) {
		boolean colourise= addon.getConfig().getBoolean("WorldChannel.colour-code");
		message = (colourise || plugin.getPermsBridge().has(sender, "TitanChat.colours")) ? colourise(message) : decolourise(message);
		
		return super.sendMessage(sender, sender.getWorld().getPlayers(), message);
	}
}