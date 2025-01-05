package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import entity.Entity;
import utils.ImageUtils;
import views.BasePanel;
import views.HallPanel;
import views.HomePanel;

import javax.imageio.ImageIO;

public abstract class SuperParticle implements Serializable {

    private static final long serialVersionUID = 1L;

    protected transient ArrayList<BufferedImage> animationFrames = new ArrayList<>();
    public String name;
    public boolean collision = false;
    public int worldX, worldY;


    protected int animationSpeed = 1; // Frames per update

    protected int currentFrame = 0;
    protected int frameCounter = 0;
    protected boolean isAnimationComplete = false;

    Entity followEntity;

    public transient ImageUtils uTool = new ImageUtils();

    /**
     * Handles the player's interaction when they click this object.
     * For example, if the object contains a rune, it prints a message.
     */

    public SuperParticle(ArrayList<BufferedImage> animationFrames) {
        this.animationFrames = animationFrames;

        loadFrames();
    }

    public abstract void loadFrames();

    public void setFollowEntity(Entity entity) {
        this.followEntity = entity;
    }

    public void updateAnimation() {
        if (animationFrames != null && !animationFrames.isEmpty()) {
            frameCounter++;
            if (frameCounter >= animationSpeed) {
                currentFrame = (currentFrame + 1) % animationFrames.size();
                frameCounter = 0;
            }

            if (currentFrame == animationFrames.size() - 1) {
                isAnimationComplete = true;
            }
        }
    }

    public void draw(Graphics2D g2, BasePanel panel) {
//        System.out.println("In draw");
        if (this instanceof PRTCL_Lightning) {
            if (animationFrames != null && currentFrame < animationFrames.size()) {
                g2.drawImage(animationFrames.get(currentFrame), this.worldX - 150, this.worldY - 230, 300, 300, null);
            }
        } else {
            if (animationFrames != null && currentFrame < animationFrames.size()) {
                g2.drawImage(animationFrames.get(currentFrame), this.worldX, this.worldY, BasePanel.tileSize, BasePanel.tileSize, null);
            }
        }
    }

    public boolean isAnimationComplete() {
        return isAnimationComplete;
    }

    // Custom serialization
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        loadFrames();
    }

    private int calculateDistanceToPlayer(HallPanel hp) {
        int playerX = hp.getPlayer().screenX;
        int playerY = hp.getPlayer().screenY;

        return (int) Math.sqrt(
                Math.pow(worldX - playerX, 2) +
                        Math.pow(worldY - playerY, 2)
        );
    }

    public void interact(HallPanel panel) {

        int dist = this.calculateDistanceToPlayer(panel);
    }

}