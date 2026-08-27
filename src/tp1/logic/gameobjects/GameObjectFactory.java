package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.view.Messages;

public class GameObjectFactory {

	private static final List<GameObject> availableObjects = Arrays.asList(new Land(), new ExitDoor(), new Goomba(),
			new Mario(), new Mushroom(), new Box());

	public static GameObject parse(String objWords[], GameWorld game) throws OffBoardException, ObjectParseException {
		for (GameObject o : availableObjects) {
			GameObject aux = o.parse(objWords, game);
			if (aux != null) {
				return aux;
			}
		}
		throw new ObjectParseException(Messages.UNKNOWN_OBJ.formatted(String.join(" ", objWords)));
	}

}
