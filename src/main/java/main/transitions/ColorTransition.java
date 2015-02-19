package main.transitions;

import pattern.ChladniParticles;

/**
 * Created by mrzl on 19.02.2015.
 */
public class ColorTransition {

    private ColorShiftThread cst;
    private ColorState from, to;

    public ColorTransition( ChladniParticles pattern, ColorState _from, ColorState _to, long duration ) {
        this.from = _from;
        this.to = _to;

        cst = new ColorShiftThread( pattern, from.getHue(), from.getSaturation(), from.getBrightness(), to.getHue(), to.getSaturation(), to.getBrightness(), duration );
    }

    public void start() {
        cst.start();
    }
}
