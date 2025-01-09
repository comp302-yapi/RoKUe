package strategy;

import java.util.ArrayList;
import java.util.Random;

import containers.HallContainer;
import object.OBJ_LuringGem;
import object.SuperObject;
import views.HallPanel;

public class TeleportRuneStrategy {
	
	public static void teleportRune(HallPanel hp) {

		Random random = new Random();
		ArrayList<SuperObject> objs = null;

		switch(hp.currentHall) {
			case HallOfWater -> {
				objs = HallContainer.getHallOfWater().objects;
			}
			case HallOfEarth -> {
				objs = HallContainer.getHallOfEarth().objects;
			}
			case HallOfFire -> {
				objs = HallContainer.getHallOfFire().objects;
			}
			case HallOfAir -> {
				objs = HallContainer.getHallOfAir().objects;
			}
		}

		SuperObject keyObject = null;
		for(SuperObject obj:objs) {
			if(obj.hasRune) {
				keyObject = obj;
				break;
			}
		}

		if(keyObject != null) {
			keyObject.hasRune = false;
			SuperObject randomObj = objs.get(random.nextInt(objs.size()));
			while(randomObj instanceof OBJ_LuringGem) {
				randomObj = objs.get(random.nextInt(objs.size()));
			}
			randomObj.hasRune = true;

		}
	}
}
