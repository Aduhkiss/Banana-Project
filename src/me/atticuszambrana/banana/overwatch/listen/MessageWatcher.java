package me.atticuszambrana.banana.overwatch.listen;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.MessageDeleteEvent;
import org.javacord.api.event.message.MessageEditEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.MessageDeleteListener;
import org.javacord.api.listener.message.MessageEditListener;

import me.atticuszambrana.banana.overwatch.Overwatch;

public class MessageWatcher implements MessageCreateListener,MessageDeleteListener,MessageEditListener {
	
	private Overwatch overwatch;
	
	public MessageWatcher(Overwatch overwatch) {
		this.overwatch = overwatch;
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		User author = null;
		try {
			author = event.getMessageAuthor().asUser().get();
		} catch(NoSuchElementException ex) {}
		String message = event.getMessageContent();
		
		try {
			overwatch.logMessage(event.getServer().get().getIdAsString(), "[CHAT] " + author.getName() + " (" + author.getId() + "): " + message);
		} catch (Exception e) {
		}
	}
	
	@Override
	public void onMessageDelete(MessageDeleteEvent event) {
		User author = event.getMessageAuthor().get().asUser().get();
		String message = event.getMessageContent().get();
		
		try {
			overwatch.logMessage(event.getServer().get().getIdAsString(), "[MESSAGE DELETED] " + author.getName() + " (" + author.getId() + "): " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessageEdit(MessageEditEvent event) {
		User author = event.getMessageAuthor().get().asUser().get();
		String old = event.getOldContent().get();
		String theNew = event.getNewContent();
		
		try {
			overwatch.logMessage(event.getServer().get().getIdAsString(), "[MESSAGE EDITED] " + author.getName() + " (" + author.getId() + ") Edited from '" + old + "' to '" + theNew + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
