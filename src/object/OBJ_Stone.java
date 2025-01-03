package object;

public class OBJ_Stone extends SuperObject {

    public OBJ_Stone() {
        super("/res/objects/stone.png", null, null); // Pass the image path to SuperObject
        name = "Stone";
        collision = true;
    }
}