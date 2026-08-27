package tp1.logic.gameobjects;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario extends MovingObject {

	private static final String NAME = Messages.MARIO_NAME;
	private static final String SHORT_NAME = Messages.MARIO_SHORT_NAME;
	private static final String NAME_U = Messages.MARIO_NAME_U;

	private boolean solid;
	private boolean big;
	private ActionList actList;
	protected boolean wasFalling;
	private boolean deadThisTurn;
	private boolean goingUp;

	Mario() {
		super();
	}

	public Mario(Mario original) {
		super(original);
		this.solid = original.solid;
		this.big = original.big;
		this.actList = new ActionList();
		this.wasFalling = original.wasFalling;
		this.deadThisTurn = original.deadThisTurn;
		this.goingUp = original.goingUp;
	}

	public Mario(GameWorld game, Position pos) {
		super(game, pos, NAME);
		this.solid = false;
		this.big = true;
		this.actList = new ActionList();
		this.wasFalling = false;
		this.deadThisTurn = false;
		this.goingUp = false;
	}

	@Override
	public void update() {
		deadThisTurn = false;

		checkGround(getPos());

		// Verifico si la lista de acciones está vacía
		if (actList.getCont() > 0) {
			readActions();
			actList.reset();
		} else if (isFalling)
			fall();
		else
			step();

		actList.reset();

		if (!game.positionIsIn(getPos()))
			dead();
	}

	private void fall() {
		Position tentativePos = new Position(getPos(), Action.DOWN);
		setPos(tentativePos);
		checkGround(getPos());
		wasFalling = true;
	}

	private void step() {
		Position tentativePos = new Position(getPos(), this.direction);
		if (game.isSolid(tentativePos) || !game.positionIsIn(tentativePos))
			this.direction = direction.opposite();
		else
			setPos(tentativePos);

		this.wasFalling = false;
		checkGround(getPos());
	}

	@Override
	public boolean isInPosition(Position pos) {
		if (this.big) {
			Position upPos = new Position(getPos(), Action.UP);
			return getPos().equals(pos) || upPos.equals(pos);
		}
		return getPos().equals(pos);
	}

	public void addAction(Action act) {
		actList.addAction(act);
	}

	public void readActions() {
		for (int i = 0; i < actList.getCont(); i++) {
			Action act = actList.getAction(i);
			if (act != null) {
				doAction(act);
				game.doInteraction(this);

				goingUp = false;
				checkGround(getPos());
			}
		}
		actList.reset();
	}

	private void isGoingDown() {
		this.wasFalling = true;
		Position nextPos = new Position(getPos(), Action.DOWN);
		while (!game.isSolid(nextPos) && !isDead()) {
			if (!game.positionIsIn(nextPos))
				dead();
			setPos(nextPos);
			nextPos = new Position(getPos(), Action.DOWN);
			game.doInteraction(this);
		}
		isFalling = false; // Está en el suelo
		this.wasFalling = true;
	}

	private void doAction(Action act) {
		Position tentativePos = new Position(getPos(), act);

		if (checkforWalls(act)) {

			if (isFalling) { // Verifico si está en el aire
				if (act == Action.DOWN)
					isGoingDown();
				else
					fall();
			} else
				doUserAction(act, tentativePos);

		} else {
			if (act == Action.UP)
				goingUp = true;

			if (act == Action.DOWN) { // Si ya está en la tierra
				isGoingDown();
				this.direction = Action.STOP;
			} else if (act == Action.LEFT || act == Action.RIGHT) {
				this.direction = direction.opposite();
				actList.reset(); // Choca con un muro
			}
		}
	}

	private boolean checkforWalls(Action act) {
		boolean isWall = false;
		Position NextPosBody = new Position(getPos(), act);

		if (this.big) {
			Position NextPosHead = new Position(NextPosBody, Action.UP);

			if (!game.isSolid(NextPosBody) && game.positionIsIn(NextPosBody) && !game.isSolid(NextPosHead)
					&& game.positionIsIn(NextPosHead))
				isWall = true;

		} else if (!game.isSolid(NextPosBody) && game.positionIsIn(NextPosBody))
			isWall = true;

		return isWall;
	}

	private void doUserAction(Action act, Position tentativePos) {
		if (act == Action.STOP) {
			this.direction = act;
		} else if (act == Action.LEFT || act == Action.RIGHT) {
			this.direction = act;
			setPos(tentativePos);
		} else if (act == Action.UP) {
			setPos(tentativePos);
			goingUp = true;
		} else
			isGoingDown();
	}

	private void checkGround(Position pos) {
		Position downPos = new Position(pos, Action.DOWN);
		if (game.isSolid(downPos))
			isFalling = false;
		else
			isFalling = true;
	}

	@Override
	public boolean interactWith(GameItem other) {
		Position currentPos = getPos();
		Position upPos = new Position(getPos(), Action.UP);

		boolean canInteract;
		if (this.big)
			canInteract = other.isInPosition(currentPos) || other.isInPosition(upPos);
		else
			canInteract = other.isInPosition(currentPos);

		if (canInteract && other.isAlive() && !deadThisTurn) {
			other.receiveInteraction(this);
		}

		return canInteract;
	}

	@Override
	public boolean receiveInteraction(Goomba goomba) {
		boolean interact = false;
		if (!deadThisTurn) {
			if (!wasFalling && !isFalling) {
				if (this.big) {
					this.big = false;
					interact = true;
				} else {
					dead();
					deadThisTurn = true;
					interact = true;
				}
			} else
				interact = true;
		}
		return interact;
	}

	@Override
	public boolean receiveInteraction(Mushroom mushroom) {
		boolean interact = true;
		this.big = true;
		return interact;
	}

	@Override
	public String getIcon() {
		switch (direction) {
		case RIGHT:
			return Messages.MARIO_RIGHT;
		case LEFT:
			return Messages.MARIO_LEFT;
		default:
			return Messages.MARIO_STOP;
		}
	}

	@Override
	public boolean receiveInteraction(Box box) {
		return this.goingUp;
	}

	@Override
	public boolean isSolid() {
		return solid;
	}

	@Override
	public void dead() {
		if (!isDead()) {
			super.dead();
			actList.reset();
			try {
				game.marioDead();
			} catch (GameLoadException e) {
			}
		}
	}

	@Override
	protected Mario createInstance(Position pos, GameWorld game) {
		return new Mario(game, pos);
	}

	@Override
	public Mario parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
		Mario marioParse = (Mario) super.parse(strsObject, game);
		if (strsObject.length > 4 && matchParseName(strsObject[1]))
			throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));

		if (marioParse != null) {
			if (strsObject.length == 2 || strsObject.length == 3)
				marioParse.big = true;
			else {
				marioParse.big = switch (strsObject[3].toLowerCase()) {
				case "big", "b" -> true;
				case "small", "s" -> false;
				default ->
					throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", strsObject)));
				};
			}
		}
		return marioParse;

	}

	@Override
	protected boolean matchParseName(String name) {
		String input = name.toUpperCase();
		return input.equals(NAME) || input.equals(SHORT_NAME) || input.equals(NAME_U);
	}

	@Override
	public String toString() {
		String superStr = super.toString();
		String sizeStr = big ? "Big" : "Small";

		return String.format("%s %s", superStr, sizeStr);
	}

	@Override
	public GameObject copy() {
		return new Mario(this);
	}

}
