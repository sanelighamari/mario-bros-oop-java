package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class SolidIsLava extends GameObject {

	private static final String NAME = Messages.LAVA_NAME;
	private static final String SHORT_NAME = Messages.LAVA_SHORT_NAME;
	private static final String NAME_U = Messages.LAVA_NAME_U;

	private int cont;
	private boolean hasDamaged = false;
	
	public SolidIsLava() {
		super();
	}
	
	public SolidIsLava(SolidIsLava original) {
		super(original);
		cont = original.cont;
	}
	
	public SolidIsLava(GameWorld game, Position pos) {
		super(game, pos, NAME);
		cont = 1;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void update() {
		if (cont == 3) {
			cont = 1;
		} else {
			cont++;
		}
		this.hasDamaged = false;
	}

	@Override
	public String getIcon() {
		return "X";
	}

	@Override
	public boolean interactWith(GameItem other) {
		if (cont == 3 && other.isAlive() && !hasDamaged) {
			if (other.receiveInteraction(this)) 
				hasDamaged = true;
		}
		return false;
	}

	@Override
	public GameObject copy() {
		return new SolidIsLava(this);
	}

	@Override
	protected boolean matchParseName(String name) {
		String input = name.toUpperCase();
		return input.equals(NAME) || input.equals(SHORT_NAME) || input.equals(NAME_U);
	}

	@Override
	protected GameObject createInstance(Position pos, GameWorld game) {
		return new SolidIsLava(game, pos);
	}
	
	@Override
	public GameObject parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
		GameObject obj = super.parse(strsObject, game);
		if (strsObject.length > 2 && matchParseName(strsObject[1]))
			throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));
		return obj;
	}

}