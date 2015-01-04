package main;

import controlP5.ControlP5;
import processing.core.PApplet;

import java.awt.*;

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

        cp5.addSlider( "circleN" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 90 ).setValue( 2.0f );
        cp5.addSlider( "circleM" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 130 ).setValue( 3.0f );
        cp5.addSlider( "circlePoles" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 170 ).setValue( 33.0f );
        cp5.addSlider( "circleScale" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 210 ).setValue( 1.0f );

        cp5.addSlider( "jumpyness" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 270 ).setValue( 3.0f );
        cp5.addSlider( "particleSize" ).setRange( 0, 10 ).setSize( 300, 20 ).setPosition( 10, 300 ).setValue( 3.0f );
        cp5.addSlider( "particleCount" ).setRange( 0, 80000 ).setSize( 300, 20 ).setPosition( 10, 330 ).setValue( 10000.0f );
        cp5.addSlider( "particleOpacity" ).setRange( 0, 1 ).setSize( 300, 20 ).setPosition( 10, 360 ).setValue( 0.6f );

        cp5.addToggle("thresholdToggle")
                .setPosition( 40,450 )
                .setSize(80,20)
                .setValue(false)
                .setMode(ControlP5.SWITCH)
        ;
        cp5.addSlider( "threshold" ).setRange( 0, 1 ).setSize( 300, 20 ).setPosition( 10, 490 ).setValue( 0.2f );
    }

    public void rectM( float val ) {
        parent.chladniRect.frequencyChanged();
        ChladniRectangle r = ( ChladniRectangle ) parent.chladniRect.getSurface();
        r.setM( val );
    }

    public void rectN( float val ) {
        parent.chladniRect.frequencyChanged();
        ChladniRectangle r = ( ChladniRectangle ) parent.chladniRect.getSurface();
        r.setN( val );
    }

    public void circleN( float val ) {
        parent.chladniCircle.frequencyChanged();
        ChladniCircle c = ( ChladniCircle ) parent.chladniCircle.getSurface();
        c.setN( val );
    }

    public void circleM( float val ) {
        parent.chladniCircle.frequencyChanged();
        ChladniCircle c = ( ChladniCircle ) parent.chladniCircle.getSurface();
        c.setM( val );
    }

    public void circlePoles( float val ) {
        parent.chladniCircle.frequencyChanged();
        ChladniCircle c = ( ChladniCircle ) parent.chladniCircle.getSurface();
        c.setPoles( ( int ) ( val ) );
    }

    public void circleScale( float val ) {
        parent.chladniCircle.frequencyChanged();
        ChladniCircle c = ( ChladniCircle ) parent.chladniCircle.getSurface();
        c.setScale( val );
    }

    public void jumpyness( float _jumpyness ) {
        parent.chladniRect.setRebuildSpeed( _jumpyness );
        parent.chladniCircle.setRebuildSpeed( _jumpyness );
        parent.chladniRealCircle.setRebuildSpeed( _jumpyness );
    }

    public void threshold( float _threshold ) {
        parent.mm.setThreshold( _threshold );
    }

    public void thresholdToggle( boolean isEnabled ) {
        parent.doMetaBall = isEnabled;
    }

    public void particleSize( float _particleSize ) {
        parent.chladniRect.setParticleSize( _particleSize );
        parent.chladniCircle.setParticleSize( _particleSize );
        parent.chladniRealCircle.setParticleSize( _particleSize );
    }

    public void particleCount( float _particleCount) {
        parent.chladniCircle.setParticleCount( ( int )( _particleCount ) );
        parent.chladniRect.setParticleCount( ( int )( _particleCount ) );
        parent.chladniRealCircle.setParticleCount( ( int )( _particleCount ) );
    }

    public void particleOpacity( float _particleOpacity ) {
        parent.chladniCircle.setParticleOpacity( _particleOpacity );
        parent.chladniRect.setParticleOpacity( _particleOpacity );
        parent.chladniRealCircle.setParticleOpacity( _particleOpacity );
    }

    public void draw() {
        background( 0 );
    }

    public ControlFrame( Main theParent, int theWidth, int theHeight ) {
        parent = theParent;
        w = theWidth;
        h = theHeight;
    }

    public static ControlFrame addControlFrame(Main pa, String theName, int theWidth, int theHeight) {
        Frame f = new Frame(theName);
        ControlFrame p = new ControlFrame(pa, theWidth, theHeight);
        f.add(p);
        p.init();
        f.setTitle(theName);
        f.setSize(p.w, p.h);
        f.setLocation(100, 100);
        f.setResizable(false);
        f.setVisible(true);
        return p;
    }
}