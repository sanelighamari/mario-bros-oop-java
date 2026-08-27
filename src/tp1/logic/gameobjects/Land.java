package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Land extends GameObject {

	private static final String NAME = Messages.LAND_NAME;
	private static final String SHORT_NAME = Messages.LAND_SHORT_NAME;
	private static final String NAME_U = Messages.LAND_NAME_U;

	private boolean solid;

	Land() {
		super();
	}

	public Land(Land original) {
		super(original);
		this.solid = original.solid;
	}

	public Land(GameWorld game, Position pos) {
		super(game, pos, NAME);
		this.solid = true;
	}

	@Override
	public String getIcon() {
		return Messages.LAND;
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
		boolean canInteract = other.isInPosition(getPos());
		if (canInteract)
			other.receiveInteraction(this);

		return canInteract;
	}

	@Override
	protected Land createInstance(Position pos, GameWorld game) {
		return new Land(game, pos);
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
		return new Land(this);
	}

}
