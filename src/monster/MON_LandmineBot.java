package monster;

import containers.HallContainer;
import entity.Arrow;
import entity.Entity;
import enums.Hall;
import object.OBJ_LuringGem;
import object.SuperObject;
import views.BasePanel;
import views.BossPanel;
import views.HallPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class MON_LandmineBot extends Entity {

    BasePanel gp;
    public boolean spawned = false;
    public int countdown;
    public boolean attacking;
    private int fighterCounter;
    public int tempScreenY, tempScreenX;
    public int loadCountWalk, loadCountAttacking;

    public boolean firstAttackCall;
    private int attackTimer = 0;
    private int randomAttackInterval = 60 + new Random().nextInt(181);
    private int attackDuration = 48; // Frames for attack animation to complete
    private int attackAnimationCounter = 0;


    private transient BufferedImage up_walking1, up_walking2, down_walking1, down_walking2;
    private transient BufferedImage left_walking1, left_walking2, right_walking1, right_walking2;
    private transient BufferedImage up_attacking1, up_attacking2, down_attacking1, down_attacking2;
    private transient BufferedImage left_attacking1, left_attacking2, right_attacking1, right_attacking2;

    private final String leftWalking1Path = "/res/monster/LandmineBotWalk/left1";
    private final String leftWalking2Path = "/res/monster/LandmineBotWalk/left2";
    private final String leftWalking3Path = "/res/monster/LandmineBotWalk/left3";
    private final String leftWalking4Path = "/res/monster/LandmineBotWalk/left4";

    private final String rightWalking1Path = "/res/monster/LandmineBotWalk/right1";
    private final String rightWalking2Path = "/res/monster/LandmineBotWalk/right2";
    private final String rightWalking3Path = "/res/monster/LandmineBotWalk/right3";
    private final String rightWalking4Path = "/res/monster/LandmineBotWalk/right4";

    private final String upWalking1Path = "/res/monster/LandmineBotWalk/left1";
    private final String upWalking2Path = "/res/monster/LandmineBotWalk/left2";
    private final String upWalking3Path = "/res/monster/LandmineBotWalk/left3";
    private final String upWalking4Path = "/res/monster/LandmineBotWalk/left4";

    private final String downWalking1Path = "/res/monster/LandmineBotWalk/left1";
    private final String downWalking2Path = "/res/monster/LandmineBotWalk/left2";
    private final String downWalking3Path = "/res/monster/LandmineBotWalk/left3";
    private final String downWalking4Path = "/res/monster/LandmineBotWalk/left4";

    private final String upAttacking1Path = "/res/monster/LandmineBotExplode/tile000";
    private final String upAttacking2Path = "/res/monster/LandmineBotExplode/tile001";
    private final String upAttacking3Path = "/res/monster/LandmineBotExplode/tile002";
    private final String upAttacking4Path = "/res/monster/LandmineBotExplode/tile003";
    private final String upAttacking5Path = "/res/monster/LandmineBotExplode/tile004";
    private final String upAttacking6Path = "/res/monster/LandmineBotExplode/tile005";
    private final String upAttacking7Path = "/res/monster/LandmineBotExplode/tile006";

    private final String downAttacking1Path = "/res/monster/LandmineBotExplode/tile000";
    private final String downAttacking2Path = "/res/monster/LandmineBotExplode/tile001";
    private final String downAttacking3Path = "/res/monster/LandmineBotExplode/tile002";
    private final String downAttacking4Path = "/res/monster/LandmineBotExplode/tile003";
    private final String downAttacking5Path = "/res/monster/LandmineBotExplode/tile004";
    private final String downAttacking6Path = "/res/monster/LandmineBotExplode/tile005";
    private final String downAttacking7Path = "/res/monster/LandmineBotExplode/tile006";

    private final String leftAttacking1Path = "/res/monster/LandmineBotExplode/tile000";
    private final String leftAttacking2Path = "/res/monster/LandmineBotExplode/tile001";
    private final String leftAttacking3Path = "/res/monster/LandmineBotExplode/tile002";
    private final String leftAttacking4Path = "/res/monster/LandmineBotExplode/tile003";
    private final String leftAttacking5Path = "/res/monster/LandmineBotExplode/tile004";
    private final String leftAttacking6Path = "/res/monster/LandmineBotExplode/tile005";
    private final String leftAttacking7Path = "/res/monster/LandmineBotExplode/tile006";

    private final String rightAttacking1Path = "/res/monster/LandmineBotExplode/tile000";
    private final String rightAttacking2Path = "/res/monster/LandmineBotExplode/tile001";
    private final String rightAttacking3Path = "/res/monster/LandmineBotExplode/tile002";
    private final String rightAttacking4Path = "/res/monster/LandmineBotExplode/tile003";
    private final String rightAttacking5Path = "/res/monster/LandmineBotExplode/tile004";
    private final String rightAttacking6Path = "/res/monster/LandmineBotExplode/tile005";
    private final String rightAttacking7Path = "/res/monster/LandmineBotExplode/tile006";

    public MON_LandmineBot(BasePanel gp) {
        super(gp);

        this.gp = gp;

        type = 2; // monster type
        name = "Fighter Monster";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 1;
        damage = 5;
        life = maxLife;

        // Solid area for collision detection
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;

        solidAreaDefaultWidth = solidArea.width;
        solidAreaDefaultHeight = solidArea.height;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Attack Area
        attackArea.width = 200;
        attackArea.height = 200;

        getImage();
        getImageAttacking();
        chooseImage();
    }

    public void getImage() {
        up_walking1 = setup(upWalking1Path, 104 * 2, 104 * 2);
        up_walking2 = setup(upWalking2Path, 104 * 2, 104 * 2);
        up_walking3 = setup(upWalking3Path, 104 * 2, 104 * 2);
        up_walking4 = setup(upWalking4Path, 104 * 2, 104 * 2);

        down_walking1 = setup(downWalking1Path, 104 * 2, 104 * 2);
        down_walking2 = setup(downWalking2Path, 104 * 2, 104 * 2);
        down_walking3 = setup(downWalking3Path, 104 * 2, 104 * 2);
        down_walking4 = setup(downWalking4Path, 104 * 2, 104 * 2);

        left_walking1 = setup(leftWalking1Path, 104 * 2, 104 * 2);
        left_walking2 = setup(leftWalking2Path, 104 * 2, 104 * 2);
        left_walking3 = setup(leftWalking3Path, 104 * 2, 104 * 2);
        left_walking4 = setup(leftWalking4Path, 104 * 2, 104 * 2);

        right_walking1 = setup(rightWalking1Path, 104 * 2, 104 * 2);
        right_walking2 = setup(rightWalking2Path, 104 * 2, 104 * 2);
        right_walking3 = setup(rightWalking3Path, 104 * 2, 104 * 2);
        right_walking4 = setup(rightWalking4Path, 104 * 2, 104 * 2);
    }

    public void chooseImage() {

        if (attacking) {
            chooseImageAttacking();
        }
        else {
            chooseImageWalking();
        }
    }

    public void getImageAttacking() {
        up_attacking1 = setup(upAttacking1Path, 104 * 2, 104 * 2);
        up_attacking2 = setup(upAttacking2Path, 104 * 2, 104 * 2);
        up_attacking3 = setup(upAttacking3Path, 104 * 2, 104 * 2);
        up_attacking4 = setup(upAttacking4Path, 104 * 2, 104 * 2);
        up_attacking5 = setup(upAttacking5Path, 104 * 2, 104 * 2);
        up_attacking6 = setup(upAttacking6Path, 104 * 2, 104 * 2);
        up_attacking7 = setup(upAttacking7Path, 104 * 2, 104 * 2);

        down_attacking1 = setup(downAttacking1Path, 104 * 2, 104 * 2);
        down_attacking2 = setup(downAttacking2Path, 104 * 2, 104 * 2);
        down_attacking3 = setup(downAttacking3Path, 104 * 2, 104 * 2);
        down_attacking4 = setup(downAttacking4Path, 104 * 2, 104 * 2);
        down_attacking5 = setup(downAttacking5Path, 104 * 2, 104 * 2);
        down_attacking6 = setup(downAttacking6Path, 104 * 2, 104 * 2);
        down_attacking7 = setup(downAttacking7Path, 104 * 2, 104 * 2);

        left_attacking1 = setup(leftAttacking1Path, 104 * 2, 104 * 2);
        left_attacking2 = setup(leftAttacking2Path, 104 * 2, 104 * 2);
        left_attacking3 = setup(leftAttacking3Path, 104 * 2, 104 * 2);
        left_attacking4 = setup(leftAttacking4Path, 104 * 2, 104 * 2);
        left_attacking5 = setup(leftAttacking5Path, 104 * 2, 104 * 2);
        left_attacking6 = setup(leftAttacking6Path, 104 * 2, 104 * 2);
        left_attacking7 = setup(leftAttacking7Path, 104 * 2, 104 * 2);

        right_attacking1 = setup(rightAttacking1Path, 104 * 2, 104 * 2);
        right_attacking2 = setup(rightAttacking2Path, 104 * 2, 104 * 2);
        right_attacking3 = setup(rightAttacking3Path, 104 * 2, 104 * 2);
        right_attacking4 = setup(rightAttacking4Path, 104 * 2, 104 * 2);
        right_attacking5 = setup(rightAttacking5Path, 104 * 2, 104 * 2);
        right_attacking6 = setup(rightAttacking6Path, 104 * 2, 104 * 2);
        right_attacking7 = setup(rightAttacking7Path, 104 * 2, 104 * 2);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        getImage();
        getImageAttacking();
    }

    public void chooseImageAttacking() {

        up1 = up_attacking1;
        up2 = up_attacking2;
        up3 = up_attacking3;
        up4 = up_attacking4;
        up5 = up_attacking5;

        down1 = down_attacking1;
        down2 = down_attacking2;
        down3 = down_attacking3;
        down4 = down_attacking4;
        down5 = down_attacking5;

        left1 = left_attacking1;
        left2 = left_attacking2;
        left3 = left_attacking3;
        left4 = left_attacking4;
        left5 = left_attacking5;

        right1 = right_attacking1;
        right2 = right_attacking2;
        right3 = right_attacking3;
        right4 = right_attacking4;
        right5 = right_attacking5;

    }

    public void chooseImageWalking() {

        up1 = up_walking1;
        up2 = up_walking2;
        up3 = up_walking3;
        up4 = up_walking4;
        up5 = up_walking5;

        down1 = down_walking1;
        down2 = down_walking2;
        down3 = down_walking3;
        down4 = down_walking4;
        down5 = down_walking5;

        left1 = left_walking1;
        left2 = left_walking2;
        left3 = left_walking3;
        left4 = left_walking4;
        left5 = left_walking5;

        right1 = right_walking1;
        right2 = right_walking2;
        right3 = right_walking3;
        right4 = right_walking4;
        right5 = right_walking5;

    }

    public boolean isAttackingFighter() {
        return attacking;
    }

    public void setAction() {
        if (!spawned) {
            Random random = new Random();
            worldX = random.nextInt(BasePanel.worldWidth);
            worldY = random.nextInt(BasePanel.worldHeight);
            spawned = true;
        }

        boolean hitPlayer = panel.getCollisionChecker().checkPlayer(this);

        if (hitPlayer && !panel.getPlayer().invincible) {
            panel.getPlayer().damagePlayer(damage);
        }

        if (attacking) {
            attackAnimationCounter++;
            if (attackAnimationCounter >= attackDuration) {
                attacking = false;
                attackAnimationCounter = 0;
            }
        } else {
            attackTimer++;
            if (attackTimer >= randomAttackInterval) {
                firstAttackCall = true;
                attacking = true;
                attackTimer = 0;
                randomAttackInterval = 60 + new Random().nextInt(181); // New random interval
            } else {
                firstAttackCall = false;
                actionLockCounter++;

                if (actionLockCounter == 120) {
                    Random random = new Random();
                    int i = random.nextInt(100) + 1;

                    direction = "right";

                    if (checkLuringGem()) direction = determineDirectionLuringGem();
                    actionLockCounter = 0;
                }
            }
        }
    }

    public BufferedImage getAnimateImage() {
        return image;
    }

    public int getBufferX() {
        return tempScreenX;
    }

    public int getBufferY() {
        return tempScreenY;
    }

    private String determineDirection() {
        int playerX = panel.getPlayer().screenX;
        int playerY = panel.getPlayer().screenY;

        if (Math.abs(playerX - worldX) > Math.abs(playerY - worldY)) {
            return playerX > worldX ? "right" : "left";
        } else {
            return playerY > worldY ? "down" : "up";
        }
    }

    public boolean checkLuringGem() {
        boolean checker = false;

        if (panel instanceof HallPanel) {
            switch (((HallPanel) panel).currentHall) {
                case HallOfEarth -> {
                    for (SuperObject obj : HallContainer.getHallOfEarth().objects) {
                        if (obj instanceof OBJ_LuringGem) {
                            checker = true;
                            break;
                        }
                    }
                }
                case HallOfFire -> {
                    for (SuperObject obj : HallContainer.getHallOfFire().objects) {
                        if (obj instanceof OBJ_LuringGem) {
                            checker = true;
                            break;
                        }
                    }
                }
                case HallOfWater -> {
                    for (SuperObject obj : HallContainer.getHallOfWater().objects) {
                        if (obj instanceof OBJ_LuringGem) {
                            checker = true;
                            break;
                        }
                    }
                }
                case HallOfAir -> {
                    for (SuperObject obj : HallContainer.getHallOfAir().objects) {
                        if (obj instanceof OBJ_LuringGem) {
                            checker = true;
                            break;
                        }
                    }
                }
                default -> checker = false;
            }
        }
        return checker;
    }

    public String determineDirectionLuringGem() {
        if (panel instanceof HallPanel) {
            for (SuperObject obj : HallContainer.getHallOfEarth().objects) {
                if (obj instanceof OBJ_LuringGem luringGem) {
                    if (Math.abs(luringGem.worldX - worldX) > Math.abs(luringGem.worldY - worldY)) {
                        return luringGem.worldX > worldX ? "right" : "left";
                    } else {
                        return luringGem.worldY > worldY ? "down" : "up";
                    }
                }
            }
        }
        return direction;
    }

    public void animate() {
        fighterCounter++;
        if (fighterCounter > 6) {
            if (isAttackingFighter()) {

                int currentWorldX = worldX;
                int currentWorldY = worldY;
                int solidAreaWidth = solidArea.width;
                int solidAreaHeight = solidArea.height;

                if (firstAttackCall) {
                    spriteNum = 1;
                    firstAttackCall = false;
                }

                if (spriteNum < 7) {
                    spriteNum++;
                } else {
                    this.life = 0;
                    this.alive = false;
                    spriteNum = 1;

                }

                if (spriteNum == 2) {
                    solidArea.width = attackArea.width;
                    solidArea.height = attackArea.height;
                }


                if (panel instanceof BossPanel bossPanel) {
                    boolean playerHitAttackCheck = bossPanel.getCollisionCheckerForBoss().checkPlayer(this);
                    if (playerHitAttackCheck && !panel.getPlayer().invincible) {
                        panel.getPlayer().damagePlayer(damage);
                    }
                }


                worldX = currentWorldX;
                worldY = currentWorldY;
                solidArea.width = solidAreaWidth;
                solidArea.height = solidAreaHeight;
            } else {
                if (spriteNum < 4) {
                    spriteNum++;
                } else {
                    spriteNum = 1;
                }
            }
            fighterCounter = 0;
        }
    }
    public void animateDirection() {
        tempScreenX = worldX;
        tempScreenY = worldY;

        if (!isAttackingFighter()) {
            switch (direction) {
                case "up" -> image = switch (spriteNum) {
                    case 1 -> up1;
                    case 2 -> up2;
                    case 3 -> up3;
                    case 4 -> up4;
                    default -> up1;
                };
                case "down" -> image = switch (spriteNum) {
                    case 1 -> down1;
                    case 2 -> down2;
                    case 3 -> down3;
                    case 4 -> down4;
                    default -> down1;
                };
                case "left" -> image = switch (spriteNum) {
                    case 1 -> left1;
                    case 2 -> left2;
                    case 3 -> left3;
                    case 4 -> left4;
                    default -> left1;
                };
                case "right" -> image = switch (spriteNum) {
                    case 1 -> right1;
                    case 2 -> right2;
                    case 3 -> right3;
                    case 4 -> right4;
                    default -> right1;
                };
            }
        }

        if (isAttackingFighter()) {
            switch (direction) {
                case "up" -> {
                    tempScreenY = worldY - BasePanel.tileSize;
                    image = switch (spriteNum) {
                        case 1 -> up1;
                        case 2 -> up2;
                        case 3 -> up3;
                        case 4 -> up4;
                        case 5 -> up5;
                        case 6 -> up6;
                        case 7 -> up7;
                        default -> up1;
                    };
                }
                case "down" -> image = switch (spriteNum) {
                    case 1 -> down1;
                    case 2 -> down2;
                    case 3 -> down3;
                    case 4 -> down4;
                    case 5 -> down5;
                    case 6 -> down6;
                    case 7 -> down7;
                    default -> down1;
                };
                case "left" -> {
                    tempScreenX = worldX - BasePanel.tileSize;
                    image = switch (spriteNum) {
                        case 1 -> left1;
                        case 2 -> left2;
                        case 3 -> left3;
                        case 4 -> left4;
                        case 5 -> left5;
                        case 6 -> left6;
                        case 7 -> left7;
                        default -> left1;
                    };
                }
                case "right" -> image = switch (spriteNum) {
                    case 1 -> right1;
                    case 2 -> right2;
                    case 3 -> right3;
                    case 4 -> right4;
                    case 5 -> right5;
                    case 6 -> right6;
                    case 7 -> right7;
                    default -> right1;
                };
            }
        }
    }


    public boolean isAttackingLandmineBot() {
        return attacking;
    }

    private int calculateDistanceToPlayer() {
        int playerX = panel.getPlayer().screenX;
        int playerY = panel.getPlayer().screenY;

        return (int) Math.sqrt(
                Math.pow(worldX - playerX, 2) +
                        Math.pow(worldY - playerY, 2)
        );
    }

}