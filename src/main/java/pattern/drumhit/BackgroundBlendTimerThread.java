package pattern.drumhit;

import pattern.ChladniParticles;

/**
 * Created by mrzl on 06.02.2015.
 */
public class BackgroundBlendTimerThread extends Thread {
    private ChladniParticles particles;
    public boolean running;
    private float targetBackgroundColor;

    public BackgroundBlendTimerThread( ChladniParticles _particles, float _targetBackgroundColor ) {
        this.particles = _particles;
        this.running = false;
        this.targetBackgroundColor = _targetBackgroundColor;
    }

    public void start() {
        super.start();
        running = true;
    }

    public void run() {
        while( running ) {
            try {
                if( particles.getCurrentBlendedBackgroundValue( ) > targetBackgroundColor ) {
                    particles.setCurrentBlendedBackgroundValue( ( int ) (particles.getCurrentBlendedBackgroundValue( ) * 0.5f) );
                } else {
                    running = false;
                }

                Thread.sleep( 100 );
            } catch ( InterruptedException e ) {
                e.printStackTrace( );
            }
        }
    }
}
