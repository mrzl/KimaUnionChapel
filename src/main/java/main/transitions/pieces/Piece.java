package main.transitions.pieces;

import main.Main;
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

    /**
     * TODO: This is where the parameters need to be changed for now.
     * TODO: I'm starting to mess up the code- been waiting for this to happen.
     */
    protected void selectCustomAuroraParameters() {

        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getBloomModifier().setThreshold( 0.10f );
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getBloomModifier().setBlurSize( 36 );
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getBloomModifier().setBlurSigma( 4.16f );
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getBrightnessContrastShader().setContrast( 2.36f );

        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ).getBloomModifier().setThreshold( 0.06f );
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ).getBloomModifier().setBlurSize( 42 );
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ).getBloomModifier().setBlurSigma( 8.0f );
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ).getBrightnessContrastShader().setContrast( 2.43f );

        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ).getBloomModifier().setThreshold( 0.07f );
        getTransitionController( ).getMain().chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ).getBloomModifier( ).setBlurSize( 42 );
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ).getBloomModifier().setBlurSigma( 8.0f );
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ).getBrightnessContrastShader().setContrast( 2.3f );
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
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ).setDisabled( true );
    }

    protected void enableCircle() {
        getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ).setDisabled( false );
    }
}
