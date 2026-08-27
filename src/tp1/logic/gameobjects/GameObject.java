package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public abstract class GameObject implements GameItem {

	private Position pos;
	protected GameWorld game;
	protected boolean isAlive;
	protected final String name;

	GameObject() {
		this.name = "";
	}

	public GameObject(GameObject original) {
		this.isAlive = original.isAlive;
		this.pos = new Position(original.pos);
		this.game = original.game;
		this.name = original.name;
	}

	public GameObject(GameWorld game, Position pos, String name) {
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
		this.name = name;
	}

	protected Position getPos() {
		return this.pos;
	}

	protected void setPos(Position position) {
		this.pos = position;
	}

	@Override
	public boolean isInPosition(Position p) {
		return pos.equals(p);
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public boolean isDead() {
		return !isAlive;
	}

	public void dead() {
		isAlive = false;
	}

	@Override
	public boolean isWithinDistance1(Position pos) {
		return this.pos.distance1(pos);
	}

	public abstract boolean isSolid();

	public abstract void update();

	public abstract String getIcon();

	public abstract boolean interactWith(GameItem item);

//	public abstract void reverseDirection();
	public void reverseDirection() {

	}

	@Override
	public boolean receiveInteraction(Land obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(ExitDoor obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Goomba obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Box obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Grenade grenade) {
		return false;
	}

	@Override
	public boolean receiveInteraction(CajaEstrella ce) {
		return false;
	}

	@Override
	public boolean receiveInteraction(SolidIsLava solidIsLava) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Player player) {
		return false;
	}
	
	public abstract GameObject copy();

	@Override
	public GameObject parse(String strsObject[], GameWorld game) throws OffBoardException, ObjectParseException {
		Position pos;

		try {
			pos = new Position(strsObject[0]);
		} catch (PositionParseException ppe) {
			throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", strsObject)),
					ppe);
		}

		if (!game.positionIsIn(pos)) {
			throw new OffBoardException(Messages.OFF_BOARD_POSITION.formatted(String.join(" ", strsObject)));
		}

		if (strsObject.length < 2 || !matchParseName(strsObject[1])) {
			return null;
		}

		return this.createInstance(pos, game);

	}

	protected abstract boolean matchParseName(String name);

	protected abstract GameObject createInstance(Position pos, GameWorld game);

	@Override
	public String toString() {
		return String.format("%s %s", this.pos.toString(), this.name);
	}

	public void mirrorPosition(int dimX) {
		this.pos = this.pos.mirrorP(dimX);
	}

	public void teleport(Position fin) {
		this.pos = fin;
	}

}
