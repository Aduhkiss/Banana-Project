package me.atticuszambrana.banana.discord;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import me.atticuszambrana.banana.command.CommandListener;
import me.atticuszambrana.banana.overwatch.Overwatch;
import me.atticuszambrana.banana.overwatch.listen.MessageWatcher;
import me.atticuszambrana.banana.overwatch.listen.UserWatcher;
import me.atticuszambrana.banana.util.Console;

public class Banana {
	
	private String token;
	private String prefix;
	
	private DiscordApi api;
	
	public Banana(String token, String prefix) {
		
		this.token = token;
		this.prefix = prefix;
		
		this.api = new DiscordApiBuilder().setToken(token).login().join();
		Console.print("Banana Project", "Connected to Discord API.");
		
		Console.print("Comand Listener", "Making Command Listener and registering all commands...");
		CommandListener commandListener = new CommandListener();
		api.addMessageCreateListener(commandListener);
		
		Console.print("Overwatch", "Starting Overwatch System...");
		Overwatch over = new Overwatch(api);
		Console.print("Overwatch", "Registering Action Listeners...");
		
		MessageWatcher watcher = new MessageWatcher(over);
		UserWatcher uWatch = new UserWatcher(over);
		api.addMessageCreateListener(watcher);
		api.addMessageDeleteListener(watcher);
		api.addMessageEditListener(watcher);
		api.addUserChangeNicknameListener(uWatch);
		
		Console.print("Banana Project", "Done. Thank you for using The Banana Project!");
	}
	
	public String getPrefix() {
		return prefix;
	}
}
