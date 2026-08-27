package tp1.logic.gameobjects;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameWorld;
import tp1.logic.Position;

public interface GameItem {
	public void update();

	public boolean isSolid();

	public boolean isAlive();

	public boolean isDead();

	public boolean isInPosition(Position pos);

	public boolean interactWith(GameItem item);

	public boolean receiveInteraction(Land obj);

	public boolean receiveInteraction(ExitDoor obj);

	public boolean receiveInteraction(Goomba obj);

	public boolean receiveInteraction(Mushroom mushroom);

	public boolean receiveInteraction(Box box);

	public boolean receiveInteraction(Grenade grenade);

	public boolean receiveInteraction(CajaEstrella cajaEstrella);

	public boolean receiveInteraction(SolidIsLava solidIsLava);
	
	public boolean receiveInteraction(Player player);

	public GameObject parse(String[] objWords, GameWorld game) throws GameModelException, CommandExecuteException;

	public boolean isWithinDistance1(Position pos);

}
