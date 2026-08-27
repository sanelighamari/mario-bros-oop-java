package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario extends Player {
    private static final int NEGATIVE_POINTS = Messages.MARIO_NEGATIVE_POINTS;
    private boolean grenadeThrownThisTurn;

    public Mario() { super(); }
    
    public Mario(Mario original) {
        super(original);
        this.grenadeThrownThisTurn = false;
    }
    
    public Mario(GameWorld game, Position pos) {
        super(game, pos, Messages.MARIO_NAME);
        this.grenadeThrownThisTurn = false;
    }

    @Override
    public void update() {
        super.update();
        grenadeThrownThisTurn = false;
    }

    @Override
    protected void doUserAction(Action act, Position tentativePos) {
        if (act == Action.GRENADE && !grenadeThrownThisTurn) {
            lanzarGranada();
            grenadeThrownThisTurn = true;
        } else {
            super.doUserAction(act, tentativePos);
        }
    }

    private void lanzarGranada() {
        Position pos1 = new Position(getPos(), this.direction);
        Position pos2 = new Position(pos1, this.direction);
        Grenade grenade = new Grenade(this.game, pos2, this.direction);
        this.game.addBuffer(grenade);
    }

    @Override
    public String getIcon() {
        switch (direction) {
            case RIGHT: return Messages.MARIO_RIGHT;
            case LEFT: return Messages.MARIO_LEFT;
            default: return Messages.MARIO_STOP;
        }
    }

    @Override
    public boolean receiveInteraction(Grenade grenade) {
        if (!deadThisTurn) {
            if (this.big) {
                this.big = false;
            } else {
                game.addPoints(NEGATIVE_POINTS);
            }
            return true;
        }
        return false;
    }

    @Override
    protected Mario createInstance(Position pos, GameWorld game) {
        return new Mario(game, pos);
    }

    @Override
    protected boolean matchParseName(String name) {
        String input = name.toUpperCase();
        return input.equals(Messages.MARIO_NAME_U) || input.equals(Messages.MARIO_SHORT_NAME);
    }

    @Override
    public GameObject copy() { return new Mario(this); }
    
    @Override
    public Mario parse(String strsObject[], GameWorld game) throws ObjectParseException, OffBoardException {
        Mario marioParse = (Mario) super.parse(strsObject, game);
        if (strsObject.length > 4 && matchParseName(strsObject[1]))
            throw new ObjectParseException(Messages.INVALID_OBJ_TOO_MANY_ARG.formatted(String.join(" ", strsObject)));
        if (marioParse != null) {
            if (strsObject.length == 2 || strsObject.length == 3) marioParse.big = true;
            else {
                marioParse.big = switch (strsObject[3].toLowerCase()) {
                    case "big", "b" -> true;
                    case "small", "s" -> false;
                    default -> throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", strsObject)));
                };
            }
            return marioParse;
        }
        return null;
    }
}