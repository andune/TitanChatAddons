package com.titankingdoms.nodinchan.titanchat.channel;

import org.bukkit.entity.Player;

public final class GlobalChannel extends CustomChannel {
	
	public GlobalChannel() {
		super("Global");
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
		boolean colourise= getConfig().getBoolean("colour-code");
		message = (colourise || plugin.getPermsBridge().has(sender, "TitanChat.colours")) ? colourise(message) : decolourise(message);
		
		return super.sendMessage(sender, plugin.getServer().getOnlinePlayers(), message);
	}
}