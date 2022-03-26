package org.firstinspires.ftc.teamcode.bt;

import java.util.HashMap;

public abstract class Action implements Cloneable {

    protected Action[] childActions;

    public enum State {
        DEFAULT,
        STARTED,
        TICKED,
        ENDED
    }


    HashMap<State, Long> lastStateChange = new HashMap<>();

    State state = State.DEFAULT;

    // start runs once, starts the action

    public void start(AutonomousOpMode context) {
        assert(state == State.DEFAULT || state == State.ENDED);
        state = State.STARTED;
        lastStateChange.put(State.STARTED, System.currentTimeMillis());
        _start(context);
    }

    protected abstract void _start(AutonomousOpMode context);

    public void tick(AutonomousOpMode context) {
        assert(state == State.STARTED || state == State.TICKED);
        state = State.TICKED;
        lastStateChange.put(State.TICKED, System.currentTimeMillis());
        _tick(context);
    }

    protected abstract void _tick(AutonomousOpMode context);

    public boolean hasFinished(AutonomousOpMode context) {
        return _hasFinished(context);
    }
    protected abstract boolean _hasFinished(AutonomousOpMode context);

    public void end(AutonomousOpMode context) {
        assert(state == State.STARTED || state == State.TICKED);
        state = State.ENDED;
        lastStateChange.put(State.ENDED, System.currentTimeMillis());
        _end(context);
    }
    // end runs once, for cleanup purposes
    protected abstract void _end(AutonomousOpMode context);

    public Action[] getChildActions() {
        return childActions;
    }

    public State getState() {
        return state;
    }

    public long getLastTimeStampForState(State state) {
        return lastStateChange.getOrDefault(state, (long) -1);
    }

    public boolean DEBUG_showChildren() { return true; }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getCustomDisplay() {
        return "";
    }

}