package main.transitions.color;

import main.transitions.TransitionInterface;
import pattern.ChladniParticles;

/**
 * Created by mrzl on 19.02.2015.
 */
public class ColorTransition implements TransitionInterface {

    private ColorShiftThread cst;
    private ColorState from, to;

    public ColorTransition( ChladniParticles pattern, ColorState _from, ColorState _to, long duration ) {
        this.from = _from;
        this.to = _to;

        cst = new ColorShiftThread( pattern, from.getMinHue( ), from.getMaxHue(), from.getSaturation(), from.getBrightness(), to.getMinHue( ), to.getMaxHue(), to.getSaturation(), to.getBrightness(), duration );
    }

    public void start() {
        cst.start();
    }

    public void stop() {
        cst.running = false;
    }
}
