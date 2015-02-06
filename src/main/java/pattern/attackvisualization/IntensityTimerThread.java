package pattern.attackvisualization;

import pattern.ChladniParticles;

/**
 * Created by mrzl on 06.02.2015.
 */
public class IntensityTimerThread extends Thread {
    private ChladniParticles particles;
    public boolean running;
    private float targetIntensity;

    public IntensityTimerThread( ChladniParticles _particles, float _targetIntensity ) {
        this.particles = _particles;
        this.running = false;
        this.targetIntensity = _targetIntensity;
    }

    public void start() {
        super.start();
        running = true;
    }

    public void run() {
        while( running ) {
            try {
                if( particles.getSurface().getIntensity() > targetIntensity ) {
                    particles.getSurface().setIntensity( particles.getSurface( ).getIntensity( ) * 0.8f );
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
