package main;

import controlP5.ControlP5;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by mar on 13.12.14.
 */
public class Main extends PApplet {

    protected ChladniContainer chladniRect;
    protected ChladniContainer chladniCircle;

    Minim minim;
    AudioOutput out;
    Oscil wave;

    private ControlP5 cp5;
    ControlFrame cf;
/*
    public void init() {
        frame.removeNotify();
        frame.setUndecorated( true );
        frame.setResizable( true );
        frame.addNotify();
        super.init();
    }*/

    int resolution = 800;

    public void setup() {
        size( 1600, 800, PConstants.P3D );

        frameRate( 200 );

        ChladniRectangle rect = new ChladniRectangle( this, resolution, resolution );
        ChladniCircle circle = new ChladniCircle( this, resolution, resolution );

        chladniRect = new ChladniContainer( this, rect, 50000 );
        chladniCircle = new ChladniContainer( this, circle, 50000 );

        minim = new Minim(this);
        out = minim.getLineOut();
        // create a sine wave Oscil, set to 440 Hz, at 0.5 amplitude
        wave = new Oscil( 440, 0.5f, Waves.SINE );
        // patch the Oscil to the output
        //wave.patch( out );

        cp5 = new ControlP5(this);
        cf = MathUtils.addControlFrame( this, "Controls", 400, 600 );
    }

    public void draw() {
        background( 0 );
        frame.setTitle( frameRate + "" );

        chladniRect.update( 5 );
        //chladniRect.drawOriginal( 0, 0, 800, 800 );
        chladniRect.drawParticles( 0, 0 );

        chladniCircle.update( 5 );
        chladniCircle.restrictCircular( ( int ) ( chladniCircle.getSurface().getWidth() / 2 ) );
        //chladniCircle.drawOriginal( 800, 0, 800, 800 );
        chladniCircle.drawParticles( 800, 0 );
    }

    public void mouseMoved() {
        float minFreq = 110;
        float maxFreq = 880;
        float freq = map( mouseX, 0, width, minFreq, maxFreq );
        float amplitude = map( mouseY, 0, height, 0, 2 );
        wave.setFrequency( freq );
        wave.setAmplitude( amplitude );

        ChladniRectangle r = ( ChladniRectangle ) chladniRect.getSurface();
        r.setN( map( freq, minFreq, maxFreq, 0, 40 ) );
        r.setM( map( amplitude, 0, 2, 0, 50 ) );

        ChladniCircle c = ( ChladniCircle ) chladniCircle.getSurface();
        c.setN( map( mouseX, 0, width, 0, 5 ) );
        c.setM( map( mouseY, 0, height, 0, 20 ) );

        chladniRect.frequencyChanged();
        chladniCircle.frequencyChanged();
    }

    public static void main( String[] args ) {
        PApplet.main( new String[]{ "main.Main" } );
    }
}
