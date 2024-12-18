package monster;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;
import entity.Entity;
import views.BasePanel;

public class MON_Witch extends Entity{

	BasePanel panel;
	public BufferedImage allTiles;
	public BufferedImage witchImage;


	public MON_Witch(BasePanel panel) {
		super(panel);
		
		this.panel = panel;
		
		type = 2;
		name = "Witch";
		speed = 2;
		maxLife = 4;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	public void getImage() {
		
		try {
			allTiles = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/0x72_16x16DungeonTileset.v5.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		witchImage = allTiles.getSubimage(80, 144, 16, 16);
		
		up1 = witchImage;
		up2 = witchImage;
		down1 = witchImage;
		down2 = witchImage;
		left1 = witchImage;
		left2 = witchImage;
		right1 = witchImage;
		right2 = witchImage;

	}
	
	public void setAction() {
		
		actionLockCounter++;
		
			if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			
			if (i <= 25) {
				direction = "up";
			}
			else if (i <= 50) {
				direction = "down";
			}
			else if (i <= 75) {
				direction = "left";
			}
			else {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}

}
