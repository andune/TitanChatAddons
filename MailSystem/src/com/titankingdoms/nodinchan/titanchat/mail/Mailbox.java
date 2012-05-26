package com.titankingdoms.nodinchan.titanchat.mail;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

/**
 * Mailbox - Represents a Player's Mailbox
 * 
 * @author NodinChan
 *
 */
public final class Mailbox implements Serializable {
	
	private static final long serialVersionUID = 6389202341763414739L;
	
	private final String owner;
	
	private LinkedList<Mail> mailbox;
	private List<Integer> selection;
	
	public Mailbox(String owner) {
		this.owner = owner;
		this.mailbox = new LinkedList<Mail>();
		this.selection = new LinkedList<Integer>();
	}
	
	/**
	 * Deletes the selected Mail
	 * 
	 * @param mail The ID of the Mail
	 * 
	 * @return True if the Mail could be found and deleted
	 */
	public void deleteMail(Collection<Mail> mail) {
		mailbox.removeAll(mail);
	}
	
	/**
	 * Gets a list of all the Mail the Player has
	 * 
	 * @return The Mail list
	 */
	public List<Mail> getMail() {
		return mailbox;
	}
	
	/**
	 * Gets the name of the Owner of the Mailbox
	 * 
	 * @return The Owner name
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * Gets the selected list of Mail IDs
	 * 
	 * @return The selected list
	 */
	public List<Integer> getSelection() {
		return selection;
	}
	
	/**
	 * Gets the amount of unread Mail
	 * 
	 * @return The number of unread Mail
	 */
	public int getUnreadMail() {
		int unread = 0;
		
		for (Mail mail : mailbox)
			if (!mail.isRead())
				unread++;
		
		return unread;
	}
	
	/**
	 * Gets the Mail of the selected ID
	 * 
	 * @param mail The ID of the Mail
	 * 
	 * @return The Mail if exists, otherwise null
	 */
	public Mail readMail(int mail) {
		return mailbox.get(mail);
	}
	
	/**
	 * Receives the Mail and saves to the Mailbox
	 * 
	 * @param sender The sender of the Mail
	 * 
	 * @param message The message of the Mail
	 */
	public void receiveMail(String sender, String message) {
		mailbox.add(new Mail(sender, message));
	}
	
	/**
	 * Gets the size of the Mailbox
	 * 
	 * @return The amount of Mail
	 */
	public int size() {
		return mailbox.size();
	}
	
	/**
	 * Sorts the list of Mail
	 */
	public void sort() {
		Map<Long, Mail> mailMap = new HashMap<Long, Mail>();
		List<Long> times = new LinkedList<Long>();
		
		for (Mail mail : mailbox) {
			mailMap.put(mail.getSystemTime(), mail);
			times.add(mail.getSystemTime());
		}
		
		Collections.sort(times);
		
		LinkedList<Mail> mail = new LinkedList<Mail>();
		
		for (long time : times)
			mail.add(mailMap.get(time));
		
		this.mailbox = mail;
	}
}