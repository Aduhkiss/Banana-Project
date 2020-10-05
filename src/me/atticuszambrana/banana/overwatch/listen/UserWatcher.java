package me.atticuszambrana.banana.overwatch.listen;

import java.io.IOException;

import org.javacord.api.event.user.UserChangeNicknameEvent;
import org.javacord.api.listener.user.UserChangeNicknameListener;

import me.atticuszambrana.banana.overwatch.Overwatch;

public class UserWatcher implements UserChangeNicknameListener {
	
	private Overwatch ow;
	public UserWatcher(Overwatch ow) {
		this.ow = ow;
	}
	
	@Override
	public void onUserChangeNickname(UserChangeNicknameEvent event) {
		try {
			ow.logMessage(event.getServer().getIdAsString(), event.getUser().getName() + " has changed their nickname!   " + event.getOldNickname().get() + " ---> " + event.getNewNickname().get());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
