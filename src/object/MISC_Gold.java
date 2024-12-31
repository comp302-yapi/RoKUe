package object;

public class MISC_Gold extends SuperObject {

    public MISC_Gold() {
        super("/res/objects/gold/tile000.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Reveal";
        this.collision = false;
    }
}