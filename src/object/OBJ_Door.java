package object;

public class OBJ_Door extends SuperObject {

	public OBJ_Door() {
		super("/res/objects/door.png", null, null); // Pass the image path for image1, others are null if unused
		name = "Door";
		this.collision = true;
	}
}