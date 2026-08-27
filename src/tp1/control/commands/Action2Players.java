package tp1.control.commands;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class Action2Players extends AbstractCommand {

    private static final String NAME = "action2player";
    private static final String SHORTCUT = "a2p";
    private static final String DETAILS = "Action2Players M<UP,UP,RIGHT> L<RIGHT,LEFT>";
    private static final String HELP = "action for 2 players";

    private ActionList actListM = new ActionList();
    private ActionList actListL = new ActionList();

    public Action2Players() {
        super(NAME, SHORTCUT, DETAILS, HELP);
    }

    public Action2Players(ActionList actionListM, ActionList actionListL) {
        super(NAME, SHORTCUT, DETAILS, HELP);
        this.actListM = actionListM;
        this.actListL = actionListL;
    }

    @Override
    public void execute(GameModel game, GameView view) throws CommandExecuteException {
        if (actListM.getCont() == 0 && actListL.getCont() == 0)
            throw new CommandExecuteException(Messages.ERROR_ACTION_LIST_EMPTY);

        for (int i = 0; i < actListM.getCont(); i++) {
            game.addAction(actListM.getAction(i));
        }
        for (int i = 0; i < actListL.getCont(); i++) {
            game.addActionLui(actListL.getAction(i));
        }
        
        game.update();
        actListM.reset();
        actListL.reset();
        view.showGame();
    }

    @Override
    public Command parse(String[] commandWords) throws CommandParseException {
        if (commandWords.length < 5 && matchCommandName(commandWords[0]))
            throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

        if (commandWords.length >= 5 && matchCommandName(commandWords[0])) {
            boolean isLuigi = false;
            ActionList newActListM = new ActionList();
            ActionList newActListL = new ActionList();

            // Un único bucle que lee todo perfectamente
            for (int i = 1; i < commandWords.length; i++) {
                String palabra = commandWords[i];

                if (palabra.equalsIgnoreCase("mario")) {
                    isLuigi = false;
                } else if (palabra.equalsIgnoreCase("lui")) {
                    isLuigi = true;
                } else {
                    try {
                        Action act = Action.parse(palabra);
                        if (isLuigi) {
                            newActListL.addAction(act);
                        } else {
                            newActListM.addAction(act);
                        }
                    } catch (ActionParseException ape) {
                        throw new CommandParseException("Acción desconocida: " + palabra, ape);
                    }
                }
            }
            return new Action2Players(newActListM, newActListL);
        } else {
            return null;
        }
    }
}