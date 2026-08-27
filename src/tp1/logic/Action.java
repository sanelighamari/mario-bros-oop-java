package tp1.logic;

import tp1.exceptions.ActionParseException;
import tp1.view.Messages;

public enum Action {
	LEFT(-1, 0), RIGHT(1, 0), DOWN(0, 1), UP(0, -1), STOP(0, 0);

	private int x;
	private int y;

	private Action(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Action opposite() {
		switch (this) {
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		default:
			return STOP;
		}
	}

	public static Action parse(String str) throws ActionParseException {
		String ac = str.toLowerCase();

		switch (ac) {
		case "right":
		case "r":
			return RIGHT;
		case "left":
		case "l":
			return LEFT;
		case "down":
		case "d":
			return DOWN;
		case "up":
		case "u":
			return UP;
		case "stop":
		case "s":
			return STOP;
		default:
			throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(str));
		}
	}
}
