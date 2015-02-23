package main.transitions.pieces;

import main.transitions.TransitionController;
import main.transitions.color.ColorTransition;

import java.util.ArrayList;

/**
 * Created by mrzl on 20.02.2015.
 */
public class Piece {
    protected ArrayList< ColorTransition > transitions;
    private TransitionController tc;
    protected long durationMillis;

    public Piece( TransitionController _tc ) {
        this.tc = _tc;
        this.durationMillis = 5000;
        this.transitions = new ArrayList<>();
    }

    protected TransitionController getTransitionController () {
        return tc;
    }

    public void select() {
        System.err.println( "ERROR: not overwritten select method." );
    }

    public void stopColorTransitioning () {
        for( ColorTransition t : transitions ) {
            t.stop();
        }
    }
}
