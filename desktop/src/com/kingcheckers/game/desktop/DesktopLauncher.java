package com.kingcheckers.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kingcheckers.game.KCGame;
import com.kingcheckers.game.KingCheckers;
import com.kingcheckers.game.Screen.StartScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 650;
		new LwjglApplication(new KingCheckers(), config);
	}
}
