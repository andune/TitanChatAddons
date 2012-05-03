package com.titankingdoms.nodinchan.titanchat.channel;

import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.addon.RangeChannels;

public final class GlobalChannel extends CustomChannel {
	
	private final RangeChannels addon;
	
	public GlobalChannel(RangeChannels addon) {
		super("Global");
		init(this);
		this.addon = addon;
	}
	
	@Override
	public String format(Player player, String format) {
		String tag = addon.getConfig().getString("GlobalChannel.tag");
		String prefix = plugin.getPermsBridge().getPlayerPrefix(player);
		String suffix = plugin.getPermsBridge().getPlayerSuffix(player);
		String colour = addon.getConfig().getString("GlobalChannel.chat-display-colour");
		
		format = format.replace("%tag", colourise(tag));
		format = format.replace("%prefix", colourise(prefix));
		format = format.replace("%suffix", colourise(suffix));
		format = format.replace("%player", colourise(colour + player.getDisplayName()));
		format = format.replace("%message", colourise(colour) + "%message");
		
		return format;
	}
	
	@Override
	public String getFormat() {
		return addon.getConfig().getString("GlobalChannel.format");
	}
	
	@Override
	public String sendMessage(Player sender, String message) {
		boolean colourise= addon.getConfig().getBoolean("GlobalChannel.colour-code");
		message = (colourise || plugin.getPermsBridge().has(sender, "TitanChat.colours")) ? colourise(message) : decolourise(message);
		
		return super.sendMessage(sender, plugin.getServer().getOnlinePlayers(), message);
	}
}