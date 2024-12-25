package main;

import managers.ViewManager;
import views.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

import containers.TileContainer;

public class Main {
	
	public static void main(String [] args) {
		
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("RokueLike Game");

		TileContainer.initiateTiles();
		
		ViewManager viewManager = new ViewManager(window);
		JPanel gamePanel = new GamePanel(viewManager);
		JPanel titlePanel = new TitlePanel(viewManager);
		JPanel buildPanel = new BuildPanel(viewManager);

		viewManager.addPanel("TitlePanel", titlePanel);
		viewManager.addPanel("GamePanel", gamePanel);
		viewManager.addPanel("BuildPanel", buildPanel);
		viewManager.switchTo("TitlePanel", true);
		viewManager.startThread();

		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
