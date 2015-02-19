package main.transitions;

import pattern.ChladniParticles;

/**
 * Created by mrzl on 19.02.2015.
 */
public class ColorShiftThread extends Thread {
    private ChladniParticles pattern;
    private float startHue, endHue, startBrightness, endBrightness, startSaturation, endSaturation;
    private long duration;
    private boolean running;
    private long startTime, endTime;

    public ColorShiftThread( ChladniParticles _pattern, float startHue, float startSaturation, float startBrightness, float endHue, float endSaturation, float endBrightness, long durationMillis ) {
        this.pattern = _pattern;
        this.startHue = startHue;
        this.endHue = endHue;
        this.startBrightness = startBrightness;
        this.endBrightness = endBrightness;
        this.startSaturation = startSaturation;
        this.endSaturation = endSaturation;
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

                float currentMinHue = ( ( endHue - startHue ) * percentage ) + startHue;

                if( pattern.getOpacityToHueShader().isEnabled() ) {
                    pattern.getOpacityToHueShader().setMinHue( currentMinHue );
                    pattern.getOpacityToHueShader().setMaxHue( currentMinHue );
                } else {
                    pattern.getColorMode().setMinHue( currentMinHue );
                    pattern.getColorMode().setMaxHue( currentMinHue );
                }

                float currentSaturation = ( ( endSaturation - startSaturation ) * percentage ) + startSaturation;
                pattern.getSurface().setSaturation( currentSaturation );

                float currentBrightness = ( ( endBrightness - startBrightness ) * percentage) + startBrightness;
                pattern.setIntensity( currentBrightness );

                if( percentage > 1.0f ) {
                    running = false;
                    System.out.println( "Done." );
                }

                Thread.sleep( 5 );
            } catch ( InterruptedException e ) {
                e.printStackTrace( );
            }
        }
    }
}
