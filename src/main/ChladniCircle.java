package main;

import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;

/**
 * Created by mar on 13.12.14.
 */
public class ChladniCircle extends ChladniSurface {

    private float m, n, scale;
    private int poles;
    private PImage circleMask;

    public ChladniCircle( PApplet p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_circle.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

        setPoles( 21 );
        setScale( 1.0f );
        setN( 3.0f );
        setM( 2.0f );

        this.circleMask = p.loadImage( "media" + File.separator + "circle.png" );
        this.circleMask.resize( ( int ) getWidth(), ( int ) getHeight() );
    }

    public void update() {
        this.shader.set( "m", getM() );
        this.shader.set( "n", getN() );
        this.shader.set( "scale", getScale() );
        this.shader.set( "poles", getPoles() );

        getBuffer().beginDraw();
        getBuffer().background( 255 );

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer().mask( circleMask );
        getBuffer().endDraw();
    }

    public void setM( float m ) {
        this.m = m;
    }

    public void setN( float n ) {
        this.n = n;
    }

    public float getN() {
        return this.n;
    }

    public float getM() {
        return this.m;
    }

    public void setScale( float scale ) {
        this.scale = scale;
    }

    public float getScale() {
        return this.scale;
    }

    public void setPoles( int poles ) {
        this.poles = poles;
    }

    public int getPoles( ) {
        return this.poles;
    }
}