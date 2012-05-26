package com.titankingdoms.nodinchan.titanchat.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.titankingdoms.nodinchan.titanchat.addon.Addon;

/*     Copyright (C) 2012  Nodin Chan <nodinchan@live.com>
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class MailSystem extends Addon {
	
	private final Map<String, Mailbox> mailboxes;
	
	public MailSystem() {
		super("MailSystem");
		register(this);
		register(new MailCommand(this));
		
		if (getMailDir().mkdir())
			plugin.log(Level.INFO, "Creating mail directory...");
		
		this.mailboxes = new HashMap<String, Mailbox>();
	}
	
	/**
	 * Gets the Mailbox of the Player
	 * 
	 * @param name The name of the Player
	 * 
	 * @return The Player Mailbox if found, otherwise null
	 */
	public Mailbox getMailbox(String name) {
		return mailboxes.get(name.toLowerCase());
	}
	
	/**
	 * Gets the Mail Directory
	 * 
	 * @return The Mail Directory
	 */
	public File getMailDir() {
		return new File(plugin.getDataFolder(), "mail");
	}
	
	/**
	 * Loads the Mailbox of the Player
	 * 
	 * @param name The name of the Player
	 * 
	 * @return The Player Mailbox
	 */
	public Mailbox loadMailbox(String name) {
		Mailbox mailbox = new Mailbox(name);
		
		File mb = new File(getMailDir(), name + ".mailbox");
		
		try {
			if (mb.createNewFile()) {
				mailboxes.put(name.toLowerCase(), mailbox);
				getMailbox(name).receiveMail("Server", "Welcome to " + plugin.getServer().getServerName() + "!");
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(mb));
				out.writeObject(getMailbox(name));
				out.close();
				
			} else {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(mb));
				mailbox = (Mailbox) in.readObject();
				mailboxes.put(name.toLowerCase(), mailbox);
				in.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mailbox;
	}
	
	/**
	 * Listens to PlayerQuitEvent to save the Player Mailbox
	 * 
	 * @param event PlayerQuitEvent
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(getMailDir(), event.getPlayer().getName() + ".mailbox")));
			out.writeObject(getMailbox(event.getPlayer().getName()));
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Listens to PlayerJoinEvent to load the Player Mailbox
	 * 
	 * @param event PlayerJoinEvent
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Mailbox mailbox = null;
		
		if (getMailbox(event.getPlayer().getName()) == null) {
			mailbox = loadMailbox(event.getPlayer().getName());
			
		} else {
			mailbox = getMailbox(event.getPlayer().getName());
		}
		
		if (mailbox.getUnreadMail() > 0)
			plugin.sendInfo(event.getPlayer(), "You have " + mailbox.getUnreadMail() + " unread mail");
	}
	
	/**
	 * Unloads the MailManager
	 */
	public void unload() {
		for (Mailbox mailbox : mailboxes.values()) {
			try {
				File mb = new File(getMailDir(), mailbox.getOwner() + ".mailbox");
				
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(mb));
				output.writeObject(mailbox);
				output.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		mailboxes.clear();
	}
}