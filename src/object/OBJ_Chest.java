package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject{
	
	public OBJ_Chest() {
		
		name = "Chest";
		this.collision = true;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
