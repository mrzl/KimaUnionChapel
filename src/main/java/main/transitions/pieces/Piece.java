package main.transitions.pieces;

import main.transitions.TransitionController;

/**
 * Created by mrzl on 20.02.2015.
 */
public class Piece {
    private TransitionController tc;

    public Piece( TransitionController _tc ) {
        this.tc = _tc;
    }

    protected TransitionController getTransitionController () {
        return tc;
    }
}
