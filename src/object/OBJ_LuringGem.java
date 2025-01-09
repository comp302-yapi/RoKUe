package object;

public class OBJ_LuringGem extends SuperObject {

    public OBJ_LuringGem() {
        super("/res/objects/ThrowedLuringGem.png", null, null); // Pass the image path for image1, others are null if unused
        name = "LuringGem";
        collision = true;
    }
}