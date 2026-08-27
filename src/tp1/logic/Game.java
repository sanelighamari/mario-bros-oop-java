package tp1.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

public class Game implements GameModel, GameStatus, GameWorld {

	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;

	private GameObjectContainer gameObjects;
	private Mario mario;

	private int nLevel;
	private int remainingTime;
	private int numLives;
	private int points;
	private boolean active;
	private boolean exit;
	private GameConfiguration fileloader = FileGameConfiguration.NONE;

	public Game(int nLevel) {

		switch (nLevel) {
		case 0:
			initLevel0();
			break;
		case 1:
			initLevel1();
			break;
		case -1:
			initLevelCreativeMode();
			break;
		case 2:
			initLevel2();
			break;
		default:
			initLevel0();
		}
	}

	private void initLevel0() {
		this.nLevel = 0;
		this.remainingTime = 100;
		this.numLives = 3;
		this.points = 0;
		this.active = true;
		this.exit = false;

		gameObjects = new GameObjectContainer();

		for (int row = 0; row < 15; row++) {
			gameObjects.add(new Land(this, new Position(row, 13)));
			gameObjects.add(new Land(this, new Position(row, 14)));
		}

		gameObjects.add(new Land(this, new Position(9, Game.DIM_Y - 3)));
		gameObjects.add(new Land(this, new Position(12, Game.DIM_Y - 3)));

		for (int row = 17; row < Game.DIM_X; row++) {
			gameObjects.add(new Land(this, new Position(row, Game.DIM_Y - 2)));
			gameObjects.add(new Land(this, new Position(row, Game.DIM_Y - 1)));
		}

		gameObjects.add(new Land(this, new Position(2, 9)));
		gameObjects.add(new Land(this, new Position(5, 9)));
		gameObjects.add(new Land(this, new Position(6, 9)));
		gameObjects.add(new Land(this, new Position(7, 9)));
		gameObjects.add(new Land(this, new Position(6, 5)));

		int tamX = 8;
		int posIniX = Game.DIM_X - 3 - tamX;
		int posIniY = Game.DIM_Y - 3;

		for (int row = 0; row < tamX; row++) {
			for (int col = 0; col < row + 1; col++) {
				gameObjects.add(new Land(this, new Position(posIniX + row, posIniY - col)));
			}
		}

		this.mario = new Mario(this, new Position(0, Game.DIM_Y - 3));

		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(19, 0)));

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_X - 1, Game.DIM_Y - 3)));
	}

	private void initLevel1() {
		this.nLevel = 1;
		this.remainingTime = 100;
		this.numLives = 3;
		this.points = 0;
		this.active = true;
		this.exit = false;

		gameObjects = new GameObjectContainer();

		for (int row = 0; row < 15; row++) {
			gameObjects.add(new Land(this, new Position(row, 13)));
			gameObjects.add(new Land(this, new Position(row, 14)));
		}

		gameObjects.add(new Land(this, new Position(9, Game.DIM_Y - 3)));
		gameObjects.add(new Land(this, new Position(12, Game.DIM_Y - 3)));

		for (int row = 17; row < Game.DIM_X; row++) {
			gameObjects.add(new Land(this, new Position(row, Game.DIM_Y - 2)));
			gameObjects.add(new Land(this, new Position(row, Game.DIM_Y - 1)));
		}

		gameObjects.add(new Land(this, new Position(2, 9)));
		gameObjects.add(new Land(this, new Position(5, 9)));
		gameObjects.add(new Land(this, new Position(6, 9)));
		gameObjects.add(new Land(this, new Position(7, 9)));
		gameObjects.add(new Land(this, new Position(6, 5)));

		int tamX = 8;
		int posIniX = Game.DIM_X - 3 - tamX;
		int posIniY = Game.DIM_Y - 3;

		for (int row = 0; row < tamX; row++) {
			for (int col = 0; col < row + 1; col++) {
				gameObjects.add(new Land(this, new Position(posIniX + row, posIniY - col)));
			}
		}

		this.mario = new Mario(this, new Position(0, Game.DIM_Y - 3));

		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(19, 0)));
		gameObjects.add(new Goomba(this, new Position(6, 4)));
		gameObjects.add(new Goomba(this, new Position(6, 12)));
		gameObjects.add(new Goomba(this, new Position(8, 12)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(11, 12)));
		gameObjects.add(new Goomba(this, new Position(14, 12)));

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_X - 1, Game.DIM_Y - 3)));
	}

	private void initLevelCreativeMode() {
		this.nLevel = -1;
		this.remainingTime = 100;
		this.numLives = 3;
		this.points = 0;
		this.active = true;
		this.exit = false;

		gameObjects = new GameObjectContainer();
	}

	private void initLevel2() {
		this.nLevel = 2;
		this.remainingTime = 100;
		this.numLives = 3;
		this.points = 0;
		this.active = true;
		this.exit = false;

		gameObjects = new GameObjectContainer();

		for (int row = 0; row < 15; row++) {
			gameObjects.add(new Land(this, new Position(row, 13)));
			gameObjects.add(new Land(this, new Position(row, 14)));
		}

		gameObjects.add(new Land(this, new Position(9, Game.DIM_Y - 3)));
		gameObjects.add(new Land(this, new Position(12, Game.DIM_Y - 3)));

		for (int row = 17; row < Game.DIM_X; row++) {
			gameObjects.add(new Land(this, new Position(row, Game.DIM_Y - 2)));
			gameObjects.add(new Land(this, new Position(row, Game.DIM_Y - 1)));
		}

		gameObjects.add(new Land(this, new Position(2, 9)));
		gameObjects.add(new Land(this, new Position(5, 9)));
		gameObjects.add(new Land(this, new Position(6, 9)));
		gameObjects.add(new Land(this, new Position(7, 9)));
		gameObjects.add(new Land(this, new Position(6, 5)));

		int tamX = 8;
		int posIniX = Game.DIM_X - 3 - tamX;
		int posIniY = Game.DIM_Y - 3;

		for (int row = 0; row < tamX; row++) {
			for (int col = 0; col < row + 1; col++) {
				gameObjects.add(new Land(this, new Position(posIniX + row, posIniY - col)));
			}
		}

		this.mario = new Mario(this, new Position(0, Game.DIM_Y - 3));

		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(19, 0)));
		gameObjects.add(new Goomba(this, new Position(6, 4)));
		gameObjects.add(new Goomba(this, new Position(6, 12)));
		gameObjects.add(new Goomba(this, new Position(8, 12)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(11, 12)));
		gameObjects.add(new Goomba(this, new Position(14, 12)));

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_X - 1, Game.DIM_Y - 3)));

		// Level 2:
		gameObjects.add(new Box(this, new Position(4, 9)));

		gameObjects.add(new Mushroom(this, new Position(8, 12)));
		gameObjects.add(new Mushroom(this, new Position(20, 2)));
	}

	// Los Metodos de Interfaz GameModel:

	@Override
	public boolean isFinished() {
		return (!getActive() || gameEnds() || isExit());
	}

	@Override
	public void update() {
		this.remainingTime--;
		gameObjects.update();
	}

	@Override
	public boolean reset(int i) {
		int auxLives = numLives();
		int auxPoints = points();
		boolean isValid = true;
		if (fileloader == FileGameConfiguration.NONE || i != -3) {
			switch (i) {
			case 0:
				initLevel0();
				break;

			case 1:
				initLevel1();
				break;

			case -1:
				initLevelCreativeMode();
				break;

			case -3:
				reset(this.nLevel);
				break;

			case 2:
				initLevel2();
				break;

			default:
				isValid = false;
			}

			if (isValid && this.nLevel != -1) {
				this.points = auxPoints;
				this.numLives = auxLives;
			}
			return isValid;
		} else {
			restoreFromFileLoader(this.fileloader);
			this.points = auxPoints;
			this.numLives = auxLives;

			return true;
		}

	}

	@Override
	public void exit() {
		this.exit = true;
	}

	@Override
	public void addAction(Action act) {
		this.mario.addAction(act);
	}

	@Override
	public void readActions() {
		this.mario.readActions();
	}

	@Override
	public boolean addObject(String[] objsStr) throws OffBoardException, ObjectParseException {
		Mario newMario = new Mario(this, new Position(0, 0));
		newMario = newMario.parse(objsStr, this);
		GameObject newObject = newMario;
		if (newMario == null) {
			GameObject obj = GameObjectFactory.parse(objsStr, this);
			gameObjects.add(obj);
		} else {
			gameObjects.add(newObject);
			this.mario = newMario;
		}
		return true;
	}

	public void save(String fileName) throws GameModelException {
		try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
			pw.printf("%d %d %d%n", this.remainingTime, this.points, this.numLives);
			pw.printf(gameObjects.toString());

		} catch (IOException e) {
			throw new GameModelException(Messages.ERROR_FILE.formatted(fileName), e);
		}
	}

	public void load(String fileName) throws GameLoadException {
		GameConfiguration fileConfiguration = new FileGameConfiguration(fileName, this);
		this.restoreFromFileLoader(fileConfiguration);

		this.fileloader = fileConfiguration;
	}

	private void restoreFromFileLoader(GameConfiguration fileConfiguration) {
		this.remainingTime = fileConfiguration.getRemainingTime();
		this.points = fileConfiguration.points();
		this.numLives = fileConfiguration.numLives();

		this.gameObjects = new GameObjectContainer();
		for (GameObject obj : fileConfiguration.getNPCObjects()) {
			this.gameObjects.add(obj);
		}

		this.mario = fileConfiguration.getMario();
		this.gameObjects.add(this.mario);
	}

	// Los Metodos de Interfaz GameStatus:

	@Override
	public boolean playerWins() {
		return !this.active && this.numLives > 0;
	}

	@Override
	public boolean playerLoses() {
		return numLives() <= 0 || remainingTime() <= 0;
	}

	@Override
	public int remainingTime() {
		return this.remainingTime;
	}

	@Override
	public int points() {
		return this.points;
	}

	@Override
	public String positionToString(int col, int row) {
		return gameObjects.postitionToString(new Position(col, row));
	}

	@Override
	public int numLives() {
		return this.numLives;
	}

	// Los Metodos de Interfaz GameWorld:

	@Override
	public boolean isSolid(Position pos) {
		return gameObjects.isSolid(pos);
	}

	@Override
	public void addPoints(int point) {
		this.points += point;
	}

	@Override
	public boolean isExit() {
		return exit;
	}

	@Override
	public void marioExited() {
		this.points += this.remainingTime * 10;
		this.remainingTime = 0;
		this.active = false;
	}

	@Override
	public void addBuffer(GameObject gameObject) {
		gameObjects.addBuffer(gameObject);
	}

	@Override
	public void doInteraction(GameItem item) {
		this.gameObjects.doInteraction(item);
	}

	@Override
	public boolean positionIsIn(Position pos) {
		return pos.isInBoard(pos, DIM_X, DIM_Y);
	}

	@Override
	public void marioDead() {
		loseLive();
		if (numLives() > 0)
			reset(-3);

	}

	// Los Metodos auxiliares:

	private void loseLive() {
		this.numLives--;
	}

	private boolean getActive() {
		return this.active;
	}

	private boolean gameEnds() {
		if (playerLoses() || playerWins())
			return true;
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(remainingTime + " " + points + " " + numLives + "\n");
		sb.append(gameObjects.toString());

		return sb.toString();
	}

}
