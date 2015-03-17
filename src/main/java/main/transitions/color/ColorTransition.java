package main.transitions.color;

import main.transitions.TransitionInterface;
import pattern.ChladniParticles;

/**
 * Created by mrzl on 19.02.2015.
 */
public class ColorTransition implements TransitionInterface {

    private ColorShiftThread cst;
    private ColorState from, to;
    private ColorTransition secondTrans;

    public ColorTransition( ChladniParticles pattern, ColorState _from, ColorState _to, long duration ) {
        this.from = _from;
        this.to = _to;

        secondTrans = null;

        cst = new ColorShiftThread( pattern, from.getMinHue( ), from.getMaxHue(), from.getSaturation(), from.getBrightness(), to.getMinHue( ), to.getMaxHue(), to.getSaturation(), to.getBrightness(), duration );
    }

    public void setSecondTrans( ColorTransition _tra ) {
        this.secondTrans = _tra;
    }

    public void start() {
        if( secondTrans != null ) {
            cst.addSecond( secondTrans );
        }
        cst.start();
    }

    public void stop() {
        if( secondTrans != null ) {
            secondTrans.stop();
        }
        cst.running = false;
    }
}
