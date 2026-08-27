package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer {

	private List<GameObject> gameObjects;
	private List<GameObject> buffer;

	public GameObjectContainer() {
		gameObjects = new ArrayList<GameObject>();
		buffer = new ArrayList<GameObject>();
	}

	public void add(GameObject object) {
		this.gameObjects.add(object);
	}

	public void addBuffer(GameObject object) {
		this.buffer.add(object);
	}

	public String postitionToString(Position pos) {
		StringBuilder sb = new StringBuilder();

		for (GameObject object : gameObjects) {
			if (object.isInPosition(pos))
				sb.append(object.getIcon());
		}
		return sb.toString();
	}

	public void update() {

		for (GameObject object : gameObjects) {
			object.update();
			doInteraction(object);
		}

		this.gameObjects.addAll(buffer);
		buffer.clear();

		removeDead();
	}

	public boolean isSolid(Position pos) {
		Boolean isFound = false;

		for (GameObject object : gameObjects) {
			if (object != null && object.isInPosition(pos) && object.isSolid())
				isFound = true;
		}
		return isFound;
	}

	public void removeDead() {
		gameObjects.removeIf(GameObject::isDead);
	}

	public void doInteraction(GameItem item) {
		for (GameObject other : gameObjects) {
			if (other != item) {
				other.interactWith(item);
				item.interactWith(other);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (GameObject o : gameObjects) {
			sb.append(o.toString()).append("\n");
		}
		return sb.toString();
	}

}
