package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Luigi extends Player {

    public Luigi() { super(); }
    
    public Luigi(Luigi original) { super(original); }
    
    public Luigi(GameWorld game, Position pos) {
        super(game, pos, Messages.LUIGI_NAME);
    }

    @Override
    public String getIcon() {
        return "L";
    }

    @Override
    protected Luigi createInstance(Position pos, GameWorld game) {
        return new Luigi(game, pos);
    }

    @Override
    protected boolean matchParseName(String name) {
        String input = name.toUpperCase();
        return input.equals(Messages.LUIGI_NAME_U) || input.equals(Messages.LUIGI_SHORT_NAME);
    }

    @Override
    public GameObject copy() { return new Luigi(this); }
    
    @Override
    public Luigi parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
        Luigi luigiParse = (Luigi) super.parse(strsObject, game);
        if (strsObject.length > 4 && matchParseName(strsObject[1]))
            throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));
        if (luigiParse != null) {
            if (strsObject.length == 2 || strsObject.length == 3) luigiParse.big = true;
            else {
                luigiParse.big = switch (strsObject[3].toLowerCase()) {
                    case "big", "b" -> true;
                    case "small", "s" -> false;
                    default -> throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", strsObject)));
                };
            }
            return luigiParse;
        }
        return null;
    }
}