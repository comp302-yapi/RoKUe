package strategy;

import java.util.Random;

import views.HallPanel;

public class TeleportPlayerStrategy {
	
	
	public static void teleportPlayer(HallPanel hp) {
		Random random = new Random();

		//TODO: do not use hardcoded values for map

		hp.getPlayer().screenX = random.nextInt(12 * 48) + 336 + 48; // Adjust range as needed
		hp.getPlayer().screenY = random.nextInt(13 * 48) + 96 + 48;
		while (hp.getCollisionCheckerForHall().checkObject(hp.getPlayer(),true) != 999 ||
				hp.getCollisionCheckerForHall().checkEntity(hp.getPlayer(), hp.getMonsters()) != 999) {
			hp.getPlayer().screenX = random.nextInt(12 * 48) + 336 + 48; // Adjust range as needed
			hp.getPlayer().screenY = random.nextInt(13 * 48) + 96 + 48;


		}
	}
}
