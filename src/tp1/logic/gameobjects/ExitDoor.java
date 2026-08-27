package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject {

	private static final String NAME = Messages.EXITDOOR_NAME;
	private static final String SHORT_NAME = Messages.EXITDOOR_SHORT_NAME;
	private static final String NAME_U = Messages.EXITDOOR_NAME_U;

	private boolean solid;

	public ExitDoor(ExitDoor original) {
		super(original);
		this.solid = original.isSolid();
	}

	ExitDoor() {
		super();
	}

	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos, NAME);
		this.solid = false;
	}

	@Override
	public String getIcon() {
		return Messages.EXIT_DOOR;
	}

	@Override
	public boolean isSolid() {
		return solid;
	}

	@Override
	public boolean interactWith(GameItem other) {
		boolean canInteract = other.isInPosition(getPos());
		if (canInteract)
			other.receiveInteraction(this);
		return canInteract;
	}

	@Override
	public boolean receiveInteraction(Mario obj) {
		game.marioExited();
		return true;
	}

	@Override
	public void update() {
	}

	@Override
	protected ExitDoor createInstance(Position pos, GameWorld game) {
		return new ExitDoor(game, pos);
	}

	@Override
	protected boolean matchParseName(String name) {
		String input = name.toUpperCase();
		return input.equals(NAME) || input.equals(SHORT_NAME) || input.equals(NAME_U);
	}

	@Override
	public GameObject parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
		GameObject obj = super.parse(strsObject, game);
		if (strsObject.length > 2 && matchParseName(strsObject[1]))
			throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));
		return obj;
	}

	@Override
	public GameObject copy() {
		return new ExitDoor(this);
	}

}
