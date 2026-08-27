package tp1.logic.gameobjects;

import tp1.exceptions.GameLoadException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameWorld;
import tp1.logic.Position;

public abstract class Player extends MovingObject {
    protected boolean solid;
    protected boolean big;
    protected ActionList actList;
    protected boolean wasFalling;
    protected boolean deadThisTurn;
    protected boolean goingUp;
    protected boolean inmutable = false;
    protected int cont = 3;

    public Player() {
        super();
    }

    public Player(GameWorld game, Position pos, String name) {
        super(game, pos, name);
        this.solid = false;
        this.big = true;
        this.actList = new ActionList();
        this.wasFalling = false;
        this.deadThisTurn = false;
        this.goingUp = false;
    }

    public Player(Player original) {
        super(original);
        this.solid = original.solid;
        this.big = original.big;
        this.actList = new ActionList(); 
        this.wasFalling = original.wasFalling;
        this.deadThisTurn = original.deadThisTurn;
        this.goingUp = original.goingUp;
        this.inmutable = original.inmutable;
        this.cont = original.cont;
    }

    @Override
    public void update() {
        deadThisTurn = false;
        if (inmutable) {
            cont--;
            if (cont <= 0) inmutable = false;
        }
        checkGround(getPos());
        
        if (actList.getCont() > 0) {
            readActions();
        } else if (isFalling) {
            fall();
        } else {
            step();
        }
        
        actList.reset();
        if (!game.positionIsIn(getPos())) dead();
    }

    protected void fall() {
        Position tentativePos = new Position(getPos(), Action.DOWN);
        setPos(tentativePos);
        checkGround(getPos());
        wasFalling = true;
    }

    protected void step() {
        Position tentativePos = new Position(getPos(), this.direction);
        if (game.isSolid(tentativePos) || !game.positionIsIn(tentativePos)) {
            this.direction = direction.opposite();
        } else {
            setPos(tentativePos);
        }
        this.wasFalling = false;
        checkGround(getPos());
    }

    @Override
    public boolean isInPosition(Position pos) {
        if (this.big) {
            Position upPos = new Position(getPos(), Action.UP);
            return getPos().equals(pos) || upPos.equals(pos);
        }
        return getPos().equals(pos);
    }

    public void addAction(Action act) {
        actList.addAction(act);
    }

    public void readActions() {
        for (int i = 0; i < actList.getCont(); i++) {
            Action act = actList.getAction(i);
            if (act != null) {
                doAction(act);
                game.doInteraction(this);
                goingUp = false;
                checkGround(getPos());
            }
        }
    }

    protected void isGoingDown() {
        this.wasFalling = true;
        Position nextPos = new Position(getPos(), Action.DOWN);
        while (!game.isSolid(nextPos) && !isDead()) {
            if (!game.positionIsIn(nextPos)) dead();
            setPos(nextPos);
            nextPos = new Position(getPos(), Action.DOWN);
            game.doInteraction(this);
        }
        isFalling = false;
        this.wasFalling = true;
    }

    protected void doAction(Action act) {
        Position tentativePos = new Position(getPos(), act);
        if (checkforWalls(act)) {
            if (isFalling) {
                if (act == Action.DOWN) isGoingDown();
                else fall();
            } else doUserAction(act, tentativePos);
        } else {
            if (act == Action.UP) goingUp = true;
            if (act == Action.DOWN) {
                isGoingDown();
                this.direction = Action.STOP;
            } else if (act == Action.LEFT || act == Action.RIGHT) {
                this.direction = direction.opposite();
            }
        }
    }

    protected boolean checkforWalls(Action act) {
        boolean isWall = false;
        Position nextPosBody = new Position(getPos(), act);
        if (this.big) {
            Position nextPosHead = new Position(nextPosBody, Action.UP);
            if (!game.isSolid(nextPosBody) && game.positionIsIn(nextPosBody) && !game.isSolid(nextPosHead) && game.positionIsIn(nextPosHead))
                isWall = true;
        } else if (!game.isSolid(nextPosBody) && game.positionIsIn(nextPosBody)) {
            isWall = true;
        }
        return isWall;
    }

    protected void doUserAction(Action act, Position tentativePos) {
        if (act == Action.STOP) {
            this.direction = act;
        } else if (act == Action.LEFT || act == Action.RIGHT) {
            this.direction = act;
            setPos(tentativePos);
        } else if (act == Action.UP) {
            setPos(tentativePos);
            goingUp = true;
        } else {
            isGoingDown();
        }
    }

    protected void checkGround(Position pos) {
        Position downPos = new Position(pos, Action.DOWN);
        isFalling = !game.isSolid(downPos);
    }

    @Override
    public boolean interactWith(GameItem other) {
        Position currentPos = getPos();
        Position upPos = new Position(getPos(), Action.UP);
        boolean canInteract = this.big ? (other.isInPosition(currentPos) || other.isInPosition(upPos)) : other.isInPosition(currentPos);
        if (canInteract && other.isAlive() && !deadThisTurn) {
            other.receiveInteraction(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveInteraction(Goomba goomba) {
        if (inmutable) return true;
        if (!deadThisTurn) {
            if (!wasFalling && !isFalling) {
                if (this.big) {
                    this.big = false;
                } else {
                    dead();
                    deadThisTurn = true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveInteraction(Mushroom mushroom) {
        this.big = true;
        return true;
    }

    @Override
    public boolean receiveInteraction(CajaEstrella ce) {
        this.inmutable = true;
        this.big = true;
        cont = 3;
        return true;
    }
    
    @Override
    public boolean receiveInteraction(SolidIsLava solidIsLava) {
        boolean tocandoSolido = false;
        Position up = new Position(getPos(), Action.UP);
        Position down = new Position(getPos(), Action.DOWN);
        Position left = new Position(getPos(), Action.LEFT);
        Position right = new Position(getPos(), Action.RIGHT);
        
        if (big) {
            up = new Position(up, Action.UP);
            Position headLeft = new Position(up, Action.LEFT);
            Position headRight = new Position(up, Action.RIGHT);
            if (game.isSolid(headLeft) || game.isSolid(headRight)) tocandoSolido = true;
        }
        if (game.isSolid(down) || game.isSolid(up) || game.isSolid(left) || game.isSolid(right)) tocandoSolido = true;
        
        if (tocandoSolido && !deadThisTurn) {
            if (big) big = false;
            else {
                dead();
                deadThisTurn = true;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveInteraction(Box box) {
        return this.goingUp;
    }

    @Override
    public boolean isSolid() {
        return solid;
    }

    @Override
    public void dead() {
        if (!isDead()) {
            super.dead();
            actList.reset();
            try {
                game.marioDead();
            } catch (GameLoadException e) {
            }
        }
    }
}