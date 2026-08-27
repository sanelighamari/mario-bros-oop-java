package tp1.control.commands;

import java.util.Arrays;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class AddObjectCommand extends AbstractCommand {

	private static final String NAME = Messages.COMMAND_ADD_OBJECT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ADD_OBJECT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ADD_OBJECT_DETAILS;
	private static final String HELP = Messages.COMMAND_ADD_OBJECT_HELP;

	private String[] objsStr;

	public AddObjectCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	public AddObjectCommand(String[] objsStr) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.objsStr = objsStr;
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try {
			game.addObject(objsStr);
		} catch (GameModelException gme) {
			throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, gme);
		}

		view.showGame();
	}

	@Override
	public Command parse(String[] objsStr) throws CommandParseException {
		if (objsStr.length < 3 && matchCommandName(objsStr[0]))
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

		if (objsStr.length >= 3 && matchCommandName(objsStr[0]))
			return new AddObjectCommand(Arrays.copyOfRange(objsStr, 1, objsStr.length));
		else
			return null;
	}

}
