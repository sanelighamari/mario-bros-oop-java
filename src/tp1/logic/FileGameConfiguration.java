package tp1.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Luigi;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration {

	private int remainingTime;
	private int points;
	private int numLives;
	private Mario mario;
	private Luigi luigi;
	private List<GameObject> gameObjects;

	public static final GameConfiguration NONE = new FileGameConfiguration();

	public FileGameConfiguration() {
	}

	public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			this.gameObjects = new ArrayList<>();
			String firstLine = reader.readLine();

			parseGameStatus(firstLine);

			readFileConf(reader, game);

		} catch (FileNotFoundException fnfe) {
			String systemMessage = fnfe.getMessage();
			String correctedMessage = systemMessage.replace(fileName + ".txt", fileName);

			FileNotFoundException corrected = new FileNotFoundException(correctedMessage);
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), corrected);
		} catch (IOException ioe) {
			throw new GameLoadException();

		} catch (GameLoadException gle) {
			throw gle;

		} catch (GameModelException gme) {
			throw new GameLoadException(Messages.INVALID_FILE.formatted(fileName), gme);

		} catch (Exception e) {
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), e);
		}
	}

	private void parseGameStatus(String firstLine) throws GameLoadException {
		if (firstLine == null)
			throw new GameLoadException(Messages.INVALID_INIT_CONFIGURATION.formatted(firstLine));

		String[] parts = firstLine.trim().split(" ");
		if (parts.length != 3)
			throw new GameLoadException(Messages.INCORRECT_STATUS.formatted(firstLine));

		try {
			this.remainingTime = Integer.parseInt(parts[0]);
			this.points = Integer.parseInt(parts[1]);
			this.numLives = Integer.parseInt(parts[2]);

		} catch (NumberFormatException nfe) {
			throw new GameLoadException(Messages.INVALID_INIT_CONFIGURATION.formatted(firstLine), nfe);
		}
	}

	private void readFileConf(BufferedReader reader, GameWorld game) throws GameModelException, IOException {
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				String[] tokens = line.split(" ");

				Mario aux = new Mario(game, new Position(0, 0));
				Mario newMario = aux.parse(tokens, game);

				Luigi auxL = new Luigi(game, new Position(0, 0));
				Luigi newLuigi = auxL.parse(tokens, game);

				if (newMario != null) {
					this.mario = newMario;
				} else if (newLuigi != null) {
					this.luigi = newLuigi;
				} else {
					GameObject obj = GameObjectFactory.parse(tokens, game);
					gameObjects.add(obj);
				}

			}
		} catch (OffBoardException | ObjectParseException e) {
			throw e;
		}
	}

	@Override
	public int getRemainingTime() {
		return this.remainingTime;
	}

	@Override
	public int points() {
		return this.points;
	}

	@Override
	public int numLives() {
		return this.numLives;
	}

	@Override
	public Mario getMario() {
		return new Mario(this.mario);
	}
	
	@Override
    public Luigi getLuigi() {
        return this.luigi != null ? new Luigi(this.luigi) : null;
    }

	@Override
	public List<GameObject> getNPCObjects() {
		ArrayList<GameObject> copyList = new ArrayList<>();
		for (GameObject obj : this.gameObjects)
			copyList.add(obj.copy());

		return copyList;
	}

}
