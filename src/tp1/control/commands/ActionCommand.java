package tp1.control.commands;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ActionCommand extends AbstractCommand {

	private static final String NAME = Messages.COMMAND_ACTION_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
	private static final String HELP = Messages.COMMAND_ACTION_HELP;

	private ActionList actList = new ActionList();

	ActionCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	public ActionCommand(ActionList actionList) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.actList = actionList;
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		if (actList.getCont() == 0)
			throw new CommandExecuteException(Messages.ERROR_ACTION_LIST_EMPTY);

		for (int i = 0; i < actList.getCont(); i++) {
			game.addAction(actList.getAction(i));
		}

		game.update();
		actList.reset();
		view.showGame();
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if (commandWords.length < 2 && matchCommandName(commandWords[0]))
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

		if (commandWords.length >= 2 && matchCommandName(commandWords[0])) {
			for (int i = 1; i < commandWords.length; i++) {
				Action act;
				try {
					act = Action.parse(commandWords[i]);

				} catch (ActionParseException ape) {
					act = null;
				}

				if (act != null)
					actList.addAction(act);
			}
			return new ActionCommand(actList);
		} else
			return null;

	}

}
