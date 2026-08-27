package tp1.logic;

import tp1.exceptions.GameLoadException;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

public interface GameWorld {

	public boolean isSolid(Position pos);

	public void addPoints(int point);

	public boolean isExit();

	public void marioExited();

	public void doInteraction(GameItem item);

	public boolean positionIsIn(Position pos);

	public void marioDead() throws GameLoadException;

	public void addBuffer(GameObject gameObject);
}
