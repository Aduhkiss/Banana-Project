package me.atticuszambrana.banana.override;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.user.User;

public class OverrideManager {
	
	public static List<User> NeedsOverride = new ArrayList<>();
	public static List<User> Supervisors = new ArrayList<>();
	
	public static boolean needsOverride(User user) {
		for(User u : NeedsOverride) {
			if(u.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSupervisor(User user) {
		for(User u : Supervisors) {
			if(u.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public static void add(User user) {
		NeedsOverride.add(user);
	}
	
	public static void remove(User user) {
		NeedsOverride.remove(user);
	}
}
