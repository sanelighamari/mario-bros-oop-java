package tp1.view;

import tp1.util.MyStringUtils;

public class Messages {

	public static final String VERSION = "3.0";

	public static final String GAME_NAME = "MarioBross";

	public static final String USAGE = "Usage: %s [<level>]".formatted(GAME_NAME);

	public static final String WELCOME = String.format("%s %s%n", GAME_NAME, VERSION);

	public static final String LEVEL_NOT_A_NUMBER = "The level must be a number";
	public static final String INVALID_LEVEL_NUMBER = "Not valid level number";

	public static final String LEVEL_NOT_A_NUMBER_ERROR = String.format("%s: %%s", LEVEL_NOT_A_NUMBER);

	public static final String PROMPT = "Command > ";

	public static final String DEBUG = "[DEBUG] Executing: %s%n";
	public static final String ERROR = "[ERROR] Error: %s";

	// GAME STATUS
	public static final String NUMBER_OF_CYCLES = "Number of cycles: %s";

	public static final String REMAINING_TIME = "Time: %s";
	public static final String POINTS = "Points: %s";
	public static final String NUM_LIVES = "Lives: %s";
	public static final String INCORRECT_STATUS = "Incorrect game status \"%s\"";

	// GAME END MESSAGE
	public static final String GAME_OVER = "Game over";
	public static final String PLAYER_QUITS = "Player leaves the game";
	public static final String MARIO_WINS = "Thanks, Mario! Your mission is complete.";
	// Position format
	public static final String POSITION = "(%s,%s)";

	// Other
	public static final String SPACE = " ";
	public static final String TAB = "   ";
	public static final String LINE_SEPARATOR = System.lineSeparator();
	public static final String LINE = "%s" + LINE_SEPARATOR;
	public static final String LINE_TAB = TAB + LINE;
	public static final String LINE_2TABS = TAB + LINE_TAB;

//Commands
	// Errores de factorías:
	public static final String UNKNOWN_COMMAND = "Unknown command: %s";
	public static final String INVALID_GAME_OBJECT = "Invalid game object: %s";
	public static final String INVALID_OBJECT_POSITION = "Invalid object position: \"%s\"";
	public static final String INVALID_POSITION = "Invalid position: \"%s\"";
	public static final String OFF_BOARD_POSITION = "Object position is off board: \"%s\"";
	public static final String INVALID_FOR_INPUT = "For input string: \"%s\"";
	public static final String INVALID_MARIO_SIZE = "Invalid Mario size: \"%s\"";
	public static final String UNKNOWN_OBJ = "Unknown game object: \"%s\"";
	public static final String UNKNOWN_MOV_OBJ_DIR = "Unknown moving object direction: \"%s\"";
	public static final String INVALID_MOV_OBJ_DIR = "Invalid moving object direction: \"%s\"";
	public static final String INVALID_OBJ_TOO_MANY_ARG = "Object parse error, too much args: \"%s\"";
	public static final String INVALID_BOX_STATUS = " Invalid Box status: \"%s\"";

	// Otros:
	public static final String SAVE_CORRECT = "   File \"%s\"" + " correctly saved\n";

	// Errores de commandos:
	public static final String COMMAND_PARAMETERS_MISSING = "Missing parameters";
	public static final String COMMAND_INCORRECT_PARAMETER_NUMBER = "Incorrect parameter number";
	public static final String UNKNOWN_ACTION = "Unknown action: \"%s\"";
	public static final String ILLEGAL_ACTION = "Illegal action: \"%s\"";
	public static final String INVALID_COMMAND = "Invalid command: %s";
	public static final String INVALID_COMMAND_PARAMETERS = "Invalid command parameters";
	public static final String ERROR_COMMAND_EXECUTE = "Command execute problem";
	public static final String ERROR_ACTION_LIST_EMPTY = "Incorrect 'action command', "
			+ "because the action list is empty (all actions are unknown).";

	// Errores de files:
	public static final String FILE_NOT_FOUND = "File not found: \"%s\"";
	public static final String READ_ERROR = "Error reading file: \"%s\"";
	public static final String INVALID_INIT_CONFIGURATION = "Unknown invalid init configuration: \"%s\"";
	public static final String ERROR_UNABLE_TO_LOAD = "Unable to load game configuration from file \"%s\"";
	public static final String INVALID_FILE = "Invalid file \"%s\" configuration";
	public static final String ERROR_FILE = "Error saving file: \"%s\"";

	public static final String HELP_AVAILABLE_COMMANDS = "Available commands:";
	@Deprecated
	/* @formatter:off */
	public static final String[] HELP_LINES = new String[] { HELP_AVAILABLE_COMMANDS,
		"[a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+: user performs actions",
		"[u]pdate | \"\": user does not perform any action",
		"[r]eset [numLevel]: reset the game to initial configuration if not numLevel else load the numLevel map",
		"[h]elp: print this help message",
		"[e]xit: exits the game"
	};
	/* @formatter:on */
	@Deprecated
	public static final String HELP = String.join(LINE_SEPARATOR + "   ", HELP_LINES) + LINE_SEPARATOR;
	public static final String COMMAND_HELP_TEXT = "%s: %s";

	// ACTION
	public static final String COMMAND_ACTION_NAME = "action";
	public static final String COMMAND_ACTION_SHORTCUT = "a";
	public static final String COMMAND_ACTION_DETAILS = "[a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+";
	public static final String COMMAND_ACTION_HELP = "user performs actions";

	// UPDATE
	public static final String COMMAND_UPDATE_NAME = "update";
	public static final String COMMAND_UPDATE_SHORTCUT = "u";
	public static final String COMMAND_UPDATE_DETAILS = "[u]pdate | \"\"";
	public static final String COMMAND_UPDATE_HELP = "user does not perform any action";

	// EXIT
	public static final String COMMAND_EXIT_NAME = "exit";
	public static final String COMMAND_EXIT_SHORTCUT = "e";
	public static final String COMMAND_EXIT_DETAILS = "[e]xit";
	public static final String COMMAND_EXIT_HELP = "exits the game";

	// HELP
	public static final String COMMAND_HELP_NAME = "help";
	public static final String COMMAND_HELP_SHORTCUT = "h";
	public static final String COMMAND_HELP_DETAILS = "[h]elp";
	public static final String COMMAND_HELP_HELP = "print this help message";

	// RESET
	public static final String COMMAND_RESET_NAME = "reset";
	public static final String COMMAND_RESET_SHORTCUT = "r";
	public static final String COMMAND_RESET_DETAILS = "[r]eset [numLevel]";
	public static final String COMMAND_RESET_HELP = "reset the game to initial configuration if not numLevel else load the numLevel map";

	// ADD OBJECT
	public static final String COMMAND_ADD_OBJECT_NAME = "addObject";
	public static final String COMMAND_ADD_OBJECT_SHORTCUT = "aO";
	public static final String COMMAND_ADD_OBJECT_DETAILS = "[a]dd[O]bject <object_description>";
	public static final String COMMAND_ADD_OBJECT_HELP = "adds to the board the object given by object_description.\n"
			+ "      <object_description> = (col,row) objName [dir [BIG|SMALL]]. Ej. (12,3) Mario LEFT SMALL";

	// SAVE
	public static final String COMMAND_SAVE_NAME = "save";
	public static final String COMMAND_SAVE_SHORTCUT = "s";
	public static final String COMMAND_SAVE_DETAILS = "[s]ave <fileName>";
	public static final String COMMAND_SAVE_HELP = "save the actual configuration in text file <fileName>";

	// LOAD
	public static final String COMMAND_LOAD_NAME = "load";
	public static final String COMMAND_LOAD_SHORTCUT = "l";
	public static final String COMMAND_LOAD_DETAILS = "[l]oad <fileName>";
	public static final String COMMAND_LOAD_HELP = "load the game configuration from text file <fileName>";

	// Symbols
	public static final String EMPTY = "";
	public static final String LAND = MyStringUtils.repeat("▓", ConsoleView.CELL_SIZE);
	public static final String EXIT_DOOR = "🚪";
	public static final String MARIO_STOP = "🧑";
	public static final String MARIO_RIGHT = "🧍";// "🧍➡️";
	public static final String MARIO_LEFT = "🚶";// "⬅️🚶";
	public static final String GOOMBA = "🐻";

	public static final String MUSHROOM = "🍄";
	public static final String BOX = MyStringUtils.repeat("?", ConsoleView.CELL_SIZE);
	public static final String EMPTY_BOX = MyStringUtils.repeat("0", ConsoleView.CELL_SIZE);

	// NAMES
	public static final String GOOMBA_NAME = "Goomba";
	public static final String GOOMBA_SHORT_NAME = "G";
	public static final String GOOMBA_NAME_U = "GOOMBA";

	public static final String MUSHROOM_NAME = "Mushroom";
	public static final String MUSHROOM_SHORT_NAME = "MU";
	public static final String MUSHROOM_NAME_U = "MUSHROOM";

	public static final String MARIO_NAME = "Mario";
	public static final String MARIO_SHORT_NAME = "M";
	public static final String MARIO_NAME_U = "MARIO";
	public static final int MARIO_NEGATIVE_POINTS = -100;
	public static int MARIO_CONT = 3;

	public static final String LAND_NAME = "Land";
	public static final String LAND_SHORT_NAME = "L";
	public static final String LAND_NAME_U = "LAND";

	public static final String EXITDOOR_NAME = "ExitDoor";
	public static final String EXITDOOR_SHORT_NAME = "ED";
	public static final String EXITDOOR_NAME_U = "EXITDOOR";

	public static final String BOX_NAME = "Box";
	public static final String BOX_SHORT_NAME = "B";
	public static final String BOX_NAME_U = "BOX";

	// Reverse
	public static final String COMMAND_REVERSE_NAME = "reverseDirections";
	public static final String COMMAND_REVERSE_SHORTCUT = "rD";
	public static final String COMMAND_REVERSE_DETAILS = "[r]everse [D]irections";
	public static final String COMMAND_REVERSE_HELP = "reverse all moving  objects' directions";

	// Grenade
	public static final String GRENADE_NAME = "Grenade";
	public static final String GRENADE_SHORT_NAME = "Gr";
	public static final String GRENADE_NAME_U = "GRENADE";
	
	//Luigi
	public static final String LUIGI_NAME = "Luigi";
	public static final String LUIGI_SHORT_NAME = "LUI";
	public static final String LUIGI_NAME_U = "LUIGI";
	
	// Caja Estrella
	public static final String CAJA_ESTRELLA_NAME = "CajaEstrella";
	public static final String CAJA_ESTRELLA_SHORT_NAME = "CE";
	public static final String CAJA_ESTRELLA_NAME_U = "CAJA ESTRELLA";

	// Solid Is Lava
	public static final String LAVA_NAME = "Solid Is Lava";
	public static final String LAVA_SHORT_NAME = "S";
	public static final String LAVA_NAME_U = "SOLID IS LAVA";
	
	// Mirror Position
	public static final String COMMAND_MP_NAME = "verticalMirrorPositions";
	public static final String COMMAND_MP_SHORTCUT = "MP";
	public static final String COMMAND_MP_DETAILS = "vertical [M]irror [P]ositions";
	public static final String COMMAND_MP_HELP = "reverses the game objects' positions as if theere were a vertical mirror";

	// Teleransport
	public static final String COMMAND_TELE_NAME = "teletransportation";
	public static final String COMMAND_TELE_SHORTCUT = "t";
	public static final String COMMAND_TELE_DETAILS = "[t]eletransportation (ROW_INI,COL_INI) (ROW_END,COL_END)";
	public static final String COMMAND_TELE_HELP = "teleports all objects in initial position (INI) to\r\n"
			+ "the end position (END)";
}