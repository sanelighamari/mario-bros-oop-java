package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Box extends GameObject {

	private static final String NAME = Messages.BOX_NAME;
	private static final String SHORT_NAME = Messages.BOX_SHORT_NAME;
	private static final String NAME_U = Messages.BOX_NAME_U;
	private static final int BOX_POINTS = 50;

	private boolean solid;
	private boolean isEmpty;

	Box() {
		super();
	}

	public Box(Box original) {
		super(original);
		this.solid = original.isSolid();
		this.isEmpty = original.isEmpty;
	}

	public Box(GameWorld game, Position pos) {
		super(game, pos, NAME);
		this.solid = true;
		this.isEmpty = false;
	}

	public String getIcon() {
		return isEmpty ? Messages.EMPTY_BOX : Messages.BOX;
	}

	@Override
	public boolean isSolid() {
		return solid;
	}

	@Override
	public void update() {
	}

	@Override
	public boolean interactWith(GameItem other) {
		Position posUp = new Position(getPos(), Action.UP);
		Position posDown = new Position(getPos(), Action.DOWN);

		boolean canInteract = other.isInPosition(posDown) && !this.isEmpty && other.receiveInteraction(this);

		if (canInteract) {
			this.game.addBuffer(new Mushroom(game, posUp));
			this.isEmpty = true;
			this.game.addPoints(BOX_POINTS);
		}
		return canInteract;
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
		return true;
	}

	@Override
	protected Box createInstance(Position pos, GameWorld game) {
		return new Box(game, pos);
	}

	@Override
	protected boolean matchParseName(String name) {
		String input = name.toUpperCase();
		return input.equals(NAME) || input.equals(SHORT_NAME) || input.equals(NAME_U);
	}

	@Override
	public GameObject parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
		Box box = (Box) super.parse(strsObject, game);
		if (strsObject.length > 3 && matchParseName(strsObject[1]))
			throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));

		if (strsObject.length > 2 && matchParseName(strsObject[1])) {
			box.isEmpty = switch (strsObject[2].toLowerCase()) {
			case "full", "f" -> false;
			case "empty", "e" -> true;
			default ->
				throw new ObjectParseException(Messages.INVALID_BOX_STATUS.formatted(String.join(" ", strsObject)));
			};
		} else if (strsObject.length == 2)
			isEmpty = false;

		return box;
	}

	@Override
	public String toString() {
		String parentString = super.toString();
		String status = isEmpty ? "Empty" : "Full";
		return String.format("%s %s", parentString, status);
	}

	@Override
	public GameObject copy() {
		return new Box(this);
	}

}