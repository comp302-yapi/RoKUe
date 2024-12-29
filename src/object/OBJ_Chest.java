package object;

public class OBJ_Chest extends SuperObject {

	public OBJ_Chest() {
		super("/res/objects/chest.png", null, null); // Pass the image path for image1, others are null if unused
		name = "Chest";
		this.collision = true;
	}
}