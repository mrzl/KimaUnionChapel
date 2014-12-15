package main;

import controlP5.ControlP5;
import processing.core.PApplet;

/**
 * Created by mar on 15.12.14.
 */
public class ControlFrame extends PApplet {

    ControlP5 cp5;
    Main parent;

    int w, h;

    public void setup() {
        size( w, h );
        frameRate( 500 );
        cp5 = new ControlP5( this );
        cp5.addSlider( "rectN" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 10 ).setValue( 2.0f );
        cp5.addSlider( "rectM" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 50 ).setValue( 3.0f );
    }

    public void rectM( float val ) {
        parent.particlesRect.frequencyChanged();
        parent.rect.setM( val );
    }

    public void rectN( float val ) {
        parent.particlesRect.frequencyChanged();
        parent.rect.setN( val );
    }

    public void draw() {
        background( 0 );
    }

    public ControlFrame( Main theParent, int theWidth, int theHeight ) {
        parent = theParent;
        w = theWidth;
        h = theHeight;
    }
}