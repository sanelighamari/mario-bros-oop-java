package tp1.control.commands;

import java.util.Arrays;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class TeleportCommand extends AbstractCommand{

	private static final String NAME = Messages.COMMAND_TELE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_TELE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_TELE_DETAILS;
	private static final String HELP = Messages.COMMAND_TELE_HELP;
	
	private String[] commandWords;
	
	public TeleportCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	public TeleportCommand(String[] commandWords) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.commandWords = commandWords;
	}

	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try {
			game.teleport(commandWords);
		} catch (GameModelException gme) {
			throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, gme);
		}

		view.showGame();
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if (commandWords.length < 3 && matchCommandName(commandWords[0]))
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

		if (commandWords.length == 3 && matchCommandName(commandWords[0]))
			return new TeleportCommand(Arrays.copyOfRange(commandWords, 1, commandWords.length));
		else
			return null;
	}

}