package me.atticuszambrana.banana.command;

import org.javacord.api.event.message.MessageCreateEvent;

public abstract class Command {
	
	private String command;
	private String description;
	// Wheather the command requires a manager override to execute
	private boolean mgrOverride;
	
	public Command(String command, String description, boolean mgrOverride) {
		this.command = command;
		this.description = description;
		this.mgrOverride = mgrOverride;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean requiresMgrOverride() {
		return mgrOverride;
	}
	
	public abstract void execute(String[] args, MessageCreateEvent event);

}
