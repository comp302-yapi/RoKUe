package views;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import containers.HallContainer;
import containers.TileContainer;
import data.BuildPanelData;
import enums.BuildDirection;
import enums.Hall;
import listeners.keylisteners.BuildPanelKeyListener;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.mouselisteners.BuildPanelMouseListener;
import managers.*;
import object.*;
import utils.PanelUtils;


public class BuildPanel extends NonPlayablePanel {
	private final BuildPanelMouseListener buildPanelMouseListener;
	public ArrayList<SuperObject> objectsToDraw = new ArrayList<>();

	private Hall currentHall;// enum oluşturabiliriz bunun için

	private final BuildPanelKeyListener keyListener;

	public int mouseClickedX, mouseClickedY;
	public int mouseDraggedX, mouseDraggedY;
	public int selectedIdx = -1;
	public boolean selected = false;
	public boolean isHallValidated = true;

	String errorMessageLine1 ="Hall does not fit the requirements";
	String errorMessageLine2 = "Please add more objects";

	// For button drawings
	// TODO: Find a better way to store these values
	int buttonWidth = 200;
	int buttonHeight = 50;
	int buttonOffset = 100;
	int prevButtonX = buttonOffset;
	int nextButtonX = BasePanel.screenWidth - buttonWidth - buttonOffset;
	int buttonY = BasePanel.screenHeight - buttonHeight - buttonOffset;
	Color buttonColor = new Color(26, 17, 23);


	public BuildPanel(ViewManager viewManager) {
		super(viewManager);

        this.buildPanelMouseListener = new BuildPanelMouseListener(this);
        this.addMouseListener(buildPanelMouseListener);
		this.addMouseMotionListener(buildPanelMouseListener);
		this.keyListener = new BuildPanelKeyListener(this);
		this.addKeyListener(keyListener);

		currentHall = Hall.HallOfEarth;
		this.objectsToDraw.clear();
		this.getCurrentHallManager().objects.clear();

		this.getCurrentHallManager().gridWorld = new SuperObject[13][14];
		loadObjects();

	}

	public BuildPanelData exportData() {
		return new BuildPanelData(
				new ArrayList<>(getCurrentHallManager().objects),
				currentHall,
				isHallValidated
		);
	}

	public void restoreData(BuildPanelData data) {
		this.getCurrentHallManager().objects = new ArrayList<>(data.objectsToDraw);
		this.currentHall = data.currentHall;
		this.isHallValidated = data.isHallValidated;
	}


	public Hall getCurrentHall() {
		return this.currentHall;
	}

	public TileManagerForHall getCurrentHallManager() {
		return switch (currentHall) {
			case HallOfWater -> HallContainer.getHallOfWater();
			case HallOfEarth -> HallContainer.getHallOfEarth();
			case HallOfAir -> HallContainer.getHallOfAir();
			case HallOfFire -> HallContainer.getHallOfFire();
		};
	}

	public void setCurrentHall(Hall hall) {
		this.currentHall = hall;
	}

	protected void loadObjects() {

		OBJ_Chest chest = new OBJ_Chest();
		chest.worldX = BasePanel.tileSize * 23 + 16;
		chest.worldY = BasePanel.tileSize * 7;

		OBJ_Lantern lantern = new OBJ_Lantern();
		lantern.worldX = BasePanel.tileSize * 23 + 16;
		lantern.worldY = BasePanel.tileSize * 9;


		OBJ_Cactus cactus = new OBJ_Cactus();
		cactus.worldX = BasePanel.tileSize * 25 + 16;
		cactus.worldY = BasePanel.tileSize * 7;

		objectsToDraw.add(chest);
		objectsToDraw.add(lantern);
		objectsToDraw.add(cactus);

		OBJ_Barrel barrel1 = new OBJ_Barrel();
		barrel1.worldX = BasePanel.tileSize * 25 + 16;
		barrel1.worldY = BasePanel.tileSize * 9;

		OBJ_Chain chain1 = new OBJ_Chain();
		chain1.worldX = BasePanel.tileSize * 27 + 16;
		chain1.worldY = BasePanel.tileSize * 7;

		OBJ_Pot pot1 = new OBJ_Pot();
		pot1.worldX = BasePanel.tileSize * 27 + 16;
		pot1.worldY = BasePanel.tileSize * 9;

		objectsToDraw.add(barrel1);
		objectsToDraw.add(chain1);
		objectsToDraw.add(pot1);
	}

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        drawBuildScreen(g2, this);
    }

    public boolean checkObjectBorder(int x, int y) {
		return x - (int) (BasePanel.tileSize / 2) > 384 && x + (int) (BasePanel.tileSize / 2) < 960
				&& y - (int) (BasePanel.tileSize / 2) > 144 && y + (int) (BasePanel.tileSize / 2) < 772;

	}

    public void drawBuildScreen(Graphics2D g2,BasePanel panel) {

		getCurrentHallManager().draw(g2);

		g2.setColor(new Color(62, 41, 52));

		//TODO: bunun düzeltilmesi gerekiyor
		g2.fillRect(0,0,BasePanel.screenWidth,96);
		g2.fillRect(1008,0,BasePanel.screenWidth,BasePanel.screenHeight);
		g2.fillRect(0,0,336,BasePanel.screenHeight);
		g2.fillRect(0,816,BasePanel.screenWidth,BasePanel.screenHeight);

		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
		String text = "Build Mode";
		int x, y;
		x = PanelUtils.getXForCenteredText(text, panel, g2) - BasePanel.tileSize*2 + 50;
		y = BasePanel.tileSize * 3;

		// SHADOW
		g2.setColor(new Color(40, 35, 38));
		g2.drawString(text, x+5, y-70);

		// MAIN COLOR
		g2.setColor(new Color(26, 17, 23));
		g2.drawString(text, x, y-75);

		int hallTextXOffset = 450;
		int hallTextYOffset = 75;
		switch (currentHall) {
			case HallOfEarth -> {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
				String text2 = "Hall Of Earth";
				int x2, y2;
				x2 = PanelUtils.getXForCenteredText(text2, panel, g2) - BasePanel.tileSize * 2;
				y2 = BasePanel.tileSize * 3;
				g2.setColor(new Color(26, 17, 23));
				g2.drawString(text2, x2 - hallTextXOffset, y2 - hallTextYOffset);
				drawHallControlButtons(g2, null , BuildDirection.Forward.label);
			}
			case HallOfAir -> {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
				String text3 = "Hall of Air";
				int x3, y3;
				x3 = PanelUtils.getXForCenteredText(text3, panel, g2) - BasePanel.tileSize * 2;
				y3 = BasePanel.tileSize * 3;
				g2.setColor(new Color(26, 17, 23));
				g2.drawString(text3, x3 - hallTextXOffset, y3 - hallTextYOffset);
				drawHallControlButtons(g2, BuildDirection.Backward.label, BuildDirection.Forward.label);
			}
			case HallOfWater -> {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
				String text1 = "Hall Of Water";
				int x1, y1;
				x1 = PanelUtils.getXForCenteredText(text1, panel, g2) - BasePanel.tileSize * 2;
				y1 = BasePanel.tileSize * 3;
				g2.setColor(new Color(26, 17, 23));
				g2.drawString(text1, x1 - hallTextXOffset, y1 - hallTextYOffset);
				drawHallControlButtons(g2, BuildDirection.Backward.label, BuildDirection.Forward.label);
			}
			case HallOfFire -> {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
				String text4 = "Hall Of Fire";
				int x4, y4;
				x4 = PanelUtils.getXForCenteredText(text4, panel, g2) - BasePanel.tileSize * 2;
				y4 = BasePanel.tileSize * 3;
				g2.setColor(new Color(26, 17, 23));
				g2.drawString(text4, x4 - hallTextXOffset, y4 - hallTextYOffset);
				drawHallControlButtons(g2, BuildDirection.Backward.label, "Start Game");
			}
		}

		//TODO: Tilelar için ayrı bir container yapmalıyız. 
		g2.drawImage(TileContainer.getTile()[19].image, 1075, 100, BasePanel.tileSize*7, BasePanel.tileSize*12, null);

		for (SuperObject obj : objectsToDraw) {
			if (obj != null) {
				g2.drawImage(obj.image, obj.worldX, obj.worldY, BasePanel.tileSize, BasePanel.tileSize, null);
			}
		}

		if(selected) {
		 	//g2.setColor(Color.BLACK);
			//g2.setStroke(new BasicStroke(2)); // Thicker border
			//g2.drawRect(objectsToDraw.get(selectedIdx).worldX, objectsToDraw.get(selectedIdx).worldY, BasePanel.tileSize, BasePanel.tileSize);

			drawDraggedItem(g2, mouseDraggedX, mouseDraggedY);
		}

		if (!isHallValidated) {
			drawErrorMessage(g2, nextButtonX);
		}

		/*
		g2.drawString(">", BasePanel.buildMode.objectToDraw[BasePanel.buildMode.selected].worldX - BasePanel.tileSize,
				BasePanel.buildMode.objectToDraw[BasePanel.buildMode.selected].worldY+BasePanel.tileSize);
		*/
    }

	private void drawDraggedItem(Graphics2D g2, int mouseX, int mouseY) {
		SuperObject selectedObj = objectsToDraw.get(selectedIdx);

		g2.drawImage(
				selectedObj.image,
				mouseX - selectedObj.image.getWidth() / 2 * scale,
				mouseY - selectedObj.image.getHeight() / 2 * scale,
				BasePanel.tileSize,
				BasePanel.tileSize, null
		);
	}

	private void drawHallControlButtons(Graphics2D g2, String prevText, String nextText) {
		if(prevText != null) {
			g2.setColor(buttonColor);
			g2.fillRect(prevButtonX, buttonY, buttonWidth, buttonHeight);
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
			int textXPrev = prevButtonX + (buttonWidth - g2.getFontMetrics().stringWidth(prevText)) / 2;
			int textYPrev = buttonY + (buttonHeight + g2.getFontMetrics().getAscent()) / 2 - 5;
			g2.drawString(prevText, textXPrev, textYPrev);
		}

		g2.setColor(buttonColor);
		g2.fillRect(nextButtonX, buttonY, buttonWidth, buttonHeight);
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
		int textXNext = nextButtonX + (buttonWidth - g2.getFontMetrics().stringWidth(nextText)) / 2;
		int textYNext = buttonY + (buttonHeight + g2.getFontMetrics().getAscent()) / 2 - 5;
		g2.drawString(nextText, textXNext, textYNext);

		if (!isHallValidated) {

		}
	}

	private void drawErrorMessage(Graphics2D g2, int xStart) {
		g2.setColor(new Color(180, 0,0));
		g2.setFont(arial_20);
		g2.drawString(errorMessageLine1, nextButtonX  - 50, buttonY + 75);
		g2.drawString(errorMessageLine2, nextButtonX - 10, buttonY + 95);
	}

	public boolean isInPreviousButton(int mouseX, int mouseY) {
		return mouseX > prevButtonX && mouseX < prevButtonX + buttonWidth
				&& mouseY > buttonY && mouseY < buttonY + buttonHeight;
	}

	public boolean isInNextButton(int mouseX, int mouseY) {
		return mouseX > nextButtonX && mouseX < nextButtonX + buttonWidth
				&& mouseY > buttonY && mouseY < buttonY + buttonHeight;
	}

}
