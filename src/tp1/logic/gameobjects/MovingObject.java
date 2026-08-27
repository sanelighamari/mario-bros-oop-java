package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public abstract class MovingObject extends GameObject {

	protected boolean isFalling;
	protected Action direction;
	protected final String name;

	MovingObject() {
		super();
		this.name = "";
	}

	MovingObject(MovingObject original) {
		super(original);
		this.isFalling = original.isFalling;
		this.direction = original.direction;
		this.name = original.name;
	}

	public MovingObject(GameWorld game, Position pos, String name) {
		super(game, pos, name);
		this.isFalling = false;
		this.direction = Action.RIGHT;
		this.name = name;
	}

	@Override
	public GameObject parse(String strObjects[], GameWorld game) throws OffBoardException, ObjectParseException {
		GameObject obj = super.parse(strObjects, game);

		if (obj != null) {
			if (strObjects.length == 2)
				((MovingObject) obj).direction = Action.RIGHT;
			else
				try {
					Action ac = Action.parse(strObjects[2]);

					if (ac == Action.DOWN || ac == Action.UP)
						throw new ObjectParseException(
								Messages.INVALID_MOV_OBJ_DIR.formatted(String.join(" ", strObjects)));

					((MovingObject) obj).direction = ac;
				} catch (ActionParseException ape) {
					throw new ObjectParseException(Messages.UNKNOWN_MOV_OBJ_DIR.formatted(String.join(" ", strObjects)),
							ape);
				}
		} else
			obj = null;

		return obj;
	}

	@Override
	public String toString() {
		String parentStr = super.toString();

		if (direction != Action.STOP) {
			return String.format("%s %s", parentStr, direction.toString().toUpperCase());
		}
		return parentStr;
	}
}
