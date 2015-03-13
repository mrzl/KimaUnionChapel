package main.transitions.blur;

import main.transitions.TransitionInterface;
import pattern.ChladniParticles;

/**
 * Created by mrzl on 08.03.2015.
 */
public class DirectionBlurTransition implements TransitionInterface {

    private DirectionalBlurChangeThread thread;
    private DirectionalBlurState from, to;

    public DirectionBlurTransition( ChladniParticles pattern, DirectionalBlurState from, DirectionalBlurState to, long duration ) {
        this.from = from;
        this.to = to;

        thread = new DirectionalBlurChangeThread( pattern, from.getBlurStrength(), to.getBlurStrength(), duration );
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.running = false;
    }
}
