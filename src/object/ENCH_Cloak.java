package object;

public class ENCH_Cloak extends SuperObject {

    public ENCH_Cloak() {
        super("/res/objects/cloak.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Cloak";
        this.collision = false;
    }
}