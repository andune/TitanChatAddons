package com.titankingdoms.nodinchan.titanchat.support;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.titankingdoms.nodinchan.titanchat.TitanChat;

public class HeroesSupport extends Support {
	
	protected Heroes heroes;
	
	public HeroesSupport(TitanChat plugin) {
		super(plugin, "HeroesSupport");
	}
	
	@Override
	public void chatMade(String name, String message) {}
	
	@Override
	public String chatMade(Player player, String message) {
		String msg = message;
		
		if (heroes != null) {
			String heroClass = heroes.getHeroManager().getHero(player).getHeroClass().getName();
			String profClass = heroes.getHeroManager().getHero(player).getSecondClass().getName();
			String level = heroes.getHeroManager().getHero(player).getLevel() + "";
			String health = heroes.getHeroManager().getHero(player).getHealth() + "";
			
			msg = msg.replace("%heroclass", heroClass);
			msg = msg.replace("%profClass", profClass);
			msg = msg.replace("%level", level);
			msg = msg.replace("%health", health);
		}
		
		return msg;
	}

	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("Heroes") != null)
			heroes = (Heroes) plugin.getServer().getPluginManager().getPlugin("Heroes");
	}
}