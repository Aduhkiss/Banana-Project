package me.atticuszambrana.banana;

import me.atticuszambrana.banana.discord.Banana;

public class Core {
	private static Banana bananaBot;

	public static void main(String[] args) {
		bananaBot = new Banana(args[0], ";");
	}
	
	public static Banana getBanana() {
		return bananaBot;
	}

}
