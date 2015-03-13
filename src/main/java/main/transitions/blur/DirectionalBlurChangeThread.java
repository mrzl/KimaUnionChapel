package main.transitions.blur;

import pattern.ChladniParticles;

/**
 * Created by mrzl on 08.03.2015.
 */
public class DirectionalBlurChangeThread extends Thread {
    private ChladniParticles pattern;
    private float start, end;
    private long duration;
    protected boolean running;
    private long startTime, endTime;

    public DirectionalBlurChangeThread( ChladniParticles pattern, float start, float end, long durationMillis ) {
        this.pattern = pattern;
        this.start = start;
        this.end = end;
        this.duration = durationMillis;
        this.running = false;
    }

    public void start() {
        super.start();
        running = true;

        startTime = System.currentTimeMillis();
        endTime = startTime + duration;
    }

    public void run() {
        while( running ) {
            try {
                long currentTime = System.currentTimeMillis() - startTime;
                float percentage = currentTime / (float)( duration );

                float currentBlurStrength = ( ( end - start ) * percentage ) + start;

                if( pattern.getDirectionalBlur2().isEnabled() ) {
                    pattern.getDirectionalBlur2().setRadius( currentBlurStrength );
                }

                if( percentage > 1.0f ) {
                    running = false;
                    System.out.println( "Done blur strength." );
                }

                Thread.sleep( 5 );
            } catch ( InterruptedException e ) {
                //e.printStackTrace( );
            }
        }
    }
}
