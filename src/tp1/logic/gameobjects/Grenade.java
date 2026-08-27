package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Grenade extends MovingObject {

	private static final String NAME = Messages.GRENADE_NAME;
	private static final String SHORT_NAME = Messages.GRENADE_SHORT_NAME;
	private static final String NAME_U = Messages.GRENADE_NAME_U;

	private int cont;

	Grenade() {
		super();
	}
	
	public Grenade(Grenade original) {
		super(original);
		this.direction = original.direction;
		this.cont = original.cont;
	}

	public Grenade(GameWorld game, Position pos) {
		super(game, pos, NAME);
		this.cont = 1;
//		this.direction = Action.LEFT;
	}
	
	public Grenade(GameWorld game, Position pos, Action dir) {
		super(game, pos, NAME);
		this.direction = dir;
		this.cont = 1;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public void update() {
		if (cont == 3) {
			dead();
		} else {
			Position posDown = new Position(getPos(), Action.DOWN);
			if (game.isSolid(posDown))
				step();
			else
				fall();
	
			if (!game.positionIsIn(getPos()))
				dead();
			
			this.cont++;
		}
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
		return "X";
	}

	@Override
	public boolean interactWith(GameItem other) {
		boolean canInteract = this.cont == 3 && other.isWithinDistance1(this.getPos());
		if (canInteract) {
			other.receiveInteraction(this);
		}
			
		return canInteract;
	}

	@Override
	public GameObject copy() {
		return new Grenade(this);
	}

	@Override
	protected boolean matchParseName(String name) {
		String input = name.toUpperCase();
		return input.equals(NAME) || input.equals(SHORT_NAME) || input.equals(NAME_U);
	}

	@Override
	protected Grenade createInstance(Position pos, GameWorld game) {
		return new Grenade(game, pos);
	}

	@Override
	public GameObject parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
		Grenade grenadeParse = (Grenade) super.parse(strsObject, game);
		if (strsObject.length > 4 && matchParseName(strsObject[1]))
			throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));

		if (grenadeParse != null) {
			if (strsObject.length == 2)
				grenadeParse.direction = Action.STOP;
			
			if (strsObject.length == 4) {
				try {
					grenadeParse.cont = Integer.parseInt(strsObject[3]);
				} catch (NumberFormatException e) {
					throw new ObjectParseException();
				}
			}
		}
		return grenadeParse;
	}

	@Override
	public boolean receiveInteraction(Grenade grenade) {
		return false;
	}
	
	@Override
	public String toString() {
	    return super.toString() + " " + this.cont; 
	}
	
}