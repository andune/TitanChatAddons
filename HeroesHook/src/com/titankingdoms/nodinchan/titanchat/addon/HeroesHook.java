package com.titankingdoms.nodinchan.titanchat.addon;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.titankingdoms.nodinchan.titanchat.event.MessageFormatEvent;
import com.titankingdoms.nodinchan.titanchat.util.variable.Variable.IVariable;

public class HeroesHook extends Addon {
	
	private Heroes heroes;
	
	public HeroesHook() {
		super("HeroesHook");
		init(this);
	}
	
	@Override
	public void init() {
		if (plugin.getServer().getPluginManager().getPlugin("Heroes") != null) {
			heroes = (Heroes) plugin.getServer().getPluginManager().getPlugin("Heroes");
			
			plugin.getVariableManager().register(new IVariable() {
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					return heroes.getCharacterManager().getHero(sender).getHeroClass().getName();
				}
				
				@Override
				public String getVariable() {
					return "%heroclass";
				}
				
			}, new IVariable(){
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					return heroes.getCharacterManager().getHero(sender).getSecondClass().getName();
				}
				
				@Override
				public String getVariable() {
					return "%heroprof";
				}
				
			}, new IVariable(){
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					return heroes.getCharacterManager().getHero(sender).getHealth() + "/" + heroes.getCharacterManager().getHero(sender).getMaxHealth();
				}
				
				@Override
				public String getVariable() {
					return "%hp";
				}
				
			}, new IVariable(){
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					return heroes.getCharacterManager().getHero(sender).getMana() + "/" + heroes.getCharacterManager().getHero(sender).getMaxMana();
				}
				
				@Override
				public String getVariable() {
					return "%mana";
				}
				
			}, new IVariable(){
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					return heroes.getCharacterManager().getHero(sender).getLevel(heroes.getCharacterManager().getHero(sender).getHeroClass()) + "";
				}
				
				@Override
				public String getVariable() {
					return "classlvl";
				}
				
			}, new IVariable(){
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					return heroes.getCharacterManager().getHero(sender).getLevel(heroes.getCharacterManager().getHero(sender).getSecondClass()) + "";
				}
				
				@Override
				public String getVariable() {
					return "%proflvl";
				}
				
			}, new IVariable(){
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					Hero hero = heroes.getCharacterManager().getHero(sender);
					
					StringBuilder healthBar = new StringBuilder();
					healthBar.append("\u00A77[");
					
					int bar;
					
					for (bar = 0; bar < (int) ((hero.getHealth() / hero.getMaxHealth()) * 10); bar++)
						healthBar.append("\u00A72|");
					
					for (int barLeft = bar; barLeft < 10; barLeft++)
						healthBar.append("\u00A74|");
					
					healthBar.append("\u00A77]");
					
					return healthBar.toString();
				}
				
				@Override
				public String getVariable() {
					return "%hpbar";
				}
				
			}, new IVariable(){
				
				@Override
				public Class<? extends Event> getEvent() {
					return MessageFormatEvent.class;
				}
				
				@Override
				public String getReplacement(Player sender, Player... recipants) {
					Hero hero = heroes.getCharacterManager().getHero(sender);
					
					StringBuilder manaBar = new StringBuilder();
					manaBar.append("\u00A76[");
					
					int bar;
					
					for (bar = 0; bar < (int) ((hero.getMana() / hero.getMaxMana()) * 10); bar++)
						manaBar.append("\u00A71|");
					
					for (int barLeft = bar; barLeft < 10; barLeft++)
						manaBar.append("\u00A78|");
					
					manaBar.append("\u00A76]");
					
					return manaBar.toString();
				}
				
				@Override
				public String getVariable() {
					return "%manabar";
				}
			});
		}
	}
}