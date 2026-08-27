package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Goomba extends MovingObject {

	private static final String NAME = Messages.GOOMBA_NAME;
	private static final String SHORT_NAME = Messages.GOOMBA_SHORT_NAME;
	private static final String NAME_U = Messages.GOOMBA_NAME_U;
	private static final int GOOMBA_POINTS = 100;

	private boolean solid;

	Goomba() {
		super();
	}

	public Goomba(Goomba original) {
		super(original);
		this.direction = original.direction;
		this.solid = original.isSolid();
	}

	public Goomba(GameWorld game, Position pos) {
		super(game, pos, NAME);
		this.direction = Action.LEFT;
		this.solid = false;
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
		return Messages.GOOMBA;
	}

	@Override
	public boolean isSolid() {
		return solid;
	}

	@Override
	public boolean interactWith(GameItem other) {
		boolean canInteract = other.isInPosition(getPos()) && this.isAlive();
		if (canInteract) {
			if (other.receiveInteraction(this)) {
				this.dead();
				game.addPoints(GOOMBA_POINTS);
			}
		}

		return canInteract;
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
		return true;
	}

	@Override
	protected boolean matchParseName(String name) {
		String input = name.toUpperCase();
		return input.equals(NAME) || input.equals(SHORT_NAME) || input.equals(NAME_U);
	}

	@Override
	protected Goomba createInstance(Position pos, GameWorld game) {
		return new Goomba(game, pos);
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
		return new Goomba(this);
	}

}
