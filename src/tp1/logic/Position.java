package tp1.logic;

import java.util.Objects;

import tp1.exceptions.PositionParseException;
import tp1.view.Messages;

public class Position {

	private final int col;
	private final int row;

	public Position(int col, int row) {
		this.col = col;
		this.row = row;
	}

	public Position(Position pos, Action act) {
		this.row = pos.row + act.getY();
		this.col = pos.col + act.getX();
	}

	public Position(Position pOriginal) {
		this.col = pOriginal.col;
		this.row = pOriginal.row;
	}

	public Position(String pos) throws PositionParseException {
		try {
			String clean = pos.trim().replace("(", "").replace(")", "").replace(" ", "");
			String[] coords = clean.split(",");

			if (coords.length != 2)
				throw new PositionParseException(Messages.INVALID_POSITION.formatted(pos));

			this.col = Integer.parseInt(coords[1]);
			this.row = Integer.parseInt(coords[0]);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new PositionParseException(Messages.INVALID_POSITION.formatted(pos), e);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return col == other.col && row == other.row;
	}

	public Position move(Action dir) {
		return new Position(this.col + dir.getX(), this.row + dir.getY());
	}

	public boolean isInBoard(Position pos, int dimX, int dimY) {
		return pos.col >= 0 && pos.col < dimX && pos.row >= 0 && pos.row < dimY;
	}

	@Override
	public int hashCode() {
		return Objects.hash(col, row);
	}

	@Override
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
