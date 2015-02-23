package main.transitions.color;

import pattern.ChladniParticles;

/**
 * Created by mrzl on 19.02.2015.
 */
public class ColorShiftThread extends Thread {
    private ChladniParticles pattern;
    private float startMinHue, startMaxHue, endMinHue, endMaxHue, startBrightness, endBrightness, startSaturation, endSaturation;
    private long duration;
    protected boolean running;
    private long startTime, endTime;

    public ColorShiftThread( ChladniParticles _pattern, float startMinHue,float startMaxHue, float startSaturation, float startBrightness, float endMinHue, float endMaxHue, float endSaturation, float endBrightness, long durationMillis ) {
        this.pattern = _pattern;
        this.startMinHue = startMinHue;
        this.startMaxHue = startMaxHue;
        this.endMinHue = endMinHue;
        this.endMaxHue = endMaxHue;
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

                float currentMinHue = ( ( endMinHue - startMinHue ) * percentage ) + startMinHue;
                float currentMaxHue = (( endMaxHue - startMaxHue ) * percentage ) + startMaxHue;

                if( pattern.getOpacityToHueShader().isEnabled() ) {
                    pattern.getOpacityToHueShader().setMinHue( currentMinHue );
                    pattern.getOpacityToHueShader().setMaxHue( currentMaxHue );
                } else {
                    pattern.getColorMode().setMinHue( currentMinHue );
                    pattern.getColorMode().setMaxHue( currentMaxHue );
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
                //e.printStackTrace( );
            }
        }
    }
}
