package main;

import managers.ViewManager;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String [] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("RokueLike Game");

		ViewManager viewManager = new ViewManager(window);
		GamePanel gamePanel = new GamePanel();
		viewManager.addPanel("GamePanel", gamePanel);
		viewManager.switchTo("GamePanel", true);

		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startSession();
		
	}

}
