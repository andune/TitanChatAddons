package com.titankingdoms.nodinchan.titanchat.addon;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.titankingdoms.nodinchan.titanchat.event.MessageFormatEvent;
import com.titankingdoms.nodinchan.titanchat.util.variable.Variable.IVariable;

public class WorldVariable extends Addon {
	
	private MultiverseCore multiverse;
	
	public WorldVariable() {
		super("WorldVariable");
		init(this);
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("Multiverse-Core") != null)
			multiverse = (MultiverseCore) plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
		
		plugin.getVariableManager().register(new IVariable() {
			
			@Override
			public Class<? extends Event> getEvent() {
				return MessageFormatEvent.class;
			}
			
			@Override
			public String getReplacement(Player sender, Player... recipants) {
				World world = sender.getWorld();
				
				if (multiverse != null) {
					MVWorldManager mvwManager = multiverse.getMVWorldManager();
					
					if (mvwManager.isMVWorld(world))
						return mvwManager.getMVWorld(world).getColoredWorldString();
				}
				
				return world.getName();
			}
			
			@Override
			public String getVariable() {
				return "%world";
			}
		});
	}
}