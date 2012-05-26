package com.titankingdoms.nodinchan.titanchat.mail;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * Mail - Represents a Mailbox Mail
 * 
 * @author NodinChan
 *
 */
public class Mail implements Serializable {
	
	private static final long serialVersionUID = -3426171776507035854L;
	
	private final String sender;
	
	private final long time;
	
	private final String message;
	
	private boolean read;
	
	public Mail(String sender, String message) {
		this.sender = sender;
		this.time = System.currentTimeMillis();
		this.message = message;
		this.read = false;
	}
	
	/**
	 * Gets the date when the Mail was sent
	 * 
	 * @return The date and time
	 */
	public String getDateTime() {
		return new SimpleDateFormat("dd.MM.yy HH:mm:ss").format(new Date(time));
	}
	
	/**
	 * Gets the message of the Mail
	 * 
	 * @return The Mail message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the sender of the Mail
	 * 
	 * @return The Mail sender
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * Gets the difference in milliseconds between the time the Mail was sent and midnight of January 1, 1970 UTC
	 * 
	 * @return The difference in milliseconds between the time the Mail was sent and midnight of January 1, 1970 UTC
	 */
	public long getSystemTime() {
		return time;
	}
	
	/**
	 * Gets the title of the Mail
	 * 
	 * @return The Mail title
	 */
	public String getTitle() {
		return message.substring(0, 11) + "...";
	}
	
	/**
	 * Check if the Mail has been read
	 * 
	 * @return True if the Mail has been read
	 */
	public boolean isRead() {
		return read;
	}
	
	/**
	 * Sets whether the Mail has been read or not
	 * 
	 * @param read True if Mail has been read
	 */
	public void setRead(boolean read) {
		this.read = read;
	}
}