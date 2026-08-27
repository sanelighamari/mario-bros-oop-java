package tp1.logic;

public interface GameStatus {

	public boolean playerWins();

	public boolean playerLoses();

	public int remainingTime();

	public int points();

	public String positionToString(int col, int row);

	public int numLives();
}
