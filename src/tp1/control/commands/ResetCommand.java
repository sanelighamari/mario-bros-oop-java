package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends AbstractCommand {

	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;

	private int param;

	ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	public ResetCommand(int param) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.param = param;
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		boolean validLevel = false;

		try {
			validLevel = game.reset(param);

			if (!validLevel)
				view.showError(Messages.INVALID_LEVEL_NUMBER);
			else
				view.showGame();
		} catch (GameLoadException gle) {
			throw new CommandExecuteException();
		}

	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		int level = 0;

		if (commandWords.length > 2 && matchCommandName(commandWords[0]))
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

		if (commandWords.length == 1 && matchCommandName(commandWords[0]))
			return new ResetCommand(-3);

		else if (commandWords.length == 2 && matchCommandName(commandWords[0])) {
			try {
				level = Integer.parseInt((commandWords[1]));
			} catch (NumberFormatException nfe) {
				throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]), nfe);
			}

			return new ResetCommand(level);

		} else
			return null;

	}
}
