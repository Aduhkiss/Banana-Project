package me.atticuszambrana.banana.overwatch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;

public class Overwatch {
	/*
	 * Our new Discord server logging system. This will be responsible for logging EVERYTHING that goes on in our servers.
	 */
	
	// LocalDate.now() - to get date
	public Overwatch(DiscordApi api) {
		for(Server s : api.getServers() ) {
			try {
				createLog(s.getIdAsString());
			} catch (IOException e) {
				// Well fuck
				e.printStackTrace();
			}
		}
	}
	
	private Map<String, File> EasyAccess = new HashMap<>();
	
	private boolean createLog(String server) throws IOException {
		File directory = new File("" + server);
		if(!directory.exists()) {
			directory.mkdir();
		}
		for(int i = 1; i < 100; i++) {
			String name = LocalDate.now().toString() + "-" + i;
			File log = new File(directory.getAbsolutePath() + "\\" + name + ".log");
			if(log.createNewFile()) {
				EasyAccess.put(server, log);
				return true;
			}
		}
		return false;
	}
	
	public void logMessage(String server, String message) throws IOException {
		File file = EasyAccess.get(server);
		FileWriter writer = new FileWriter(file, true);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		writer.append("{" + dtf.format(now) + "}  " + message + "\n");
		writer.close();
	}
}
