package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class LoadCommand extends AbstractCommand {

	private static final String NAME = Messages.COMMAND_LOAD_NAME;
	private static final String SHORTCUT = Messages.COMMAND_LOAD_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_LOAD_DETAILS;
	private static final String HELP = Messages.COMMAND_LOAD_HELP;

	private String fileName;

	LoadCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	public LoadCommand(String file) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.fileName = file;

	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try {
			game.load(fileName);
			view.showGame();
		} catch (GameLoadException gme) {
			throw new CommandExecuteException(Messages.ERROR_UNABLE_TO_LOAD.formatted(fileName), gme);
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {

		if (!matchCommandName(commandWords[0])) {
			return null;
		}

		if (commandWords.length != 2) {
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}

		else {
			this.fileName = commandWords[1];
			if (this.fileName.isEmpty())
				throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);

			return new LoadCommand(fileName);
		}

	}

}
