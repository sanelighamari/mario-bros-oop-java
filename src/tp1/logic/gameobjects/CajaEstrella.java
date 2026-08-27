package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class CajaEstrella extends GameObject {

	private static final String NAME = Messages.CAJA_ESTRELLA_NAME;
	private static final String SHORT_NAME = Messages.CAJA_ESTRELLA_SHORT_NAME;
	private static final String NAME_U = Messages.CAJA_ESTRELLA_NAME_U;

	
	public CajaEstrella() {
		super();
	}
	
	public CajaEstrella(CajaEstrella original) {
		super(original);
//		this.solid = original.solid;
	}
	
	public CajaEstrella(GameWorld game, Position pos) {
		super(game, pos, NAME);
//		this.solid = true;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void update() {
	
	}

	@Override
	public String getIcon() {
		return "X";
	}

	@Override
	public boolean interactWith(GameItem other) {
		boolean canInteract = other.isInPosition(getPos());
		if (canInteract)
			other.receiveInteraction(this);

		return canInteract;
	}

	@Override
	public GameObject copy() {
		return new CajaEstrella(this);
	}

	@Override
	protected boolean matchParseName(String name) {
		String input = name.toUpperCase();
		return input.equals(NAME) || input.equals(SHORT_NAME) || input.equals(NAME_U);
	}

	@Override
	protected GameObject createInstance(Position pos, GameWorld game) {
		return new CajaEstrella(game, pos);
	}
	
	@Override
	public GameObject parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
		GameObject obj = super.parse(strsObject, game);
		if (strsObject.length > 2 && matchParseName(strsObject[1]))
			throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));
		return obj;
	}

}