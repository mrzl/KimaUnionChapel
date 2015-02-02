package pattern;

/**
 * Created by mrzl on 02.02.2015.
 */
public class ParticleSizeTimerThread extends Thread {
    private ChladniParticles particles;
    public boolean running;
    private float targetParticleSize;

    public ParticleSizeTimerThread( ChladniParticles _particles, float _targetParticleSize ) {
        this.particles = _particles;
        this.running = false;
        this.targetParticleSize = _targetParticleSize;
    }

    public void start() {
        super.start();
        running = true;
    }

    public void run() {
        while( running ) {
            try {
                if( particles.getParticleSize() > targetParticleSize ) {
                    particles.setParticleSize( particles.getParticleSize() * 0.8f );
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
