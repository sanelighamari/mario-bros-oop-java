package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mushroom extends MovingObject {

	private static final String NAME = Messages.MUSHROOM_NAME;
	private static final String SHORT_NAME = Messages.MUSHROOM_SHORT_NAME;
	private static final String NAME_U = Messages.MUSHROOM_NAME_U;

	private boolean solid;

	public Mushroom(Mushroom original) {
		super(original);
		this.solid = original.isSolid();
	}

	Mushroom() {
		super();
	}

	public Mushroom(GameWorld game, Position pos) {
		super(game, pos, NAME);
		this.direction = Action.RIGHT;
		this.solid = false;
	}

	@Override
	public boolean isSolid() {
		return solid;
	}

	@Override
	public void update() {
		Position posDown = new Position(getPos(), Action.DOWN);
		if (game.isSolid(posDown))
			step();
		else
			fall();

		if (!game.positionIsIn(getPos()))
			dead();
	}

	private void fall() {
		Position tentativePos = new Position(getPos(), Action.DOWN);
		setPos(tentativePos);
	}

	private void step() {
		Position tentativePos = new Position(getPos(), this.direction);
		if (game.isSolid(tentativePos) || !game.positionIsIn(tentativePos))
			this.direction = direction.opposite();
		else
			setPos(tentativePos);
	}

	@Override
	public String getIcon() {
		return Messages.MUSHROOM;
	}

	@Override
	public boolean interactWith(GameItem other) {
		boolean canInteract = other.isInPosition(getPos());
		if (canInteract)
			other.receiveInteraction(this);
		return canInteract;
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
		this.dead();
		return mario.receiveInteraction(this);
	}

	@Override
	protected boolean matchParseName(String name) {
		String input = name.toUpperCase();
		return input.equals(NAME) || input.equals(SHORT_NAME) || input.equals(NAME_U);
	}

	@Override
	protected Mushroom createInstance(Position pos, GameWorld game) {
		return new Mushroom(game, pos);
	}

	@Override
	public GameObject parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
		GameObject obj = super.parse(strsObject, game);
		if (strsObject.length > 3 && matchParseName(strsObject[1]))
			throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));
		return obj;
	}

	@Override
	public GameObject copy() {
		return new Mushroom(this);
	}

}
