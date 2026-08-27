package tp1.logic;

import java.util.ArrayList;

public class ActionList {

	private static final int MAX_ACTIONS = 4;

	private ArrayList<Action> lista;
	private int contX = 0;
	private int contY = 0;
	private Action actionX;
	private Action actionY;

	public ActionList() {

		this.lista = new ArrayList<Action>();
		this.contX = 0;
		this.contY = 0;
		this.actionX = null;
		this.actionY = null;
	}

	public void addAction(Action act) {
		// up o down
		if (act == Action.UP || act == Action.DOWN) {
			if (actionY != null && isOpposite(act, actionY))
				return; // ignoramos el 2do comando si es opuesto al 1ero

			if (contY < MAX_ACTIONS) {
				lista.add(act);
				actionY = act;
				contY++;
			}
			// left o right
		} else if (act == Action.LEFT || act == Action.RIGHT) {
			if (actionX != null && isOpposite(act, actionX))
				return;

			if (contX < MAX_ACTIONS) {
				lista.add(act);
				actionX = act;
				contX++;
			}

		} else if (act == Action.STOP) {
			lista.add(act);
			contX = MAX_ACTIONS;
			contY = MAX_ACTIONS;
		}
	}

	private boolean isOpposite(Action a1, Action a2) {
		return (a1 == Action.LEFT && a2 == Action.RIGHT) || (a1 == Action.RIGHT && a2 == Action.LEFT)
				|| (a1 == Action.UP && a2 == Action.DOWN) || (a1 == Action.DOWN && a2 == Action.UP);
	}

	public Action getAction(int indice) {
		return lista.get(indice);
	}

	public int getCont() {
		return lista.size();
	}

	public void reset() {
		lista.clear();
		contX = 0;
		contY = 0;
		this.actionX = null;
		this.actionY = null;
	}

}
