package com.titankingdoms.nodinchan.titanchat.support;

import org.bukkit.entity.Player;

import com.titankingdoms.nodinchan.titanchat.TitanChat;
import com.titankingdoms.nodinchan.titanchat.channel.Channel;

public class GlobalChannel extends CustomChannel {
	
	public GlobalChannel(TitanChat plugin) {
		super(plugin, "Global");
	}
	
	@Override
	public String format(Player player, String message) {
		String msg = message;
		
		String tag = plugin.getConfig().getString("tag");
		String prefix = plugin.getPlayerPrefix(player);
		String suffix = plugin.getPlayerSuffix(player);
		String channelColour = plugin.getConfig().getString("channel-display-colour");
		String nameColour = plugin.getConfig().getString("name-display-colour");
		boolean colourise= plugin.getConfig().getBoolean("colour-code");
		
		if (colourise || plugin.has(player, "TitanChat.colours"))
			msg = colourise(tag + " " + prefix + nameColour + player.getDisplayName() + suffix + "&f: " + channelColour + message);
		else
			msg = colourise(tag + " " + prefix + nameColour + player.getDisplayName() + suffix + "&f: " + channelColour) + decolourise(message);
		
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
	public void onLeave(Player player) {
		if (plugin.getConfigManager().enableLeaveMessages()) {
			for (String participant : getParticipants()) {
				if (plugin.getPlayer(participant) != null)
					plugin.sendInfo(plugin.getPlayer(participant), player.getDisplayName() + " has left the channel");
			}
		}
	}

	@Override
	public void sendMessage(Player player, String message) {
		sendGlobalMessage(message);
	}
}