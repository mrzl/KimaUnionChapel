package main.transitions.pieces;

import main.Main;
import main.transitions.TransitionController;
import main.transitions.TransitionInterface;
import main.transitions.color.ColorTransition;

import java.util.ArrayList;

/**
 * Created by mrzl on 20.02.2015.
 */
public class Piece {
    protected ArrayList< TransitionInterface > transitions;
    private TransitionController tc;
    protected long durationMillis;

    public Piece( TransitionController _tc ) {
        this.tc = _tc;
        this.durationMillis = 90000;
        this.transitions = new ArrayList<>();
    }

    protected TransitionController getTransitionController () {
        return tc;
    }

    public void select() {
        System.err.println( "ERROR: not overwritten select method." );
        System.exit( 1 );
    }

    public void stopColorTransitioning () {
        for( TransitionInterface t : transitions ) {
            t.stop();
        }
    }

    /**
     * TODO: This is where the parameters need to be changed for now.
     * TODO: I'm starting to mess up the code- been waiting for this to happen.
     */
    protected void selectCustomAuroraParameters() {

    }

    protected void disableRect() {
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ).setDisabled( true );
    }

    protected void enableRect() {
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ).setDisabled( false );
    }

    protected void disableTriangle() {
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setDisabled( true );
    }

    protected void enableTriangle() {
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setDisabled( false );
    }

    protected void disableCircle() {
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setDisabled( true );
    }

    protected void enableCircle() {
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setDisabled( false );
    }
}
