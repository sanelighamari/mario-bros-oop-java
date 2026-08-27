package tp1.logic;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;

public interface GameModel {

	public boolean isFinished();

	public void update();

	public boolean reset(int i) throws GameLoadException;

	public void exit();

	public void addAction(Action act);

	public void readActions();

	public boolean addObject(String[] objsStr) throws GameModelException;

	public void save(String fileName) throws GameModelException;

	public void load(String fileName) throws GameLoadException;

	public void reverseDirections();

	void mirror();
	
	public void teleport(String[] commandWords) throws GameModelException;

	public void addActionLui(Action act);

}
