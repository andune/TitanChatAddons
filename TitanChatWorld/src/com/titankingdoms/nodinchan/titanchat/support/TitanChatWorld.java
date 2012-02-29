package com.titankingdoms.nodinchan.titanchat.support;

import org.bukkit.entity.Player;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class TitanChatWorld extends Support {
	
	protected MultiverseCore multiverse;

	public TitanChatWorld(TitanChat plugin) {
		super(plugin, "TitanChatWorld");
	}
	
	@Override
	public void chatMade(String name, String message) {}
	
	@Override
	public String chatMade(Player player, String message) {
		String msg = message;
		String world = player.getWorld().getName();
		
		if (multiverse != null) {
			if (multiverse.getMVConfig().getPrefixChat()) {
				if (multiverse.getMVWorldManager().isMVWorld(world)) {
					if (!multiverse.getMVWorldManager().getMVWorld(world).isHidden()) {
						msg = msg.replace("%world", multiverse.getMVWorldManager().getMVWorld(world).getColoredWorldString());
						
					} else {
						msg = msg.replace("%world", "");
					}
					
				} else {
					msg = msg.replace("%world", world);
				}
				
			} else {
				msg = msg.replace("%world", "");
			}
			
		} else {
			msg = msg.replace("%world", world);
		}
		
		return msg;
	}

	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("Multiverse-Core") != null)
			multiverse = (MultiverseCore) plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
	}
}