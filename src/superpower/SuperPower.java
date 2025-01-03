package superpower;

import java.io.Serializable;

public class SuperPower implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String iconPath;
    private int cooldown;
    private int remainingCooldown;

    public SuperPower(String name, String iconPath, int cooldown) {
        this.name = name;
        this.iconPath = iconPath;
        this.cooldown = cooldown;
        this.remainingCooldown = 0;
    }

    public String getName() { return name; }
    public String getIconPath() { return iconPath; }
    public int getCooldown() { return cooldown; }
    public int getRemainingCooldown() { return remainingCooldown; }

    public void startCooldown() {
        this.remainingCooldown = this.cooldown;
    }

    public void tickCooldown() {
        if (remainingCooldown > 0) {
            remainingCooldown--;
        }
    }
}