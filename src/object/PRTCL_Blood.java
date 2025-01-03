package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class PRTCL_Blood extends SuperParticle {

    public PRTCL_Blood(ArrayList<BufferedImage> bloodAnimation,  int x, int y) {
            super(bloodAnimation);
            this.worldX = x;
            this.worldY = y;
            this.animationSpeed = 1;
    }

    public void loadFrames() {
        if (animationFrames != null) {
            animationFrames.clear(); // Clear existing frames
        }
        try {
            for (int i = 0; i < 29; i++) {
                String filePath = "src/res/particles/Blood/tile" + i + ".png";
                BufferedImage frame = ImageIO.read(new File(filePath));
                if (animationFrames != null) {
                    animationFrames.add(frame);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

